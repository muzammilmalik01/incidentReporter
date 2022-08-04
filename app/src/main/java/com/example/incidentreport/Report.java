package com.example.incidentreport;

import com.google.android.gms.maps.model.LatLng;

public class Report {
    String reportnumber,incident,description,date,time,latitude,longitude,status;

    public Report() {
    }

    public String getReportnumber() {
        return reportnumber;
    }

    public void setReportnumber(String reportnumber) {
        this.reportnumber = reportnumber;
    }

    public String getIncident() {
        return incident;
    }

    public void setIncident(String incident) {
        this.incident = incident;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Report(String reportnumber, String incident, String description, String date, String time, String latitude, String longitude, String status) {
        this.reportnumber = reportnumber;
        this.incident = incident;
        this.description = description;
        this.date = date;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
    }
}
