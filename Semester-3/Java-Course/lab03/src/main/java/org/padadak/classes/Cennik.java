package org.padadak.classes;

public class Cennik {
    private int nr;
    private String nazwa_uslugi;
    private int cena;

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getNazwa_uslugi() {
        return nazwa_uslugi;
    }

    public void setNazwa_uslugi(String nazw_uslugi) {
        this.nazwa_uslugi = nazw_uslugi;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public Cennik(int nr, String nazw_uslugi, int cena) {
        this.nr = nr;
        this.nazwa_uslugi = nazw_uslugi;
        this.cena = cena;
    }

    @Override
    public String toString() {
        return "["+nr+"] - "+nazwa_uslugi+" "+cena;
    }
}
