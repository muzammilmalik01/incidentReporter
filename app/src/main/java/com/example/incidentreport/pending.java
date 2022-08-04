package com.example.incidentreport;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class pending extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    RecyclerView pendinglist;
    reportAdapter adapter;

    public pending() {
            }

    public static pending newInstance(String param1, String param2) {
        pending fragment = new pending();
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

        View view =  inflater.inflate(R.layout.fragment_pending, container, false);
        pendinglist = (RecyclerView)view.findViewById(R.id.pendinglist);
        pendinglist.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Report> options =
                new FirebaseRecyclerOptions.Builder<Report>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("reports").child("pending"), Report.class)
                        .build();
        adapter = new reportAdapter(options);
        pendinglist.setAdapter(adapter);
        return  view;

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    public  void onPause() {
        super.onPause();
        adapter.stopListening();
    }
    @Override
    public  void onResume() {
        super.onResume();
        adapter.startListening();
    }

}