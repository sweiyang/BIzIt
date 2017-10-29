package wy.bizproto;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Transfer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    private EditText result;
    private String user;
    private String destination_user;
    private FirebaseDatabase root;
    private FirebaseAuth auth;
    private EditText getMoney;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        result = (EditText) findViewById(R.id.result1);
        getMoney = (EditText) findViewById(R.id.getMoney);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

        else if(id == R.id.Home){
            Intent intent = new Intent(this,Home.class);
            startActivity(intent);
        }

        else if(id == R.id.transfer){
            Intent intent = new Intent(this,Transfer.class);
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
                        destination_user = barcode.displayValue;
                        Toast.makeText(Transfer.this, "$" + getMoney.getText() + " have been transfered to " + destination_user, Toast.LENGTH_SHORT).show();


                    }

                });

            }

        }
    }
}
