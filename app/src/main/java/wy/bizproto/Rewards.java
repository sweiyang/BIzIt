package wy.bizproto;

/**
 * Created by weiyang on 6/18/2017.
 */

public class Rewards {
    private String bank;
    private String rewards;
    private String pref;

    public Rewards(){

    }

    public Rewards(String bank,String pref, String rewards) {
        this.bank = bank;
        this.rewards = rewards;
        this.pref = pref;
    }

    public String getPref() {
        return pref;
    }

    public String getBank() {
        return bank;
    }

    public String getRewards() {
        return rewards;
    }
}
