package wy.bizproto;

/**
 * Created by weiyang on 6/21/2017.
 */

public class transaction {
    private String date;
    private Double sum;


    public transaction(){

    }

    public transaction(String date, Double sum) {
        this.date = date;
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public Double getSum() {
        return sum;
    }
}
