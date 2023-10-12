package com.ekolekta.e_kolekta;

import android.os.Bundle;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_kalat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Howto extends AppCompatActivity {

    ExpandableListViewAdapter listViewAdapter;
    ExpandableListView expandableListView;
    List<String> howList;
    HashMap <String, List<String>> subList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howto);

        expandableListView = findViewById(R.id.eListView);

        showList();

        listViewAdapter = new ExpandableListViewAdapter(this, howList,subList);
        expandableListView.setAdapter(listViewAdapter);
    }

    private void showList() {
        howList = new ArrayList<String>();
        subList = new HashMap<String, List<String>>();

        howList.add("How to check account details");
        howList.add("How to see User log");
        howList.add("How to check the News");
        howList.add("How to capture/report");
        howList.add("How to see schedule");
        howList.add("How does this help the environment?");
        howList.add("How to earn Points");

        List<String> sub1 = new ArrayList<>();
        sub1.add("◦Tap the side bar menu found on the top left of the screen, then tap on  My Account tab. You will see your account details.");
        sub1.add("◦You can also see the number of your entries and claim your rewards from here.");
        sub1.add("◦You can also logout from here.");

        List<String> sub2 = new ArrayList<>();
        sub2.add("◦Tap the side bar menu found on the top left of the screen, then tap on the User log tab.");
        sub2.add("◦You can see your entry pictures/reports.");

        List<String> sub3 = new ArrayList<>();
        sub3.add("◦Tap the side bar menu found on the top left of the screen, then tap on the News tab. Checkout news and events from the baranggay!");

        List<String> sub4 = new ArrayList<>();
        sub4.add("◦First,Please enable location services on your smartphone to proceed.");
        sub4.add("◦Tap the side bar menu found on the top left of the screen, then tap on the Capture tab.");
        sub4.add("◦Tap on the camera icon to start capturing the entry. After that, choose the proper category provided on the selection, as well as the type of it. Lastly, tap send to submit the entry. You can earn points through your submissions.");

        List<String> sub5 = new ArrayList<>();
        sub5.add("◦Tap the side bar menu found on the top left of the screen, then tap on the Schedule tab.");

        List<String> sub6 = new ArrayList<>();
        sub6.add("◦It helps the the community for environmental updates and allows them  to report environment problems specifically litters around the baranggay by giving the citizens a platform to take pictures, identify, and submit environmental problems to the officials of baranggay making them notice and take action about it.");

        List<String> sub7 = new ArrayList<>();
        sub7.add("◦Open the application E-Kolekta to your smartphones");
        sub7.add("◦Tap the upper left tab of the application to access the Menu Bar");
        sub7.add("◦Tap on Capture");
        sub7.add("◦Tap on the camera icon to start capturing the entry. After that, choose the proper category provided on the selection, as well as the type of it. Lastly, tap send to submit the entry.");
        sub7.add("◦The entry will first be verified by the admin");
        sub7.add("◦If Verified, You will earn your point!");

        subList.put(howList.get(0), sub1);
        subList.put(howList.get(1), sub2);
        subList.put(howList.get(2), sub3);
        subList.put(howList.get(3), sub4);
        subList.put(howList.get(4), sub5);
        subList.put(howList.get(5), sub6);
        subList.put(howList.get(6), sub7);
    }
}
