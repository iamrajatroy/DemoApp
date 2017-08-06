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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText userEmail;
    EditText userPassword;
    Button registerBtn;
    TextView loginBtnPage;
    EditText confirmPassword;
    FirebaseAuth mFirebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Register");
        setContentView(R.layout.activity_register);

        userEmail = (EditText)findViewById(R.id.userEmail);
        userPassword = (EditText)findViewById(R.id.userPassword);
        registerBtn = (Button)findViewById(R.id.registerBtn);
        loginBtnPage = (TextView)findViewById(R.id.LoginBtnPage);
        confirmPassword = (EditText)findViewById(R.id.confirmPassword);

        progressDialog = new ProgressDialog(this);

        registerBtn.setOnClickListener(this);
        loginBtnPage.setOnClickListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v==registerBtn){
            String pass = userPassword.getText().toString();
            String cPass = confirmPassword.getText().toString();

            Log.d("password",pass);
            Log.d("conf pass",cPass);


                register();


        }
        if(v == loginBtnPage){
            finish();
            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
        }
    }

    private void register() {

        String user_email = userEmail.getText().toString().trim();
        String user_password = userPassword.getText().toString().trim();
        String cpass = confirmPassword.getText().toString().trim();

        Log.d("user entered email...",user_email);
        Log.d("user entered password",user_password);
        Log.d("user entered cpass",cpass);

        if(user_password.equals(cpass)){
            if(TextUtils.isEmpty(user_email)){
                Toast.makeText(this,"Email cannot be empty,Please enter email.",Toast.LENGTH_SHORT).show();
            }
            if(TextUtils.isEmpty(user_password)){
                Toast.makeText(this,"Password cannot be empty,Please enter password.",Toast.LENGTH_SHORT).show();
            }
            progressDialog.setMessage("Signing up User,Please Wait...");
            progressDialog.show();

            mFirebaseAuth.createUserWithEmailAndPassword(user_email,user_password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                Log.d("Register","PASS");
                                Toast.makeText(RegisterActivity.this,"You have been registered successfully",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(RegisterActivity.this,CardsActivity.class));
                                overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);
                            }else{
                                Log.d("Register","FAIL");
                                Toast.makeText(RegisterActivity.this,"Oops Something went wrong!! Please try again",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(RegisterActivity.this,"Passwords didn't match",Toast.LENGTH_SHORT).show();
        }

    }
}
