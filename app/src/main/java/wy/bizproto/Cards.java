package wy.bizproto;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Cards extends AppCompatActivity {
    private Spinner bankSpinner;
    private ImageView chord_img;
    private Button add_card;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Users");
    private String user;
    private FirebaseAuth auth;
    private EditText cardNumber;
    private EditText CCV;
    private EditText nameOnCard;
    private EditText expiryDateYY;
    private EditText expiryDateMM;
    private ArrayList<Card> listofCard = new ArrayList<Card>();
    private User u;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Cards");
        chord_img = (ImageView) findViewById(R.id.imageView2);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid();


        bankSpinner = (Spinner) findViewById(R.id.bank_spinner);
        ArrayAdapter<CharSequence> foodadapter = ArrayAdapter.createFromResource(
                this, R.array.bank_arrays, R.layout.spinner_layout);
        foodadapter.setDropDownViewResource(R.layout.spinner_layout);
        bankSpinner.setAdapter(foodadapter);

        bankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(bankSpinner.getSelectedItem()).equals("POSB"))
                    chord_img.setImageResource(R.drawable.posb);
                else if(String.valueOf(bankSpinner.getSelectedItem()).equals("OCBC"))
                    chord_img.setImageResource(R.drawable.ocbc);
                else if(String.valueOf(bankSpinner.getSelectedItem()).equals("DBS"))
                    chord_img.setImageResource(R.drawable.dbs);
                else if(String.valueOf(bankSpinner.getSelectedItem()).equals("UOB"))
                    chord_img.setImageResource(R.drawable.uob);
                else if(String.valueOf(bankSpinner.getSelectedItem()).equals("American Express"))
                    chord_img.setImageResource(R.drawable.amex);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //addListenerOnSpinnerItemSelection();
        addListenerOnButton();

        root.child(user + "/listOfCard").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    listofCard.add(((DataSnapshot) i.next()).getValue(Card.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
    /*public void addCard(View v){
       // addListenerOnSpinnerItemSelection();
        addListenerOnButton();
    }8/
    /*public void addListenerOnSpinnerItemSelection() {
        chord_img = (ImageView) findViewById(R.id.imageView2);
        bankSpinner = (Spinner) findViewById(R.id.bank_spinner);
        bankSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());


    }*/
    public void addListenerOnButton() {

        bankSpinner = (Spinner) findViewById(R.id.bank_spinner);
        add_card= (Button) findViewById(R.id.add_card);
        cardNumber = (EditText) findViewById(R.id.cardnumber);
        CCV = (EditText) findViewById(R.id.ccv);
        nameOnCard = (EditText) findViewById(R.id.nameoncard);
        expiryDateYY = (EditText) findViewById(R.id.expirydateyy);
        expiryDateMM = (EditText) findViewById(R.id.expirydateyy);

        add_card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Card card = new Card(String.valueOf(bankSpinner.getSelectedItem()),cardNumber.getText().toString(),CCV.getText().toString(),nameOnCard.getText().toString(),expiryDateYY.getText().toString(),expiryDateMM.getText().toString());
                listofCard.add(card);
                Toast.makeText(Cards.this, card.getBank() + " card has been added", Toast.LENGTH_SHORT).show();
                //Toast.makeText(Cards.this, listofCard.get(0).getBank(), Toast.LENGTH_SHORT).show();
                Map<String, Object> userUpdates = new HashMap<String, Object>();
                userUpdates.put(user + "/listOfCard",listofCard);
                root.updateChildren(userUpdates);
                Intent intent = new Intent(Cards.this,Settings.class);
                startActivity(intent);


            }

        });

    }





}
