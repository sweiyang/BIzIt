package wy.bizproto;

/**
 * Created by weiyang on 6/19/2017.
 */

public class Budget {
    private String ExpBud;
    private String income;

    public Budget(){

    }

    public Budget(String expBud, String income) {
        ExpBud = expBud;
        this.income = income;
    }

    public String getExpBud() {
        return ExpBud;
    }

    public String getIncome() {
        return income;
    }
}
