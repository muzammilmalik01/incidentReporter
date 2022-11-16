package com.example.incidentreport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class onboarding_screens extends AppCompatActivity {
    ViewPager slides;
    LinearLayout dots;
    onboarding_adapter Adapter;
    TextView[] domts;
    Button contbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#1d1b1b"));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screens);

        slides = findViewById(R.id.slides);
        dots = findViewById(R.id.dots);
        contbtn = findViewById(R.id.contBtn);
        contbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(onboarding_screens.this,roles.class);
                finish();
                startActivity(intent);
            }
        });

        Adapter = new onboarding_adapter(this);
        slides.setAdapter(Adapter);
        addDots(0);
        slides.addOnPageChangeListener(changeListener);
    }

    private void addDots(int pos)
    {
        domts = new TextView[3];
        dots.removeAllViews();

        for (int i=0;i<domts.length;i++)
        {
            domts[i] = new TextView(this);
            domts[i].setText(Html.fromHtml("&#8226"));
            domts[i].setTextSize(30);
            dots.addView(domts[i]);
        }
        if(domts.length>0)
        {
            domts[pos].setTextColor(getResources().getColor(R.color.red));
        }
    }
    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            if(position==0)
            {
                contbtn.setVisibility(View.INVISIBLE);
            }
            else if (position == 1)
            {
                contbtn.setVisibility(View.INVISIBLE);
            }
            else if (position == 2)
            {
                contbtn.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}