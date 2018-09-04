package co.appmigo.group.common;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class User {

    String uId;
    String name;
    String lastName;
    String displayName;
    String email;
    String password;
    Uri photoUrl;
    String providerId;
    Boolean isAutenticate;
    Boolean isFirstLogin;
    Location location;
    Location lastLocation;

    public User() {
    }

    public User(String uId, String email) {
        this.uId = uId;
        this.email = email;
    }

    public User(FirebaseUser user) {
        this.uId = user.getUid();
        this.displayName = user.getDisplayName();
        this.email = user.getEmail();
        this.photoUrl = user.getPhotoUrl();
        this.providerId = user.getProviderId();
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String diaplayName) {
        this.displayName = diaplayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public Boolean getAutenticate() {
        return isAutenticate;
    }

    public void setAutenticate(Boolean autenticate) {
        isAutenticate = autenticate;
    }

    public Boolean getFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(Boolean firstLogin) {
        isFirstLogin = firstLogin;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }
}
