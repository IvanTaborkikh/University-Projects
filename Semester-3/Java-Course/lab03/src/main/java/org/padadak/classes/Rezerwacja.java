package org.padadak.classes;

public class Rezerwacja {
    private int nr;
    private int nr_zakladu;
    private String data;
    private String godzina;
    private String nazwa_uslugi;
    private int nr_pracownika;
    private int nr_klienta;
    private boolean wykonano;

    public Rezerwacja(int nr, int nr_zakladu, String data, String godzina, String nazwa_uslugi, int nr_pracownika, int nr_klienta, boolean wykonano) {
        this.nr = nr;
        this.nr_zakladu = nr_zakladu;
        this.data = data;
        this.godzina = godzina;
        this.nazwa_uslugi = nazwa_uslugi;
        this.nr_pracownika = nr_pracownika;
        this.nr_klienta = nr_klienta;
        this.wykonano = wykonano;
    }

    public int getNr() {
        return nr;
    }

    public int getNr_zakladu() {
        return nr_zakladu;
    }

    public String getData() {
        return data;
    }

    public String getGodzina() {
        return godzina;
    }

    public String getNazwa_uslugi() {
        return nazwa_uslugi;
    }

    public int getNr_pracownika() {
        return nr_pracownika;
    }

    public int getNr_klienta() {
        return nr_klienta;
    }

    public boolean getWykonano() {
        return wykonano;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public void setNr_zakladu(int nr_zakladu) {
        this.nr_zakladu = nr_zakladu;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setGodzina(String godzina) {
        this.godzina = godzina;
    }

    public void setNazwa_uslugi(String nazwa_uslugi) {
        this.nazwa_uslugi = nazwa_uslugi;
    }

    public void setNr_pracownika(int nr_pracownika) {
        this.nr_pracownika = nr_pracownika;
    }

    public void setNr_klienta(int nr_klienta) {
        this.nr_klienta = nr_klienta;
    }

    public void setWykonano(boolean wykonano) {
        this.wykonano = wykonano;
    }

    @Override
    public String toString() {
        return "Number ["+nr+"] - w zakladzie("+nr_zakladu + "). Data: " +data + "-" +godzina + ". Usluga: " +nazwa_uslugi   + ". Czy wykonana: " +wykonano;
    }
}
