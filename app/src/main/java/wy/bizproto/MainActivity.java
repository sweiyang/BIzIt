package wy.bizproto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        EditText password = (EditText) findViewById(R.id.editText2);
        password.setTransformationMethod(new PasswordTransformationMethod());



    }

    public void goToSignUp(View view){
        Intent intent = new Intent(this,SignUp.class );
        startActivity(intent);
    }

    public void loginUser(View view){
        EditText Email = (EditText)findViewById(R.id.editText);
        EditText password = (EditText) findViewById(R.id.editText2);
        if(Email.getText().toString().equals("") || password.getText().toString().equals("")){
            Toast.makeText(this, "Fill in your password and email", Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this,"Please wait...","Procesing...",true);
        (firebaseAuth.signInWithEmailAndPassword(Email.getText().toString(), password.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Log in successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this,Home.class);
                    startActivity(intent);
                }
                else{
                    Log.e("ERROR",task.getException().toString());
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        });
    }
}
