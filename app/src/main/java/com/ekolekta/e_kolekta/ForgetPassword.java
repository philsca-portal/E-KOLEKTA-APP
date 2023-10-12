package com.ekolekta.e_kolekta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_kalat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    TextInputLayout textInputLayoutEmail;
    Button resetpass;
    ProgressBar progressBar;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

         textInputLayoutEmail = findViewById(R.id.emailadd);
         resetpass = (Button) findViewById(R.id.resetPass);
         progressBar = findViewById(R.id.progress);

         mAuth = FirebaseAuth.getInstance();

         resetpass.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 resetPassword();
             }
         });
    }

    private void resetPassword() {
        String txtEmail = textInputLayoutEmail.getEditText().getText().toString();

        if(!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()){
            textInputLayoutEmail.setError("Please enter a valid Email");
            textInputLayoutEmail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.sendPasswordResetEmail(txtEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgetPassword.this, "Please Check your Email to Reset Password", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgetPassword.this, login.class);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(ForgetPassword.this, "Failed to Reset Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}