package Info;

public class InfoServiceImpl implements InfoService {
    @Override
    public String get_temp(String city) {
        if (city.equalsIgnoreCase("Paris")) return "18°C";
        if (city.equalsIgnoreCase("Berlin")) return "15°C";
        return "Temperature not available";
    }

    @Override
    public String get_road_info(int road) {
        if (road == 1) {return "Heavy traffic";}
        else if (road == 2) {return "Clear";}
        else {return "Road info not available";}
    }
}
