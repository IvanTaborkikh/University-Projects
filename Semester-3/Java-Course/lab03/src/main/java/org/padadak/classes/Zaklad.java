package org.padadak.classes;

public class Zaklad {
    private int nr;
    private String nazwa;
    private int liczba_stanowisk;
    private int numer_wlasiciela;
    private int money;

    public Zaklad(int nr, String nazwa, int liczba_stanowisk, int numer_wlasiciela, int money) {
        this.nr = nr;
        this.nazwa = nazwa;
        this.liczba_stanowisk = liczba_stanowisk;
        this.numer_wlasiciela = numer_wlasiciela;
        this.money = money;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getLiczba_stanowisk() {
        return liczba_stanowisk;
    }

    public void setLiczba_stanowisk(int liczba_stanowisk) {
        this.liczba_stanowisk = liczba_stanowisk;
    }

    public int getNumer_wlasiciela() {
        return numer_wlasiciela;
    }

    public void setNumer_wlasiciela(int numer_wlasiciela) {
        this.numer_wlasiciela = numer_wlasiciela;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "- "+nazwa;
    }
}
