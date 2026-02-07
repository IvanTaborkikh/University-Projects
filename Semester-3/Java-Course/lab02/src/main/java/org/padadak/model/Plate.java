package org.padadak.model;

public class Plate {
    private int id;
    private float radius;

    public Plate(int id, float radius) {
        this.id = id;
        this.radius = radius;
    }

    public int getId() {
        return id;
    }

    public float getRadius() {
        return radius;
    }
}
