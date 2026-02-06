package org.padadak.logika;

import org.padadak.classes.Cennik;
import org.padadak.classes.Osoba;
import org.padadak.classes.Rezerwacja;
import org.padadak.classes.Zaklad;
import org.padadak.data.Baza;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class OwnerLog {

    public static String status(String name) {
        Baza b = new Baza();
        List<Osoba> osoby = b.selectOsoby();
        b.print();
        for (Osoba osoba : osoby) {
            if (osoba.getImie().equalsIgnoreCase(name)){
                b.closeConnection();
                return "Imie i Nazwisko: " + osoba.getImie() + "\nRola: " + osoba.getRola() + "\n-------------------------";
            }
        }
        b.closeConnection();
        return "Error";
    }

    public static String showUsers() {
        Baza b = new Baza();
        List<Osoba> osoby = b.selectOsoby();
        String output = "List of Owners:";
        for (Osoba osoba : osoby) {
            if (osoba.getRola().equalsIgnoreCase("Owner")){
                output +=  "\n - " + osoba.getImie();
            }
        }
        b.closeConnection();
        if (output.equals("List of Owners:"))
        {
            output += "Nobody";
        }
        return output;
    }

    public static Boolean checkName(String name) {
        Baza b = new Baza();
        List<Osoba> osoby = b.selectOsoby();
        for (Osoba osoba : osoby) {
            if (osoba.getRola().equalsIgnoreCase("Owner") && osoba.getImie().equalsIgnoreCase(name)){
                b.closeConnection();
                return true;
            }
        }
        b.closeConnection();
        return false;
    }

    public static void set(LocalDate date, LocalTime time){
        Baza b = new Baza();
        b.set(date, time);
        b.closeConnection();
    }

    public static String showRezerwacji() {
        Baza b = new Baza();
        List<Rezerwacja> rezer = b.selectRezerwacji();
        String rezerwacji = "";
        rezerwacji += "List of rezerwacji:" + "\n";
        for (Rezerwacja r : rezer)
            rezerwacji += r.toString() + "\n";
        b.closeConnection();

        if (rezerwacji.equals(""))
        {
            return "Brak Rezerwacji";
        }
        return rezerwacji;
    }

    public static String showCenniky() {
        Baza b = new Baza();
        List<Cennik> cenniky = b.selectCenniky();
        String ceny = "";
        ceny += "List of cost:" + "\n";
        for (Cennik c : cenniky)
            ceny += c.toString() + "\n";
        b.closeConnection();

        if (ceny.equals("List of cost:" + "\n"))
        {
            ceny += "Brak cennikow";
        }
        return ceny;
    }

    public static String changeCenna(String nazwa, int cenna) {
        Baza b = new Baza();
        List<Cennik> cenniky = b.selectCenniky();
        for (Cennik c : cenniky)
            if(c.getNazwa_uslugi().equals(nazwa))
            {
                b.changeCennu(nazwa, cenna);
                b.closeConnection();
                return "Cost was edited";
            }
        return "Incorrect name";
    }

    public static int money() {
        Baza b = new Baza();
        List<Zaklad> zak = b.selectZaklady();
        int total_money = 0;
        for (Zaklad z : zak)
            total_money += z.getMoney();

        b.closeConnection();
        return total_money;
    }

}
