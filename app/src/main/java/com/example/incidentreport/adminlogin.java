package com.example.incidentreport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class adminlogin extends AppCompatActivity {
    Button submit;
    EditText adminid,password;
    ProgressBar pbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        submit = findViewById(R.id.loginbtn);
        pbar = findViewById(R.id.pbar2);
        adminid = findViewById(R.id.adminusername);
        password = findViewById(R.id.adminpassword);
        pbar.setVisibility(View.INVISIBLE);
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#1d1b1b"));
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbar.setVisibility(View.VISIBLE);
                submit.setVisibility(View.INVISIBLE);
                String ADMINID = adminid.getText().toString().trim();
                String PASSWORD = password.getText().toString().trim();
                if (ADMINID.length()<5 || PASSWORD.length() == 0 )
                {
                    pbar.setVisibility(View.INVISIBLE);
                    submit.setVisibility(View.VISIBLE);
                    if (ADMINID.length()<5)
                    {
                        Toast.makeText(adminlogin.this, "Enter correct ID", Toast.LENGTH_SHORT).show();
                    }
                    /*else if(PASSWORD.length() == 0)
                    {
                        Toast.makeText(adminlogin.this, "Please enter the password.", Toast.LENGTH_SHORT).show();
                    }*/
                    else
                    {

                    }
                }
                else
                {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("admin").child(ADMINID);
                    ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists())
                            {
                                String Passwordindb = snapshot.child("password").getValue(String.class);
                                if (Passwordindb.equals(PASSWORD))
                                {
                                    Toast.makeText(adminlogin.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                                    Intent adminactivity = new Intent(adminlogin.this, adminactivity.class);
                                    startActivity(adminactivity);
                                    finish();
                                }
                                else
                                {
                                    pbar.setVisibility(View.INVISIBLE);
                                    submit.setVisibility(View.VISIBLE);
                                    Toast.makeText(adminlogin.this, "Incorrect Password.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if (!snapshot.exists())
                            {
                                pbar.setVisibility(View.INVISIBLE);
                                submit.setVisibility(View.VISIBLE);
                                Toast.makeText(adminlogin.this, "Admin ID doesn't exists.", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    };
                    myref.addListenerForSingleValueEvent(eventListener);
                }
            }
        });
    }
}