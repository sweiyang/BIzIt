package wy.bizproto;

/**
 * Created by weiyang on 6/18/2017.
 */

public class Promo {
    private String Location;
    private String Outlet;
    private String Promotion;

    public Promo(){

    }

    public Promo(String location, String outlet, String promotion) {
        Location = location;
        Outlet = outlet;
        Promotion = promotion;
    }

    public String getLocation() {
        return Location;
    }

    public String getOutlet() {
        return Outlet;
    }

    public String getPromotion() {
        return Promotion;
    }
}
