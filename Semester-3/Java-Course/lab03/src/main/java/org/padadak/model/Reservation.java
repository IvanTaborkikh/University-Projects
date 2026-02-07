package org.padadak.model;

import org.padadak.data.Database;

import java.util.List;

public class Reservation {
    private int id;
    private int facilityId;
    private String date;
    private String time;
    private String serviceName;
    private int employeeId;
    private int clientId;
    private boolean isCompleted;

    public Reservation(int id, int facilityId, String date, String time, String serviceName, int employeeId, int clientId, boolean isCompleted) {
        this.id = id;
        this.facilityId = facilityId;
        this.date = date;
        this.time = time;
        this.serviceName = serviceName;
        this.employeeId = employeeId;
        this.clientId = clientId;
        this.isCompleted = isCompleted;
    }

    public int getId() {
        return id;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getServiceName() {
        return serviceName;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getClientId() {
        return clientId;
    }

    public boolean getCompleted() {
        return isCompleted;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
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


        return "- ID["+ id +"] - ("+ facilityName + ") Date: " + date + "-" + time + " ServiceName: " + serviceName + ". Is done: " + isCompleted;
    }
}
