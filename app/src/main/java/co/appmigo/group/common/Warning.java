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
    private User usertoregister;
    private Location localization;
    private Date dateIncident;
    private Date dateUpdate;
    private String desciption;

    public Warning() {
    }

    public Warning(DocumentSnapshot data) {

        this.type = data.getString("type");
        this.name = data.getString("name");
        this.category = data.getString("category");
        this.level = data.getString("level");

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

    public User getUsertoregister() {
        return usertoregister;
    }

    public void setUsertoregister(User usertoregister) {
        this.usertoregister = usertoregister;
    }

    public Location getLocalization() {
        return localization;
    }

    public void setLocalization(Location localization) {
        this.localization = localization;
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
