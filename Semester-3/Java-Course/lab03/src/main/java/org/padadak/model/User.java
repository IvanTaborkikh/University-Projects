package org.padadak.model;

import org.padadak.data.Database;

import java.util.List;

public class User {
    private int id;
    private int facilityId;
    private String name;
    private String role;

    public User(int id, int facilityId, String name, String role) {
        this.id = id;
        this.facilityId = facilityId;
        this.name = name;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        Database db = new Database();
        List<Facility> facilities = db.selectFacilities();
        db.closeConnection();
        Facility found = null;
        String facilityName = null;


        for (Facility i : facilities)
            if (i.getId() == this.facilityId) {
                found = i;
                break;
            }

        if (found != null) {
            facilityName = found.getName();
        }

        return "- ID["+ id +"] - ("+ facilityName +") "+ name +" - "+ role;
    }
}
