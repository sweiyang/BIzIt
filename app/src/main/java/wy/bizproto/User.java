package wy.bizproto;

import java.util.ArrayList;

/**
 * Created by BoonKok on 6/14/2017.
 */

public class User {
    private String name;
    private String email;
    private String number;
    private ArrayList<String> listofPref = new ArrayList<String>();
    private ArrayList<Card> listOfCards = new ArrayList<Card>();


    public User(){

    }


    public User(String name, String email, String number){
         this.name = name;
        this.email = email;
        this.number = number;

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void addPref(ArrayList stringofPref){
        this.listofPref = stringofPref;
    }

    public void addCard(ArrayList stringofCards){
        this.listOfCards = stringofCards;
    }

    public void clearPrefList (){
        this.listofPref.clear();
    }
    public String getPref(int i){
        return this.listofPref.get(i);
    }

    public boolean hasCards(){
        if(listOfCards.size()==0)
            return false;
        else
            return true;
    }

    public ArrayList<Card> getListOfCards(){
        return listOfCards;
    }
}
