package com.codemaker.rajatroy.demoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText userEmail;
    EditText userPassword;
    Button loginBtn;
    TextView registerPage;
    FirebaseAuth mFirebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Login");
        setContentView(R.layout.activity_main);

        userEmail = (EditText)findViewById(R.id.userEmail);
        userPassword = (EditText)findViewById(R.id.userPassword);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        registerPage = (TextView)findViewById(R.id.registerBtnPage);
        progressDialog = new ProgressDialog(this);

        loginBtn.setOnClickListener(this);
        registerPage.setOnClickListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = mFirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            //user already logged in
            Toast.makeText(MainActivity.this,"Welcome Back!",Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(getApplicationContext(),CardsActivity.class));
            overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);

        }
    }

    @Override
    public void onClick(View v) {
            if(v == loginBtn){
                login();
            }
            if(v == registerPage){
                finish();
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);
            }
    }

    private void login() {

        String user_email = userEmail.getText().toString().trim();
        String user_password = userPassword.getText().toString().trim();

        Log.d("user entered email...",user_email);
        Log.d("user entered password",user_password);

        if(TextUtils.isEmpty(user_email)){
            Toast.makeText(this,"Email cannot be empty,Please enter email.",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(user_password)){
            Toast.makeText(this,"Password cannot be empty,Please enter password.",Toast.LENGTH_SHORT).show();
        }
        progressDialog.setMessage("Signing in User,Please Wait...");
        progressDialog.show();

        mFirebaseAuth.signInWithEmailAndPassword(user_email,user_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //take user to next page
                            Log.d("Login","PASS");
                            Toast.makeText(MainActivity.this,"Signin Successful",Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(MainActivity.this,CardsActivity.class));
                            overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);
                        }else{
                            //show error msg
                            Log.d("Login","FAIL");
                            Toast.makeText(MainActivity.this,"Oops Something went wrong!! Please try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }
}
