package org.padadak.model;

public class Facility {
    private int id;
    private String name;
    private int stationsCount;
    private int ownerId;
    private int balance;

    public Facility(int id, String name, int stationsCount, int ownerId, int balance) {
        this.id = id;
        this.name = name;
        this.stationsCount = stationsCount;
        this.ownerId = ownerId;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int nr) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStationsCount() {
        return stationsCount;
    }

    public void setStationsCount(int stationsCount) {
        this.stationsCount = stationsCount;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int setOwnerId) {
        this.ownerId = setOwnerId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int money) {
        this.balance = money;
    }

    @Override
    public String toString() {
        return "- ID["+ id + "] " + name;
    }
}
