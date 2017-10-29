package wy.bizproto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Receipt extends AppCompatActivity {
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Users");
    private ArrayList<String> list_of_items = new ArrayList<String>();
    private ArrayList<Double> list_of_prices = new ArrayList<Double>();
    //private ArrayList<Double> list_of_transaction = new ArrayList<Double>();
    private ArrayList<transaction> list_of_transaction = new ArrayList<transaction>();
    private ListView displayReceipt;
    private dataListAdapter listAdapter;
    private FirebaseAuth auth;
    private String user;
    private TextView displayPrice;
    private double sum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Receipt");
        displayReceipt = (ListView) findViewById(R.id.listofItems);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid();
        displayPrice = (TextView) findViewById(R.id.displayPrice);
        sum=0;


        listAdapter = new dataListAdapter(list_of_items);
        displayReceipt.setAdapter(listAdapter);


        root.child(user + "/Receipt").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    Item item;
                    item = data.getValue(Item.class);
                    list_of_items.add(item.getName());
                    list_of_prices.add(item.getPrice());
                    //Toast.makeText(Receipt.this, String.valueOf(list_of_prices.get(0)), Toast.LENGTH_SHORT).show();

                }

                listAdapter.notifyDataSetChanged();
                for(int i=0;i<list_of_prices.size();i++){
                    sum += list_of_prices.get(i);
                }
                displayPrice.setText("$" + String.valueOf(sum));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        root.child(user + "/Transaction History").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    transaction trans;
                    trans = data.getValue(transaction.class);
                    list_of_transaction.add(trans);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    class dataListAdapter extends BaseAdapter {
        ArrayList<String> Title = new ArrayList<String>();

        dataListAdapter() {
            Title = null;

        }

        public dataListAdapter(ArrayList<String> text) {
            Title = text;


        }

        public int getCount() {
            // TODO Auto-generated method stub
            return Title.size();
        }

        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.custom, parent, false);
            TextView title;
            title = (TextView) row.findViewById(R.id.title);
            title.setText(Title.get(position));


            return (row);
        }


    }
    public void gotoBarCode(View view){
        Intent intent = new Intent(this, barcode.class);
        startActivity(intent);
    }
    public void gotoHome(View view){
        transaction t = new transaction(DateFormat.getDateTimeInstance().format(new Date()),sum);
        list_of_transaction.add(t);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(user + "/Transaction History",list_of_transaction);
        root.updateChildren(map);
        Toast.makeText(this, "Transaction Complete", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);

    }
}
