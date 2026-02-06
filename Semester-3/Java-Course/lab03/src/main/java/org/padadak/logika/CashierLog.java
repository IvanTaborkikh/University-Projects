package org.padadak.logika;

import org.padadak.classes.Cennik;
import org.padadak.classes.Osoba;
import org.padadak.classes.Rezerwacja;
import org.padadak.data.Baza;

import java.util.List;

public class CashierLog {

    public static String status(String name) {
        Baza b = new Baza();
        List<Osoba> osoby = b.selectOsoby();
        for (Osoba osoba : osoby) {
            if (osoba.getImie().equalsIgnoreCase(name)){
                b.closeConnection();
                return "Name and Surname: " + osoba.getImie() + "\nRola: " + osoba.getRola() + "\n-------------------------";
            }
        }
        b.closeConnection();
        return "Error";
    }

    public static String showUsers() {
        Baza b = new Baza();
        List<Osoba> osoby = b.selectOsoby();
        String output = "List of Cashiers:";
        for (Osoba osoba : osoby) {
            if (osoba.getRola().equalsIgnoreCase("Cashier")){
                output +=  "\n - " + osoba.getImie();
            }
        }
        b.closeConnection();
        if (output.equals("List of Cashiers:"))
        {
            output += "Nobody";
        }
        return output;
    }

    public static Boolean checkName(String name) {
        Baza b = new Baza();
        List<Osoba> osoby = b.selectOsoby();
        for (Osoba osoba : osoby) {
            if (osoba.getRola().equalsIgnoreCase("Cashier") && osoba.getImie().equalsIgnoreCase(name)){
                b.closeConnection();
                return true;
            }
        }
        b.closeConnection();
        return false;
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

    public static boolean delRezerwacja(int nr) {
        Baza b = new Baza();
        List<Rezerwacja> rezer = b.selectRezerwacji();
        List<Cennik> cen = b.selectCenniky();
        String nazwa_uslugi;
        for (Rezerwacja r : rezer) {
            if (r.getNr() == nr && r.getWykonano()) {
                if (b.delReservation(nr)) {
                    nazwa_uslugi = r.getNazwa_uslugi();
                    for (Cennik c : cen) {
                        if (c.getNazwa_uslugi().equals(nazwa_uslugi)) {
                            b.addmoney(r.getNr_zakladu(), c.getCena());
                        }
                    }
                    b.closeConnection();
                    return true;
                }
            }
        }
        b.closeConnection();
        return false;
    }
}
