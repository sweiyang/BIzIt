package wy.bizproto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class SignUp extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Users");
    private String emailstr;
    private String namestr;
    private String phonenumberstr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
        firebaseAuth = FirebaseAuth.getInstance();
        EditText password = (EditText) findViewById(R.id.editText5);
        EditText repassword = (EditText) findViewById(R.id.editText6);
        password.setTransformationMethod(new PasswordTransformationMethod());
        repassword.setTransformationMethod(new PasswordTransformationMethod());

    }

    public void gotoHome(View view){
        if(ifTextNotFilled()){
            Toast.makeText(this, "Fill in all the required details", Toast.LENGTH_LONG).show();
            return;
        }
        if(!passandrepasssame()){
            Toast.makeText(this, "Password and re-entered Password is not the same", Toast.LENGTH_LONG).show();
            return;
        }
        btnRegistrationUser();
        loginUser();
    }
    public void loginUser(){
        final EditText Email = (EditText)findViewById(R.id.editText4);
        final EditText password = (EditText) findViewById(R.id.editText5);
        final ProgressDialog progressDialog = ProgressDialog.show(SignUp.this,"Please wait...","Procesing...",true);
        (firebaseAuth.signInWithEmailAndPassword(Email.getText().toString(), password.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this, "Registration is successful", Toast.LENGTH_LONG).show();
                    FirebaseUser temp2 = FirebaseAuth.getInstance().getCurrentUser();
                    Intent intent = new Intent(SignUp.this,Home.class);
                    //Map<String, User> users = new HashMap<String, User>();
                    //users.put(temp2.getUid(),(new User(namestr,emailstr,phonenumberstr)));
                    root.child(temp2.getUid()).setValue(new User(namestr,emailstr,phonenumberstr));
                    startActivity(intent);
                }
                else{
                    Log.e("ERROR",task.getException().toString());
                    Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        });
    }
    public boolean ifTextNotFilled(){
        EditText Email = (EditText)findViewById(R.id.editText4);
        EditText password = (EditText) findViewById(R.id.editText5);
        EditText repassword = (EditText) findViewById(R.id.editText6);
        EditText phonenumber = (EditText) findViewById(R.id.editText3);
        EditText name = (EditText) findViewById(R.id.editText7);
        if(Email.getText().toString().equals("")|| password.getText().toString().equals("") || repassword.getText().toString().equals("") || phonenumber.getText().toString().equals("")||name.getText().toString().equals("")){
            return true;
        }
        return false;
    }

    public boolean passandrepasssame(){
        EditText password = (EditText) findViewById(R.id.editText5);
        EditText repassword = (EditText) findViewById(R.id.editText6);
        if(password.getText().toString().equals(repassword.getText().toString())){
            return true;
        }
        return false;
    }
    public void btnRegistrationUser(){
        final EditText Email = (EditText)findViewById(R.id.editText4);
        final EditText password = (EditText) findViewById(R.id.editText5);
        final EditText name = (EditText) findViewById(R.id.editText7);
        final EditText phonenumber = (EditText) findViewById(R.id.editText3);

        this.emailstr = Email.getText().toString();
        this.namestr = name.getText().toString();
        this.phonenumberstr = phonenumber.getText().toString();

        final ProgressDialog progressDialog = ProgressDialog.show(SignUp.this,"Please wait...","Procesing...",true);
        (firebaseAuth.createUserWithEmailAndPassword(Email.getText().toString(), password.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this, "Registration is successful", Toast.LENGTH_LONG).show();
                }
                else{
                    Log.e("ERROR",task.getException().toString());
                    Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        });
    }

}
