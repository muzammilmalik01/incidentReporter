package com.example.incidentreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jraska.falcon.Falcon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class submissionproof extends AppCompatActivity {
        TextView reportid,Incident;
        Button done;
        ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submissionproof);
        reportid = findViewById(R.id.ReportID);
        Incident = findViewById(R.id.Incident);
        done = findViewById(R.id.done);
        image = findViewById(R.id.iicon);
        Intent i = getIntent();
        String id = i.getStringExtra("id");
        String incident = i.getStringExtra("incident");
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#1d1b1b"));
        }

        reportid.setText("Report ID# "+id);
        Incident.setText(incident);
        if(incident.equals("Fire"))
        {
            image.setImageResource(R.drawable.fire);
        }
        else if(incident.equals("Snatching"))
        {
            image.setImageResource(R.drawable.snatch);
        }
        else if(incident.equals("Robbery"))
        {
            image.setImageResource(R.drawable.robbery);
        }
        else if(incident.equals("Road Accident"))
        {
            image.setImageResource(R.drawable.ca);

        }
        else if(incident.equals("Blast"))
        {
            image.setImageResource(R.drawable.blast);
        }
        else if(incident.equals("Fight"))
        {
            image.setImageResource(R.drawable.fight);
        }
        else if(incident.equals("Domestic Accident"))
        {
            image.setImageResource(R.drawable.domestic);
        }
        else if(incident.equals("Animal Rescue"))
        {
            image.setImageResource(R.drawable.pet);
        }
        else if(incident.equals("Murder"))
        {
            image.setImageResource(R.drawable.murder);
        }
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reportscn = new Intent(submissionproof.this,MainActivity.class);
                Bitmap bitmap = Falcon.takeScreenshotBitmap(submissionproof.this);
                saveImage(bitmap);
                Toast.makeText(submissionproof.this, "A Screenshot has been saved in your device.", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(reportscn);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent reportscn = new Intent(submissionproof.this,MainActivity.class);
        Bitmap bitmap = Falcon.takeScreenshotBitmap(submissionproof.this);
        saveImage(bitmap);
        Toast.makeText(submissionproof.this, "A Screenshot has been saved in your device.", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(reportscn);
    }

    private void saveImage(Bitmap bitmap) {
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            ContentValues values = contentValues();
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + getString(R.string.app_name));
            values.put(MediaStore.Images.Media.IS_PENDING, true);

            Uri uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                try {
                    saveImageToStream(bitmap, this.getContentResolver().openOutputStream(uri));
                    values.put(MediaStore.Images.Media.IS_PENDING, false);
                    this.getContentResolver().update(uri, values, null, null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        } else {
            File directory = new File(Environment.getExternalStorageDirectory().toString() + '/' + getString(R.string.app_name));

            if (!directory.exists()) {
                directory.mkdirs();
            }
            String fileName = System.currentTimeMillis() + ".png";
            File file = new File(directory, fileName);
            try {
                saveImageToStream(bitmap, new FileOutputStream(file));
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
    private ContentValues contentValues() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        }
        return values;
    }

    private void saveImageToStream(Bitmap bitmap, OutputStream outputStream) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
















}