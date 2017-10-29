package wy.bizproto;

/**
 * Created by BoonKok on 6/15/2017.
 */

public class Title {
    public String Location;
    public  String Details;

    public Title() {
    }

    public Title(String Details, String Location) {
        Location = Location;
        Details = Details;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getLocation() {
        return Location;
    }

    public String getDetails() {
        return Details;
    }
}
