package com.example.incidentreport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.incidentreport.databinding.ActivityAdminactivityBinding;

public class adminactivity extends AppCompatActivity {

    ActivityAdminactivityBinding binding;
    Button searchbtn;
    String rights;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminactivity);
        rights = "admin";
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#1d1b1b"));
        }
        title = findViewById(R.id.admintitle);
        replaceFrag(new defaultadminfrag());
        binding = ActivityAdminactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
        switch (item.getItemId()){
            case R.id.approved:
                title.setText("Approved");
                replaceFrag(new approved());
                break;
            case R.id.pending:
                title.setText("Pending");
                replaceFrag(new pending());
                break;
            case R.id.rejected:
                title.setText("Rejected");
                replaceFrag(new rejected());
                break;
        }
            return true;
        });

        searchbtn = findViewById(R.id.sbtn2);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(adminactivity.this,search.class);
                search.putExtra("rights",rights);
                startActivity(search);
            }
        });
    }
    private void replaceFrag(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentframe,fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        defaultadminfrag d = new defaultadminfrag();
        replaceFrag(d);
    }
}
