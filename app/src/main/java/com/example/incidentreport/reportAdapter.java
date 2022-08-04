package com.example.incidentreport;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class reportAdapter extends FirebaseRecyclerAdapter<Report,reportAdapter.viewholder> {
    public reportAdapter(@NonNull FirebaseRecyclerOptions<Report> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewholder holder, int position, @NonNull Report model) {
    holder.reportID.setText(model.getReportnumber());
    holder.incident.setText(model.getIncident());
    holder.date.setText(model.getDate());
    holder.time.setText(model.getTime());

    holder.reportID.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentframe, new fullreport(model.getReportnumber(),model.getStatus(),"admin")).addToBackStack(null).commit();
        }
    });
        holder.incident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentframe, new fullreport(model.getReportnumber(),model.getStatus(),"admin")).addToBackStack(null).commit();
            }
        });
        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentframe, new fullreport(model.getReportnumber(),model.getStatus(),"admin")).addToBackStack(null).commit();
            }
        });
        holder.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentframe, new fullreport(model.getReportnumber(),model.getStatus(),"admin")).addToBackStack(null).commit();
            }
        });

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reportrow,parent,false);
        return new viewholder(view);
    }

    public class viewholder extends RecyclerView.ViewHolder
    {
        TextView reportID,incident,date,time,status;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            reportID = itemView.findViewById(R.id.reportID);
            incident = itemView.findViewById(R.id.incident);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }
    }
}
