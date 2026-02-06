package org.padadak;

import org.padadak.classes.Cennik;
import org.padadak.classes.Osoba;
import org.padadak.classes.Rezerwacja;
import org.padadak.classes.Zaklad;
import org.padadak.data.Baza;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Baza b = new Baza();
//        b.insertOsoba(1, "Ivan Taborskykh", "Owner");
//        b.insertOsoba(1, "Dima Skovorodko", "Client");
//        b.insertOsoba(1, "Michael Tsoev", "Employee");
//
//        b.insertCennik("Volosy klac klac", 150);
//        b.insertCennik("Ekonom klac", 70);
//
//        b.insertRezerwacja(1, "2025-10-20", "17:00", "Volosy klac klac", 2, 3, false);
//        b.insertRezerwacja(1, "2025-10-20", "18:00", "Ekonom klac", 1, 4, true);
//
//        b.insertZaklad("Ekonom klac", 5, 1);
//        b.insertZaklad("Dorogyj klac", 10, 1);
//
//        b.insertOsoba(1, "Timoha Kartoha", "Cashier");


        b.insertOsoba(1, "Makar Stepa", "Client");
        b.insertOsoba(1, "Vasyl Stas", "Employee");

        List<Osoba> osoby = b.selectOsoby();
        List<Cennik> cenniky = b.selectCenniky();
        List<Rezerwacja> rezer = b.selectRezerwacji();
        List<Zaklad> zak = b.selectZaklady();

        System.out.println("Lista osob");
        for (Osoba osoba : osoby)
            System.out.println(osoba);
        System.out.println("Lista rezer");
        for (Rezerwacja osoba : rezer)
            System.out.println(osoba);
        System.out.println("Lista cennik");
        for (Cennik osoba : cenniky)
            System.out.println(osoba);
        System.out.println("Lista zak");
        for (Zaklad osoba : zak)
            System.out.println(osoba);

        b.closeConnection();
    }
}