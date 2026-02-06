package org.padadak.classes;

public class Osoba {
    private int nr;
    private int nr_zakladu;
    private String imie;
    private String rola;

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public int getNr_zakladu() {
        return nr_zakladu;
    }

    public void setNr_zakladu(int nr_zakladu) {
        this.nr_zakladu = nr_zakladu;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getRola() {
        return rola;
    }

    public void setRola(String rola) {
        this.rola = rola;
    }

    public Osoba(int nr, int nr_zakladu, String imie, String rola) {
        this.nr = nr;
        this.nr_zakladu = nr_zakladu;
        this.imie = imie;
        this.rola = rola;
    }

    @Override
    public String toString() {
        return "["+nr+"] - "+nr_zakladu+" "+imie+" - "+rola;
    }
}
