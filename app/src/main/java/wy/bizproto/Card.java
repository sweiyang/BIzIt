package wy.bizproto;

import java.util.Date;

/**
 * Created by weiyang on 6/17/2017.
 */

public class Card {
    private String bank;
    private String creditcardnumber;
    private String CCV;
    private String nameOnCard;
    private String expiryDateYY;
    private String expiryDateMM;

    public Card(){

    }

    public Card(String bank, String creditcardnumber, String CCV,String nameOnCard, String year, String min) {
        this.bank = bank;
        this.creditcardnumber = creditcardnumber;
        this.CCV = CCV;
        this.nameOnCard = nameOnCard;
        this.expiryDateYY = year;
        this.expiryDateMM = min;
    }

    public String getBank() {
        return bank;
    }

    public String getCreditcardnumber() {
        return creditcardnumber;
    }

    public String getCCV() {
        return CCV;
    }

    public String getExpiryDateYY() {
        return expiryDateYY;
    }

    public String getExpiryDateMM() {
        return expiryDateMM;
    }
}
