package wy.bizproto;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class payment extends AppCompatActivity {
    private Spinner availableCards;
    private TextView getRewardsinfo;
    private ImageView cards;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth auth;
    private String user;
    private ArrayList<String> listofCards = new ArrayList<String>();
    private ArrayList<Rewards> listofRewards = new ArrayList<Rewards>();
    private Map<String,String> map = new HashMap<String,String>();
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Payment");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid();
        getRewardsinfo = (TextView) findViewById(R.id.textView7);
        availableCards = (Spinner)findViewById(R.id.spinner6);
        cards = (ImageView) findViewById(R.id.imageView3);
        Intent intent = getIntent();
        result = intent.getStringExtra("result");



        root.child("Users/"+ user + "/listOfCard").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    Card card = data.getValue(Card.class);
                    listofCards.add(card.getBank());

                }

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(payment.this, R.layout.spinner_layout, listofCards);
                spinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);
                availableCards.setAdapter(spinnerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        root.child("Location/" + result).addValueEventListener(new ValueEventListener() { // must get result from QR code
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    Toast.makeText(payment.this, "You are at " + result, Toast.LENGTH_SHORT).show();
                    Rewards rewards = data.getValue(Rewards.class);
                    map.put(rewards.getBank(),rewards.getRewards());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        availableCards.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(String.valueOf(availableCards.getSelectedItem()).equals("POSB")) {
                    cards.setImageResource(R.drawable.posb);
                    getRewardsinfo.setText(" ");
                    getRewardsinfo.setText(map.get(availableCards.getSelectedItem()));
                }
                else if(String.valueOf(availableCards.getSelectedItem()).equals("OCBC")) {
                    cards.setImageResource(R.drawable.ocbc);
                    getRewardsinfo.setText(" ");
                    getRewardsinfo.setText(map.get(availableCards.getSelectedItem()));
                }
                else if(String.valueOf(availableCards.getSelectedItem()).equals("DBS")) {
                    cards.setImageResource(R.drawable.dbs);
                    getRewardsinfo.setText(" ");
                    getRewardsinfo.setText(map.get(availableCards.getSelectedItem()));
                }
                else if(String.valueOf(availableCards.getSelectedItem()).equals("UOB")) {
                    cards.setImageResource(R.drawable.uob);
                    getRewardsinfo.setText(" ");
                    getRewardsinfo.setText(map.get(availableCards.getSelectedItem()));
                }
                else if(String.valueOf(availableCards.getSelectedItem()).equals("American Express")) {
                    cards.setImageResource(R.drawable.amex);
                    getRewardsinfo.setText(" ");
                    getRewardsinfo.setText(map.get(availableCards.getSelectedItem()));
                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void gotoBarCode (View view){
        Intent intent = new Intent(this, barcode.class);
        intent.putExtra("cardname",String.valueOf(availableCards.getSelectedItem()));
        startActivity(intent);

    }


}





