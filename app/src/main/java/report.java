import com.google.android.gms.maps.model.LatLng;

public class report {
    String reportnumber,incidident,description,date,time;
    LatLng latLng;

    public report(String reportnumber, String incidident, String description, String date, String time, LatLng latLng) {
        this.reportnumber = reportnumber;
        this.incidident = incidident;
        this.description = description;
        this.date = date;
        this.time = time;
        this.latLng = latLng;
    }
}
