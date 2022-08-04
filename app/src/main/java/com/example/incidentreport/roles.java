package com.example.incidentreport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

public class roles extends AppCompatActivity {
    LinearLayout report,admin;

    Button r,a;
    Boolean location,wstorage,rstorage,camera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roles);
        location = false;
        wstorage = false;
        rstorage = false;
        camera = false;
        r = findViewById(R.id.reportbtn);
        a = findViewById(R.id.adminbtn);

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissions();
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginscreen = new Intent(getApplicationContext(),adminlogin.class);
                startActivity(loginscreen);
            }
        });

    }
    private void permission() {
        //Dexter.withContext(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,)
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                wstorage = true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(roles.this, "Storgae Access not Granted", Toast.LENGTH_SHORT).show();
                wstorage = false;
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
    private void locationpermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                location = true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(roles.this, "Location Access not Granted", Toast.LENGTH_SHORT).show();
                location = false;
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
    private void camerapermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                camera = true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(roles.this, "Camera Access not Granted", Toast.LENGTH_SHORT).show();
                camera = false;
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }


    private void permissions() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}

        }).check();
    }
}