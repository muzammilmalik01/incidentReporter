package com.example.incidentreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#1d1b1b"));
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent hehe = new Intent(splashscreen.this, roles.class);
                startActivity(hehe);
                finish();
            }
        }, 2000);
    }
}