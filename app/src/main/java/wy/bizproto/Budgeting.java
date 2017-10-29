package wy.bizproto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Budgeting extends AppCompatActivity {
    private EditText getIncome;
    private EditText getExpBud;
    private DatabaseReference root;
    private FirebaseAuth auth;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budgeting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Budgeting");
        getIncome = (EditText) findViewById(R.id.getincome);
        getExpBud = (EditText) findViewById(R.id.getExpBud);
        root = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid();



    }

    public void gotoExp(View view){
        if(getIncome.getText().toString().equals("") || getExpBud.getText().toString().equals(""))
            Toast.makeText(this, "Fill in the entries", Toast.LENGTH_SHORT).show();
        else{
            Map<String,Object> map = new HashMap<String,Object>();
            map.put(user + "/Budget",new Budget(getExpBud.getText().toString(),getIncome.getText().toString()));
            root.updateChildren(map);
            Intent intent = new Intent(this, Expendititure.class);
            startActivity(intent);
        }

    }
}
