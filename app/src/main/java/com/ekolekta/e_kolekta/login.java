package com.ekolekta.e_kolekta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_kalat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    Button gotoSignup,loginAccount,forgetpassword;
    TextInputLayout logEmail,logPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login1);

        loginAccount = findViewById(R.id.loginBtn);
        gotoSignup = findViewById(R.id.leadtosignup);
        logEmail = findViewById(R.id.emaill);
        logPassword = findViewById(R.id.password);
        forgetpassword = findViewById(R.id.forgetpass);
        mAuth = FirebaseAuth.getInstance();

        loginAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateEmail() | !validatePassword()){
                    return;
                }else{
                    isUser();
                }
            }
            private void isUser(){
                String email = logEmail.getEditText().getText().toString();
                String password = logPassword.getEditText().getText().toString();

               mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {

                       if(task.isSuccessful()){
                           startActivity(new Intent(login.this,Dashboard.class));
                           finish();
                       }else{
                           Toast.makeText(login.this, "Failed to login, Please check your credentials", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
            }
        });
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,ForgetPassword.class);
                startActivity(intent);
            }
        });
        gotoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,signup.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private Boolean validateEmail(){
        String valEmail = logEmail.getEditText().getText().toString();
        String emailvalidation = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(valEmail.isEmpty()){
            logEmail.setError("Field cannot be empty");
            return false;
        }else if(!valEmail.matches(emailvalidation)){
            logEmail.setError("Invalid Email address");
            return false;
        } else{
            logEmail.setError(null);
            return true;
        }
    }
    private Boolean validatePassword(){
        String valPass = logPassword.getEditText().getText().toString();

        if(valPass.isEmpty()){
            logPassword.setError("Field cannot be empty");
            return false;
        }else{
            logPassword.setError(null);
            logPassword.setErrorEnabled(false);
            return true;
        }
    }

}