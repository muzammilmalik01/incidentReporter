package com.example.incidentreport;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class fullreport extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    String Reportnumber;
    String Status;
    String rights;

    public fullreport() {

    }
    public fullreport(String Reportnumber,String Status,String rights) {
        this.Reportnumber = Reportnumber;
        this.Status = Status;
        this.rights = rights;
    }
    public static fullreport newInstance(String param1, String param2) {
        fullreport fragment = new fullreport();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fullreport, container, false);
        Button ACCEPT = view.findViewById(R.id.accept);
        Button REJECT = view.findViewById(R.id.reject);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                AppCompatActivity activity = (AppCompatActivity)getContext();
                Toast.makeText(activity, "Back is pressed", Toast.LENGTH_SHORT).show();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentframe, new pending()).commit();
            }
        };
        FirebaseDatabase rootnode = FirebaseDatabase.getInstance();
        DatabaseReference ref;
        if(rights.equals("user"))
        {
            ACCEPT.setVisibility(View.INVISIBLE);
            REJECT.setVisibility(View.INVISIBLE);
        }
        if(Status.equals("pending"))
        {
            ref = rootnode.getReference("reports").child("pending").child(Reportnumber);
        }
        else if(Status.equals("approved"))
        {
            ref = rootnode.getReference("reports").child("approved").child(Reportnumber);
            ACCEPT.setVisibility(View.INVISIBLE);
            REJECT.setVisibility(View.INVISIBLE);
        }
        else
        {
            ref = rootnode.getReference("reports").child("rejected").child(Reportnumber);
            ACCEPT.setVisibility(View.INVISIBLE);
            REJECT.setVisibility(View.INVISIBLE);
        }
        TextView REPORTNUMBER = view.findViewById(R.id.ReportID);
        TextView INCIDENT = view.findViewById(R.id.Incident);
        TextView D = view.findViewById(R.id.d);
        TextView DESCRIPTION =view.findViewById(R.id.Description);
        TextView LOCATION = view.findViewById(R.id.latlon);
        ImageView emergencyservices = view.findViewById(R.id.emergencyservice);
        ImageView rstatus = view.findViewById(R.id.status);


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String date = snapshot.child("date").getValue(String.class);
                    String time = snapshot.child("time").getValue(String.class);
                    String description = snapshot.child("description").getValue(String.class);
                    String incident = snapshot.child("incident").getValue(String.class);
                    String latitude = snapshot.child("latitude").getValue(String.class);
                    String longitude = snapshot.child("longitude").getValue(String.class);
                    String reportnumber = snapshot.child("reportnumber").getValue(String.class);
                    String status = snapshot.child("status").getValue(String.class);
                    String image = snapshot.child("image").getValue(String.class);
                    if(image.equals("hehe"))
                    {
                        ImageView i =view.findViewById(R.id.image);
                        i.setImageResource(R.drawable.nopicture);
                    }
                    else if(!image.equals("hehe"))
                    {
                        ImageView i =view.findViewById(R.id.image);
                        i.setImageResource(R.drawable.loading);
                    }
                    ImageView icons = view.findViewById(R.id.iicon);
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();
                    StorageReference pathReference = storageRef.child("reports").child(reportnumber);
                    if(!image.equals("hehe"))
                    {

                        pathReference.getBytes(9000*9000).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
                                ImageView i =view.findViewById(R.id.image);
                                i.setImageBitmap(bitmap);
                            }
                        });
                    }
                    D.setText("Date: "+ date +"\nTime: "+time);
                    DESCRIPTION.setText(description);
                    INCIDENT.setText(incident);
                    REPORTNUMBER.setText("Report ID# "+reportnumber);
                    if(incident.equals("Fire"))
                    {
                        icons.setImageResource(R.drawable.fire);
                        emergencyservices.setImageResource(R.drawable.firestation);
                    }
                    else if(incident.equals("Snatching"))
                    {
                        icons.setImageResource(R.drawable.snatch);
                        emergencyservices.setImageResource(R.drawable.police);
                    }
                    else if(incident.equals("Robbery"))
                    {
                        icons.setImageResource(R.drawable.robbery);
                        emergencyservices.setImageResource(R.drawable.police);

                    }
                    else if(incident.equals("Road Accident"))
                    {
                        icons.setImageResource(R.drawable.ca);
                        emergencyservices.setImageResource(R.drawable.star);

                    }
                    else if(incident.equals("Blast"))
                    {
                        icons.setImageResource(R.drawable.blast);
                        emergencyservices.setImageResource(R.drawable.rescue);

                    }
                    else if(incident.equals("Fight"))
                    {
                        icons.setImageResource(R.drawable.fight);
                        emergencyservices.setImageResource(R.drawable.police);

                    }
                    else if(incident.equals("Domestic Accident"))
                    {
                        icons.setImageResource(R.drawable.domestic);
                        emergencyservices.setImageResource(R.drawable.star);

                    }
                    else if(incident.equals("Animal Rescue"))
                    {
                        icons.setImageResource(R.drawable.pet);
                        emergencyservices.setImageResource(R.drawable.star);

                    }
                    else if(incident.equals("Murder"))
                    {
                        icons.setImageResource(R.drawable.murder);
                        emergencyservices.setImageResource(R.drawable.police);

                    }

                    if(status.equals("pending"))
                    {
                        rstatus.setImageResource(R.drawable.pending);
                    }
                    else if(status.equals("approved"))
                    {
                        rstatus.setImageResource(R.drawable.approved);
                    }
                    else
                    {
                        rstatus.setImageResource(R.drawable.rejected);
                    }
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        List<Address> listaddress = geocoder.getFromLocation(Double.valueOf(latitude),Double.valueOf(longitude),1);
                        if(listaddress.size()>0)
                        {
                            listaddress.get(0).getMaxAddressLineIndex();
                            LOCATION.setText("Location: "+ listaddress.get(0).getAddressLine(0));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (!snapshot.exists())
                {
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addListenerForSingleValueEvent(eventListener);
        ACCEPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            String date = snapshot.child("date").getValue(String.class);
                            String time = snapshot.child("time").getValue(String.class);
                            String description = snapshot.child("description").getValue(String.class);
                            String incident = snapshot.child("incident").getValue(String.class);
                            String latitude = snapshot.child("latitude").getValue(String.class);
                            String longitude = snapshot.child("longitude").getValue(String.class);
                            String image = snapshot.child("image").getValue(String.class);
                            String reportnumber = snapshot.child("reportnumber").getValue(String.class);


                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myref = database.getReference("reports").child("approved");
                            myref.child(reportnumber).child("date").setValue(date);
                            myref.child(reportnumber).child("time").setValue(time);
                            myref.child(reportnumber).child("longitude").setValue(longitude);
                            myref.child(reportnumber).child("latitude").setValue(latitude);
                            myref.child(reportnumber).child("description").setValue(description);
                            myref.child(reportnumber).child("incident").setValue(incident);
                            myref.child(reportnumber).child("reportnumber").setValue(reportnumber);
                            myref.child(reportnumber).child("status").setValue("approved");
                            myref.child(reportnumber).child("image").setValue(image);
                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            DatabaseReference REF = database.getReference("reports").child("pending").child(reportnumber);
                            REF.removeValue();
                            myref = database.getReference("reports").child("status");
                            myref.child(reportnumber).child("status").setValue("approved");
                            Toast.makeText(getActivity(), "Report Approved", Toast.LENGTH_SHORT).show();
                            if(rights.equals("admin"))
                            {
                                Intent i = new Intent(getActivity(),adminactivity.class);
                                startActivity(i);
                            }


                            //AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            //activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentframe, new pending()).addToBackStack(null).commit();

                        }
                        else if (!snapshot.exists())
                        {

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                ref.addListenerForSingleValueEvent(eventListener);

            }
        });

        REJECT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            String date = snapshot.child("date").getValue(String.class);
                            String time = snapshot.child("time").getValue(String.class);
                            String description = snapshot.child("description").getValue(String.class);
                            String incident = snapshot.child("incident").getValue(String.class);
                            String latitude = snapshot.child("latitude").getValue(String.class);
                            String longitude = snapshot.child("longitude").getValue(String.class);
                            String reportnumber = snapshot.child("reportnumber").getValue(String.class);
                            String image = snapshot.child("image").getValue(String.class);


                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myref = database.getReference("reports").child("rejected");
                            myref.child(reportnumber).child("date").setValue(date);
                            myref.child(reportnumber).child("time").setValue(time);
                            myref.child(reportnumber).child("longitude").setValue(longitude);
                            myref.child(reportnumber).child("latitude").setValue(latitude);
                            myref.child(reportnumber).child("description").setValue(description);
                            myref.child(reportnumber).child("incident").setValue(incident);
                            myref.child(reportnumber).child("reportnumber").setValue(reportnumber);
                            myref.child(reportnumber).child("status").setValue("rejected");
                            myref.child(reportnumber).child("image").setValue(image);


                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            DatabaseReference REF = database.getReference("reports").child("pending").child(reportnumber);
                            REF.removeValue();
                            myref = database.getReference("reports").child("status");
                            myref.child(reportnumber).child("status").setValue("rejected");
                            Toast.makeText(getActivity(), "Report Rejected", Toast.LENGTH_SHORT).show();
                            if(rights.equals("admin"))
                            {
                                Intent i = new Intent(getActivity(),adminactivity.class);
                                startActivity(i);
                            }
                            //AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            //activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentframe, new pending()).addToBackStack(null).commit();


                        }
                        else if (!snapshot.exists())
                        {

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                ref.addListenerForSingleValueEvent(eventListener);
            }
        });
        return  view;
    }
}