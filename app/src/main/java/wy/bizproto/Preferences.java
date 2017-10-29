package wy.bizproto;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Preferences extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public ArrayList<String> list_of_Pref = new ArrayList<String>();
    private Spinner spinner1;
    private Button button;
    private ListView displayPref;
    private dataListAdapter listAdapter;
    private ArrayList<String> temp = new ArrayList<String>();
    private FirebaseAuth auth;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Users");
    String user;
    String email;
    String phonenumber;
    ArrayList<String> tempPref = new ArrayList<String>();
    ArrayList<String> tempCard = new ArrayList<String>();

    User u = new User();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Preferences");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        displayPref = (ListView) findViewById(R.id.listof_pref);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid();

        listAdapter = new dataListAdapter(list_of_Pref);
        displayPref.setAdapter(listAdapter);




        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        addListenerOnButton();
        addListenerOnSpinnerItemSelection();


        ArrayAdapter<CharSequence> foodadapter = ArrayAdapter.createFromResource(
                this, R.array.preferences_arrays, R.layout.spinner_layout);
        foodadapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner1.setAdapter(foodadapter);

        root.child(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    u = dataSnapshot.getValue(User.class);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void updatepreference (View view){
        u.clearPrefList();
        u.addPref(temp);
        Toast.makeText(Preferences.this,"Preference updated", Toast.LENGTH_SHORT).show();
        Map<String, Object> userUpdates = new HashMap<String, Object>();
        userUpdates.put(user + "/listofPref",temp);
        root.updateChildren(userUpdates);


        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);

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



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }
    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);


        button = (Button) findViewById(R.id.button4);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int size = list_of_Pref.size() + 1;
                if(temp.contains(String.valueOf(spinner1.getSelectedItem()))){
                    Toast.makeText(Preferences.this, "This options already is in the priority list. Remove that option from priority list", Toast.LENGTH_SHORT).show();
                }
                else{
                    list_of_Pref.add(size + "." + " " + String.valueOf(spinner1.getSelectedItem()));
                    temp.add(String.valueOf(spinner1.getSelectedItem()));
                }
                listAdapter.notifyDataSetChanged();

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



}



