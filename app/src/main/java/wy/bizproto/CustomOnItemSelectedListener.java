package wy.bizproto;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by weiyang on 6/16/2017.
 */

public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    private ArrayList<String> listOfPref = new ArrayList<String>();

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        //Toast.makeText(parent.getContext(),"On Item Select : \n" + parent.getItemAtPosition(pos).toString(),Toast.LENGTH_LONG).show();
       // listOfPref.add(parent.getItemAtPosition(pos).toString());

    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    // TODO Auto-generated method stub
    }

    public ArrayList<String> getListOfPref() {
        return listOfPref;
    }
}
