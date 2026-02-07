package org.padadak.model;

public class Ring {
    private int id;
    private float outerRadius;
    private float innerRadius;
    private float height;

    public Ring(int id, float outerRadius, float innerRadius, float height) {
        this.id = id;
        this.outerRadius = outerRadius;
        this.innerRadius = innerRadius;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public float getOuterRadius() {
        return outerRadius;
    }

    public float getInnerRadius() {
        return innerRadius;
    }

    public float getHeight() {
        return height;
    }
}
