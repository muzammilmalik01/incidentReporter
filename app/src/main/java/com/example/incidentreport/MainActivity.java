package com.example.incidentreport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.StatusBarManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jraska.falcon.Falcon;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, AdapterView.OnItemSelectedListener {
    MapView map;
    GoogleMap agoogleMap;
    Button fab,media,submit,search;
    Double LONGITUDE,LATITUDE;
    TextView location,date,time,Incident;
    Spinner Incidents;
    String incident,description,currentDate,currentTime,image,rights;
    EditText Description;
    Uri i;
    ProgressBar p;
    ImageView incim;
    SliderView sliderView;
    int[] images = {R.drawable.fireimages,R.drawable.punjabpolice,R.drawable.snatching,R.drawable.ambulance};

    private FusedLocationProviderClient mLocationClient;
    @SuppressLint({"MissingPermission", "VisibleForTests"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#1d1b1b"));
        }
        image = "hehe";
        i = null;
        p = findViewById(R.id.progressBar);
        p.setVisibility(View.INVISIBLE);
        map = findViewById(R.id.mapView);
        fab = findViewById(R.id.locationbtn);
        location = findViewById(R.id.locationtxt);
        date = findViewById(R.id.datetxt);
        time = findViewById(R.id.timetxt);
        Incidents = findViewById(R.id.spinner);
        Description = findViewById(R.id.descriptionedit);
        media = findViewById(R.id.mediabtn);
        submit = findViewById(R.id.submitbtn);
        search = findViewById(R.id.sbtn);
        Incident = findViewById(R.id.inci);
        incim = findViewById(R.id.inciim);
        sliderView = findViewById(R.id.SLIDER);
        rights = "user";
        sliderAdapter sliderAdapter = new sliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.incidents, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Incidents.setAdapter(adapter);
        Incidents.setOnItemSelectedListener(this);
        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        mLocationClient = new FusedLocationProviderClient(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getCurrentLocation();
                    }
                }, 2000);

                date.setText(currentDate);
                time.setText(currentTime);
                if(LONGITUDE !=null && LATITUDE !=null)
                {
                    Geocoder geocoder = new Geocoder(MainActivity.this,Locale.getDefault());
                    try {
                        List<Address>listaddress = geocoder.getFromLocation(Double.valueOf(LATITUDE),Double.valueOf(LONGITUDE),1);
                        if(listaddress.size()>0)
                        {
                            listaddress.get(0).getMaxAddressLineIndex();
                            location.setText("Location: "+ listaddress.get(0).getAddressLine(0));
                            //Toast.makeText(MainActivity.this, listaddress.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(MainActivity.this)
                        .start();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = Description.getText().toString();
                submit.setVisibility(View.INVISIBLE);
                p.setVisibility(View.VISIBLE);
                if(LATITUDE == null || LONGITUDE == null || incident == null || description.length() < 10 || description.length() > 1000 )
                {
                    submit.setVisibility(View.VISIBLE);
                    p.setVisibility(View.INVISIBLE);
                    if(LATITUDE == null || LONGITUDE == null )
                    {
                        Toast.makeText(MainActivity.this, "Location not set.", Toast.LENGTH_SHORT).show();
                    }
                    else if(incident == null)
                    {
                        Toast.makeText(MainActivity.this, "Please Select an Incident.", Toast.LENGTH_SHORT).show();
                    }
                    else if (description.length() < 10 || description.length() > 1000)
                    {
                        Toast.makeText(MainActivity.this, "Description must be between 10-1000 characters.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    FirebaseDatabase rootnode = FirebaseDatabase.getInstance();
                    DatabaseReference ref = rootnode.getReference("rn");
                    ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                submit.setVisibility(View.INVISIBLE);
                                p.setVisibility(View.VISIBLE);
                                submit.setEnabled(false);
                                String Rn = snapshot.getValue(String.class);
                                int ReportID = Integer.valueOf(Rn);
                                int rn=Integer.valueOf(Rn);
                                rn++;
                                String reportnumber = String.valueOf(rn);
                                ref.setValue(reportnumber);
                                String lat = LATITUDE.toString();
                                String lon = LONGITUDE.toString();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myref = database.getReference("reports").child("status");
                                myref.child(String.valueOf(ReportID)).child("status").setValue("pending");
                                myref = database.getReference("reports").child("pending");
                                myref.child(String.valueOf(ReportID)).child("date").setValue(currentDate);
                                myref.child(String.valueOf(ReportID)).child("time").setValue(currentTime);
                                myref.child(String.valueOf(ReportID)).child("longitude").setValue(lon);
                                myref.child(String.valueOf(ReportID)).child("latitude").setValue(lat);
                                myref.child(String.valueOf(ReportID)).child("description").setValue(description);
                                myref.child(String.valueOf(ReportID)).child("incident").setValue(incident);
                                myref.child(String.valueOf(ReportID)).child("image").setValue(image);
                                myref.child(String.valueOf(ReportID)).child("status").setValue("pending");
                                myref.child(String.valueOf(ReportID)).child("reportnumber").setValue(String.valueOf(ReportID));
                                if(!image.equals("hehe"))
                                {
                                    FirebaseStorage storage = FirebaseStorage.getInstance();
                                    StorageReference storageRef = storage.getReference();
                                    StorageReference mountainsRef = storageRef.child("reports/"+String.valueOf(ReportID)+"/");
                                    mountainsRef.putFile(i);
                                }
                                Toast.makeText(MainActivity.this, "Incident Reported.", Toast.LENGTH_SHORT).show();
                                Intent proof = new Intent(MainActivity.this,submissionproof.class);
                                proof.putExtra("id",String.valueOf(ReportID));
                                proof.putExtra("incident",incident);
                                startActivity(proof);
                                finish();
                            }
                            else if (!snapshot.exists())
                            {
                                submit.setVisibility(View.VISIBLE);
                                p.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, "RN not found.", Toast.LENGTH_SHORT).show();
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
        /*back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(MainActivity.this,roles.class);
                startActivity(back);
                finish();
            }
        });*/
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(MainActivity.this,search.class);
                search.putExtra("rights",rights);
                startActivity(search);
            }
        });
        map.getMapAsync(this);
        map.onCreate(savedInstanceState);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            Uri uri  = data.getData();
            Button b = findViewById(R.id.mediabtn);
            ImageView ii = findViewById(R.id.selectstatus);
            b.setText("Photo Attached\n 1/1 Selected");
            ii.setImageResource(R.drawable.sign);
            image = uri.toString();
            i = uri;

                    // Use Uri object instead of File to avoid storage permissions
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
            image = "hehe";
        } else {
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
            image = "hehe";
        }
    }
    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        mLocationClient.getLastLocation().addOnCompleteListener(this::onComplete);
    }

    @SuppressLint("MissingPermission")
    private void gotoLocation(double latitude, double longitude) {
        agoogleMap.clear();
        LatLng latLng = new LatLng(latitude,longitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,18);
        agoogleMap.moveCamera(cameraUpdate);
        agoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LONGITUDE = longitude;
        LATITUDE = latitude;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        agoogleMap.addMarker(markerOptions);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        agoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //agoogleMap.setMyLocationEnabled(true);
    }



    @Override
    protected void onStart() {
        super.onStart();
        map.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        map.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map.onLowMemory();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void onComplete(Task<Location> task) {
        if (task.isSuccessful()) {
            Location location = task.getResult();
            //Toast.makeText(this, String.valueOf(location.getLongitude()), Toast.LENGTH_SHORT).show();
            gotoLocation(location.getLatitude(), location.getLongitude());
            LONGITUDE = location.getLongitude();
            LATITUDE = location.getLatitude();
        }
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        incident = parent.getItemAtPosition(position).toString();
        Incident.setText(incident);
        //setImage according to incident.
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        incident = null;
        Toast.makeText(this, "Please Select an Option.", Toast.LENGTH_SHORT).show();
    }
}