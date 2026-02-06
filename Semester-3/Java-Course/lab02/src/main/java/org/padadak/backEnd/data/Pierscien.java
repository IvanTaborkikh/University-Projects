package org.padadak.backEnd.data;

public class Pierscien {
    int nr;
    float promienZew;
    float promienWew;
    float wysokosc;

    public Pierscien(int nr, float promienZew, float promienWew, float wysokosc) {
        this.nr = nr;
        this.promienZew = promienZew;
        this.promienWew = promienWew;
        this.wysokosc = wysokosc;

    }

    public int getNr() {
        return nr;
    }

    public float getPromienZew() {
        return promienZew;
    }

    public float getPromienWew() {
        return promienWew;
    }

    public float getWysokosc() {
        return wysokosc;
    }
}
