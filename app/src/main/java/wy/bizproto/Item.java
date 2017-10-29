package wy.bizproto;

/**
 * Created by weiyang on 6/18/2017.
 */

public class Item {
    private String name;
    private double price;

    public Item(){

    }
    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
