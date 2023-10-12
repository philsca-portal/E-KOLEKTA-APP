package com.ekolekta.e_kolekta;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ekolekta.e_kolekta.HomeAdapter.RecyclerAdapter;
import com.ekolekta.e_kolekta.HomeAdapter.RecyclerHelperClass;
import com.example.e_kalat.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final float END_SCALE = 0.7f;

    RecyclerView firstRecycler, secondRecycler;
    RecyclerView.Adapter adapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;
    LinearLayout contentView;
    DatabaseReference reference;
    TextView numData;
    int countData = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        firstRecycler = findViewById(R.id.first_recycler);
        secondRecycler = findViewById(R.id.second_recycler);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);
        numData = findViewById(R.id.displaydata);

        reference = FirebaseDatabase.getInstance().getReference();


        displayNumData();
        firstRecycler();
        secondRecycler();
        navigationDrawer();

    }

    private void displayNumData() {
        reference.child("ALL DATA").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    countData = (int) dataSnapshot.getChildrenCount();
                    numData.setText(Integer.toString(countData));

                }else{
                    numData.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }


    private void firstRecycler() {
        firstRecycler.setHasFixedSize(true);
        firstRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<RecyclerHelperClass> recycleLocations = new ArrayList<>();

        recycleLocations.add(new RecyclerHelperClass(R.drawable.pasaylogo, "Barangay 183", "is a barangay in the city of Pasay."));
        recycleLocations.add(new RecyclerHelperClass(R.drawable.barangay, "About", "Its population as determined by the 2020 Census was 37,372. This represented 8.48% of the total population of Pasay."));
        recycleLocations.add(new RecyclerHelperClass(R.drawable.people, "Website", "http://www.barangay183villamor.com.ph/"));

        adapter = new RecyclerAdapter(recycleLocations);
        firstRecycler.setAdapter(adapter);
    }

    private void secondRecycler() {
        secondRecycler.setHasFixedSize(true);
        secondRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<RecyclerHelperClass> recycleLocations = new ArrayList<>();

        recycleLocations.add(new RecyclerHelperClass(R.drawable.event, "Weekly Clean up drive", "With the effort of the following: Brgy Environmental Protection Team Brgy Clearing Team Brgy Street Sweepers Brgy Staff"));
        recycleLocations.add(new RecyclerHelperClass(R.drawable.event1, "Picture", "Exchange your trash to points and use it to their partner establishments or stores."));
        recycleLocations.add(new RecyclerHelperClass(R.drawable.event2, "Weekly Clean up at creek", "headed by Kap. Ruth M. Cortez and Council."));

        adapter = new RecyclerAdapter(recycleLocations);
        secondRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_account:
                Intent intent = new Intent(Dashboard.this,MyAccount.class);
                startActivity(intent);
                break;
            case R.id.nav_logs:
                Intent intent1 = new Intent(Dashboard.this,Userlog.class);
                startActivity(intent1);
                break;
            case R.id.nav_capture:
                Intent intent2 = new Intent(Dashboard.this,Capture.class);
                startActivity(intent2);
                break;
            case R.id.nav_how:
                Intent intent3 = new Intent(Dashboard.this, Howto.class);
                startActivity(intent3);
                break;
            case R.id.nav_news:
                Intent intent4 = new Intent(Dashboard.this, News.class);
                startActivity(intent4);
                break;
            case R.id.nav_schedule:
                Intent intent5 = new Intent(Dashboard.this, Schedule.class);
                startActivity(intent5);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}