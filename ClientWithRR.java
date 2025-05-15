import RequestReply.*;
import MessageMarshaller.*;
import Registry.*;
import Commons.Address;


public class ClientWithRR
{
	public static void main(String args[])
	{
		System.out.println("\n\nClient started");
		new Configuration();
        Address dest=Registry.instance().get("Server");
		Message msg= new Message("Client","get_temp(Paris)");
		Requestor r = new Requestor("Client");
		Marshaller m = new Marshaller();
		byte[] bytes = m.marshal(msg);
		bytes = r.deliver_and_wait_feedback(dest, bytes);
		Message answer = m.unmarshal(bytes);
		System.out.println("Client received message "+answer.data+" from "+answer.sender);
	}
}