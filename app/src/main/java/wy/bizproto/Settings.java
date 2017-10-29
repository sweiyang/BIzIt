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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Settings extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    private ArrayList<String> list_of_updatedPref = new ArrayList<String>();
    private ArrayList<String> list_of_updatedCards = new ArrayList<String>();
    private FirebaseAuth auth;
    private String user;
    private ListView displayupdatedPref;
    private ListView displayupdatedCards;
    private dataListAdapter listAdapter;
    private dataListAdapter listAdapter2;
    private Card cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid();
        displayupdatedCards = (ListView) findViewById(R.id.updateCards);
        displayupdatedPref = (ListView) findViewById(R.id.updatedPref);



        listAdapter = new dataListAdapter(list_of_updatedPref);
        displayupdatedPref.setAdapter(listAdapter);

        listAdapter2 = new dataListAdapter(list_of_updatedCards);
        displayupdatedCards.setAdapter(listAdapter2);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        root.child("Users/" + user + "/listofPref").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int j=1;
                Iterator i = dataSnapshot.getChildren().iterator();
                list_of_updatedPref.clear();
                while (i.hasNext()) {
                    list_of_updatedPref.add( j  + " " + ((DataSnapshot) i.next()).getValue(String.class));
                    j++;
                }


                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        root.child("Users/" + user + "/listOfCard").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_of_updatedCards.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    cards = data.getValue(Card.class);
                    list_of_updatedCards.add(cards.getBank());
                }
                listAdapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void gotoPreference (View view){
        Intent intent = new Intent(this, Preferences.class);
        startActivity(intent);
    }

    public void gotoCards (View view){
        Intent intent = new Intent(this,Cards.class);
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
        if(id == R.id.expenditure){
            Intent intent = new Intent(this, Expendititure.class);
            startActivity(intent);

        }
        else if(id == R.id.settings){
            Intent intent = new Intent(this,Settings.class);
            startActivity(intent);
        }


        else if(id == R.id.logout){
            auth.signOut();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.Home){
            Intent intent = new Intent(this,Home.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
