package com.example.incidentreport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jraska.falcon.Falcon;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class search extends AppCompatActivity {
    String rights;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchdefualt s = new searchdefualt();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.infoframe,s);
        fragmentTransaction.commit();

        Intent i = getIntent();
        rights = i.getStringExtra("rights");
        setContentView(R.layout.activity_search);
        EditText searchbar = findViewById(R.id.searchbar);
        ImageButton sicon = findViewById(R.id.sicon);
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#1d1b1b"));
        }
        sicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rno = searchbar.getText().toString().trim();
                if(rno.isEmpty())
                {
                    Toast.makeText(search.this, "Please Enter a Report Number", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    FirebaseDatabase rootnode = FirebaseDatabase.getInstance();
                    DatabaseReference ref = rootnode.getReference("reports").child("status").child(rno);
                    ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                String status = snapshot.child("status").getValue(String.class);
                                fullreport f = new fullreport(rno,status,rights);
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.infoframe,f);
                                fragmentTransaction.commit();
                            }
                            else if (!snapshot.exists())
                            {
                                reportnotfound rn = new reportnotfound();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.infoframe,rn);
                                fragmentTransaction.commit();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    };
                    ref.addListenerForSingleValueEvent(eventListener);
                }
            }

        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(rights.equals("admin"))
        {
            Intent goback = new Intent(search.this,adminactivity.class);
            finish();
            startActivity(goback);
        }
        else if(rights.equals("user"))
        {
            Intent goback = new Intent(search.this,MainActivity.class);
            finish();
            startActivity(goback);
        }

    }
}