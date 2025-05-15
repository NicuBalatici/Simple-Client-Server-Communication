import RequestReply.*;
import MessageMarshaller.*;
import Registry.*;
import Commons.Address;
import Info.*;

class ServerTransformer implements ByteStreamTransformer
{
	private MessageServer originalServer;

	public ServerTransformer(MessageServer s)
	{
		originalServer = s;
	}

	public byte[] transform(byte[] in)
	{
		Message msg;
		Marshaller m = new Marshaller();
		msg = m.unmarshal(in);

		Message answer = originalServer.get_answer(msg);

		byte[] bytes = m.marshal(answer);
		return bytes;

	}
}


class MessageServer {
	private InfoServiceImpl infoService;

	public MessageServer() {
		this.infoService = new InfoServiceImpl();
	}

	public Message get_answer(Message msg) {
		System.out.println("Server received " + msg.data + " from " + msg.sender);
		String request = msg.data;
		String response;

		try {
			if (request.startsWith("get_road_info(")) {
				int start = request.indexOf('(') + 1;
				int end = request.indexOf(')');
				int roadNumber = Integer.parseInt(request.substring(start, end));
				response = infoService.get_road_info(roadNumber);
			} else if (request.startsWith("get_temp(")) {
				int start = request.indexOf('(') + 1;
				int end = request.indexOf(')');
				String city = request.substring(start, end).replace("\"", "").trim();
				response = infoService.get_temp(city);
			} else {
				response = "Unknown method";
			}
		} catch (Exception e) {
			response = "Error processing request: " + e.getMessage();
		}

		return new Message("Server", response);
	}
}

public class ServerWithRR
{
	public static void main(String args[])
	{
		System.out.println("\n\nServer started");
		new Configuration();
		ByteStreamTransformer transformer = new ServerTransformer(new MessageServer());
		Address myAddr = Registry.instance().get("Server");
		Replyer r = new Replyer("Server", myAddr);
		while (true) {
		  r.receive_transform_and_send_feedback(transformer);
		}
	}

}