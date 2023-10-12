package com.ekolekta.e_kolekta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_kalat.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyAccount extends AppCompatActivity {

    FirebaseUser user;
    DatabaseReference reference,reference1,reference2;
    String userID, reward,reward1,reward2;
    TextView total;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account2);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference1 = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("DATA");
        userID = user.getUid();

        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("REWARD");

        total = findViewById(R.id.totalNum);

        final TextInputEditText editFullname = (TextInputEditText) findViewById(R.id.fullName);
        final TextInputEditText editEmail = (TextInputEditText) findViewById(R.id.eMail);
        final TextInputEditText editPhoneno = (TextInputEditText) findViewById(R.id.phoneNo);

        final TextView tviewFullname = (TextView) findViewById(R.id.fnamee);
        final TextView tviewEmail = (TextView) findViewById(R.id.emaill);

        final Button logoutbutton = (Button) findViewById(R.id.myAccountLogoutBtn);
        final Button claimbutton = (Button) findViewById(R.id.claim_btn);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    count = (int) dataSnapshot.getChildrenCount();
                    total.setText(Integer.toString(count));
                }else{
                    total.setText("0");
                }
                claimbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reward = "3 instant noodle";
                        reward1 = "Bag of canned goods";
                        reward2 = "5kg of rice";

                        if(count >= 15){
                            reference2.setValue(reward2);
                            setContentView(R.layout.reward2);
                        }else if(count >=6){
                            reference2.setValue(reward1);
                            setContentView(R.layout.reward1);
                        }else if(count == 5){
                            reference2.setValue(reward);
                            setContentView(R.layout.reward);
                        }else if(count <= 4){
                            Toast.makeText(MyAccount.this, "No Rewards to claim..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userHelperClass userHelper = dataSnapshot.getValue(userHelperClass.class);

                if(userHelper != null){
                    String fullname = userHelper.fullname;
                    String email = userHelper.email;
                    String phoneno = userHelper.phoneno;

                    editFullname.setText(fullname);
                    editEmail.setText(email);
                    editPhoneno.setText(phoneno);
                    tviewFullname.setText(fullname);
                    tviewEmail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MyAccount.this, "Something Wrong Happened!", Toast.LENGTH_SHORT).show();
            }
        });

        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MyAccount.this,login.class));
                finish();
            }
        });

    }
}