package co.appmigo.group.common;

import java.util.Date;

public class Warning {
    private String type;
    private String name;
    private String category;
    private String level ;
    private User usertoregister;
    private Localization localization;
    private Date dateIncident;
    private Date dateUpdate;

    public Warning() {
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

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
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
}
