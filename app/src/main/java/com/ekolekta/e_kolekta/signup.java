package com.ekolekta.e_kolekta;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_kalat.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {
    TextInputLayout regId,regName,regUsername,regEmail,regPhone,regPassword;
    Button goToLogin, regBtn;
    FirebaseAuth mAuth;
    Dialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        goToLogin = findViewById(R.id.leadtologin);
        regId = findViewById(R.id.id);
        regName = findViewById(R.id.fullname);
        regUsername = findViewById(R.id.username1);
        regEmail = findViewById(R.id.email);
        regPhone = findViewById(R.id.phone);
        regPassword = findViewById(R.id.password1);
        regBtn = findViewById(R.id.signupbtn);
        mAuth= FirebaseAuth.getInstance();
        dialog = new Dialog(this);

        regBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!validateId() | !validateName() | !validateUsername() | !validateEmail() | !validatePhoneno() | !validatePassword()){
                    return;
                }
                String baranggayid = regId.getEditText().getText().toString();
                String fullname = regName.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneno = regPhone.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();
                String points = String.valueOf(0);

                Intent intent = new Intent(getApplicationContext(),VerifyPhoneNo.class);
                intent.putExtra("baranggayid",baranggayid);
                intent.putExtra("phoneno",phoneno);
                intent.putExtra("fullname",fullname);
                intent.putExtra("username",username);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                intent.putExtra("points",points);
                startActivity(intent);

                /*mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            userHelperClass helperClass = new userHelperClass(fullname,username,email,phoneno,password);

                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(helperClass)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                sucessdialog();
                                            }else{
                                                Toast.makeText(signup.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(signup.this, "Failed to register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/

            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this,login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private Boolean validateId(){
        String valId = regId.getEditText().getText().toString();

        if(valId.isEmpty()){
            regId.setError("Field cannot be empty");
            return false;
        }else{
            regId.setError(null);
            regId.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateName(){
        String valName = regName.getEditText().getText().toString();

        if(valName.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }else{
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername(){
        String valUsername = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if(valUsername.isEmpty()){
            regUsername.setError("Field cannot be empty");
            return false;
        }else if(valUsername.length()>=15){
            regUsername.setError("Username too long");
            return false;
        }else if(!valUsername.matches(noWhiteSpace)){
            regUsername.setError("White Spaces are not allowed");
            return false;
        }else{
            regUsername.setError(null);
            return true;
        }
    }

    private Boolean validateEmail(){
        String valEmail = regEmail.getEditText().getText().toString();
        String emailvalidation = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(valEmail.isEmpty()){
            regEmail.setError("Field cannot be empty");
            return false;
        }else if(!valEmail.matches(emailvalidation)){
            regEmail.setError("Invalid Email address");
            return false;
        } else{
            regEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePhoneno(){
        String valPhone = regPhone.getEditText().getText().toString();

        if(valPhone.isEmpty()){
            regPhone.setError("Field cannot be empty");
            return false;
        }else{
            regPhone.setError(null);
            return true;
        }
    }

    private Boolean validatePassword(){
        String valPass = regPassword.getEditText().getText().toString();
        String passwordvalidation = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if(valPass.isEmpty()){
            regPassword.setError("Field cannot be empty");
            return false;
        }else if(!valPass.matches(passwordvalidation)){
            regPassword.setError("Password is too weak");
            return false;
        }else{
            regPassword.setError(null);
            return true;
        }
    }

    private void sucessdialog(){
        dialog.setContentView(R.layout.sucess);
        Button btn = dialog.findViewById(R.id.buttonok);
        dialog.show();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),login.class);
                startActivity(intent);
                finish();
            }
        });
    }

}