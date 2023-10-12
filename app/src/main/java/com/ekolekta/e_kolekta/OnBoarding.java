package com.ekolekta.e_kolekta;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.e_kalat.R;

public class OnBoarding extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout display;

    SliderAdapter sliderAdapter;
    TextView[] dots;
    Button letsGetStarted;
    Animation animation;
    int currentPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);

        viewPager = findViewById(R.id.slider);
        display = findViewById(R.id.dots);
        letsGetStarted = findViewById(R.id.getstartedbtn);

        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);

        letsGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoarding.this,login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void skip(View view){
        startActivity(new Intent(this, login.class));
        finish();
    }

    public void next(View view){
        viewPager.setCurrentItem(currentPos + 1);
    }

    private void addDots(int position){

        dots = new TextView[4];
        display.removeAllViews();

        for(int i=0; i<dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);

            display.addView(dots[i]);
        }

        if(dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.pink));
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPos = position;
            if(position == 0){
                letsGetStarted.setVisibility(View.INVISIBLE);
            }else if (position == 1){
                letsGetStarted.setVisibility(View.INVISIBLE);
            }
            else if (position == 2){
                letsGetStarted.setVisibility(View.INVISIBLE);
            }
            else{
                animation = AnimationUtils.loadAnimation(OnBoarding.this,R.anim.side_anim);
                letsGetStarted.setAnimation(animation);
                letsGetStarted.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}