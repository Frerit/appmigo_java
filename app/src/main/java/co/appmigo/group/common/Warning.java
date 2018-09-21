package co.appmigo.group.common;

import android.location.Location;

import com.google.common.base.Objects;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Warning {

    private String type;
    private String name;
    private String category;
    private String level ;
    private String usertoregister;
    private double accuracy;
    private double altitude;
    private double bearing;
    private boolean complete;
    private String extras;
    private String pairValue;
    private boolean parcelled;
    private double latitude;
    private double longitude ;
    private String provider;
    private double speed;
    private double time;
    private Date dateIncident;
    private Date dateUpdate;
    private String desciption;


    public Warning() {
    }

    public Warning(String type, String name, String category, String level, String usertoregister, double accuracy, double altitude, double bearing, boolean complete, String extras, String pairValue, boolean parcelled, double latitude, double longitude, String provider, double speed, double time, Date dateIncident, Date dateUpdate, String desciption) {
        this.type = type;
        this.name = name;
        this.category = category;
        this.level = level;
        this.usertoregister = usertoregister;
        this.accuracy = accuracy;
        this.altitude = altitude;
        this.bearing = bearing;
        this.complete = complete;
        this.extras = extras;
        this.pairValue = pairValue;
        this.parcelled = parcelled;
        this.latitude = latitude;
        this.longitude = longitude;
        this.provider = provider;
        this.speed = speed;
        this.time = time;
        this.dateIncident = dateIncident;
        this.dateUpdate = dateUpdate;
        this.desciption = desciption;
    }

    public Warning(DocumentSnapshot data) {
        this.type = data.getString("type");
        this.name = data.getString("name");
        this.category = data.getString("category");
        this.level = data.getString("level");
        this.usertoregister = data.getString("usertoregister");
        this.accuracy = data.getDouble("accuracy");
        this.altitude = data.getDouble("altitude");
        this.bearing = data.getDouble("bearing");
        this.complete = data.getBoolean("complete");
        this.extras = data.getString("extras");
        this.pairValue = data.getString("pairValue");
        this.parcelled = data.getBoolean("parcelled");
        this.latitude = data.getDouble("latitude");
        this.longitude = data.getDouble("longitude");
        this.provider = data.getString("provider");
        this.speed = data.getDouble("speed");
        this.time = data.getDouble("time");
        this.dateIncident = data.getDate("dateIncident");
        this.dateUpdate = data.getDate("dateUpdate");
        this.desciption = data.getString("desciption");
    }

    public void setLocationComplete(Location locale) {
        this.accuracy = locale.getAccuracy();
        this.altitude = locale.getAltitude();
        this.bearing = locale.getBearing();
        this.latitude = locale.getLatitude();
        this.longitude = locale.getLongitude();
        this.provider = locale.getProvider();
        this.speed = locale.getSpeed();
        this.time = locale.getTime();
    }

    public String getUsertoregister() {
        return usertoregister;
    }

    public void setUsertoregister(String usertoregister) {
        this.usertoregister = usertoregister;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public String getPairValue() {
        return pairValue;
    }

    public void setPairValue(String pairValue) {
        this.pairValue = pairValue;
    }

    public boolean isParcelled() {
        return parcelled;
    }

    public void setParcelled(boolean parcelled) {
        this.parcelled = parcelled;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getDateIncident() {
        return dateIncident;
    }

    public void setDateIncident(Date dateIncident) {
        this.dateIncident = dateIncident;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }
}
