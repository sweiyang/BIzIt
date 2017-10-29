package wy.bizproto;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    private ArrayList<String> list_of_plans = new ArrayList<String>();
    private ArrayList<String> list_of_strings = new ArrayList<String>();
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    private ListView displayPref;
    private ListView displayPlans;
    private dataListAdapter listAdapter;
    private dataListAdapter listAdapter2;
    private TextView result; // get the result of the QR code;
    public String result1;
    private FirebaseAuth firebaseAuth;
    private ArrayList<String> list_of_cards = new ArrayList<String>();
    private ArrayList<String> list_of_location = new ArrayList<String>();
    private String user;
    private String location;
    private String temp;
    private String temp1;
    private ArrayList<String> list_of_pref = new ArrayList<String>();
    private ArrayList<String> list_of_temp = new ArrayList<String>();
    private ArrayList<Rewards> list_of_rewards = new ArrayList<Rewards>();
    private Map<Integer, String> mp = new HashMap<Integer, String>();
    private ArrayList<String> list_of_banks = new ArrayList<String>();
    private ArrayList<String> list_of_availpref = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        displayPref = (ListView) findViewById(R.id.listofpref);
        displayPlans = (ListView) findViewById(R.id.listofplans);
        result = (TextView) findViewById(R.id.result);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser().getUid();

        listAdapter = new dataListAdapter(list_of_strings);
        displayPref.setAdapter(listAdapter);

        listAdapter2 = new dataListAdapter(list_of_plans);
        displayPlans.setAdapter(listAdapter2);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        root.child("Users/" + user + "/Location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                location = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        root.child("Location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    list_of_location.add(data.getKey());
                }
                if(list_of_location.contains(location)){

                    root.child("Users/" + user + "/listOfCard").addValueEventListener(new ValueEventListener() {       // gets the list of cards that the user has
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                Card card;
                                card = data.getValue(Card.class);
                                list_of_cards.add(card.getBank());



                            }
                            root.child("Users/" + user + "/listofPref").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        list_of_pref.add(data.getValue(String.class));
                                        //temp = data.getValue(String.class);

                                    }
                                    root.child("Location/" + location).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for(DataSnapshot data: dataSnapshot.getChildren()){
                                                temp = data.getKey();
                                            }
                                            root.child("Location/" + location + "/" + temp).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for(DataSnapshot data:dataSnapshot.getChildren()){
                                                        Rewards r;
                                                        r = data.getValue(Rewards.class);
                                                        list_of_rewards.add(r);
                                                    }
                                                    for(int i=0;i<list_of_pref.size();i++){
                                                        for(int j=0;j<list_of_rewards.size();j++){
                                                            if(list_of_cards.contains(list_of_rewards.get(j).getBank())){
                                                                if(list_of_pref.get(i).equals(list_of_rewards.get(j).getPref())){
                                                                    list_of_strings.add("Location: " + temp + "\nBanks: " + list_of_rewards.get(j).getBank() + "\nRewards: " + list_of_rewards.get(j).getRewards() + "\nPreference: " + list_of_rewards.get(j).getPref());
                                                                }

                                                            }
                                                        }
                                                    }
                                                    listAdapter.notifyDataSetChanged();

                                                }


                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });



                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        root.child("Plans/POSB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();
                list_of_plans.clear();
                while (i.hasNext()) {
                    list_of_plans.add(((DataSnapshot) i.next()).getValue(String.class));
                }


                listAdapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.expenditure) {
            Intent intent = new Intent(this, Expendititure.class);
            startActivity(intent);

        } else if (id == R.id.settings) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        } else if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.Home) {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        } else if (id == R.id.transfer) {
            Intent intent = new Intent(this, Transfer.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void gotoQRscanner(View view) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST);

        } else {
            Intent intent = new Intent(this, scan.class);
            startActivityForResult(intent, REQUEST_CODE);
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            if (data != null) {

                final Barcode barcode = data.getParcelableExtra("barcode");

                result.post(new Runnable() { // there is an error here


                    @Override

                    public void run() {
                        //Toast.makeText(Home.this, barcode.displayValue, Toast.LENGTH_SHORT).show();
                        result1 = barcode.displayValue;
                        Intent intent = new Intent(Home.this, payment.class);
                        intent.putExtra("result", barcode.displayValue);
                        startActivity(intent);


                    }

                });

            }

        }

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

}
