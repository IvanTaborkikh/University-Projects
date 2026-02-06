package org.padadak.logika;

import org.padadak.classes.Osoba;
import org.padadak.classes.Rezerwacja;
import org.padadak.data.Baza;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class EmployeeLog {

    public static String status(String name) {
        Baza b = new Baza();
        List<Osoba> osoby = b.selectOsoby();
        for (Osoba osoba : osoby) {
            if (osoba.getImie().equalsIgnoreCase(name)){
                b.closeConnection();
                return "Imie i Nazwisko: " + osoba.getImie() + "\nRola: " + osoba.getRola() + "\n-------------------------" ;
            }
        }
        b.closeConnection();
        return "Error";
    }

    public static String showUsers() {
        Baza b = new Baza();
        List<Osoba> osoby = b.selectOsoby();
        String output = "List of Employees:";
        for (Osoba osoba : osoby) {
            if (osoba.getRola().equalsIgnoreCase("Employee")){
                output +=  "\n - " + osoba.getImie();
            }
        }
        b.closeConnection();
        if (output.equals("List of Employees:"))
        {
            output += "Nobody";
        }
        return output;
    }

    public static Boolean checkName(String name) {
        Baza b = new Baza();
        List<Osoba> osoby = b.selectOsoby();
        for (Osoba osoba : osoby) {
            if (osoba.getRola().equalsIgnoreCase("Employee") && osoba.getImie().equalsIgnoreCase(name)){
                b.closeConnection();
                return true;
            }
        }
        b.closeConnection();
        return false;
    }

    public static String showRezerwacji(String name) {
        Baza b = new Baza();
        List<Rezerwacja> rezer = b.selectRezerwacji();
        List<Osoba> osoby = b.selectOsoby();
        String rezerwacji = "";
        rezerwacji += "Lista rezer:" + "\n";
        Osoba found = null;

        for (Osoba osoba : osoby) {
            if (osoba.getImie().equalsIgnoreCase(name)) {
                found = osoba;
                break;
            }
        }

        for (Rezerwacja r : rezer)
            if (r.getNr_pracownika() == found.getNr())
                rezerwacji += r.toString() + "\n";
        b.closeConnection();

        if (rezerwacji.equals(""))
        {
            return "Brak Rezerwacji";
        }
        return rezerwacji;
    }


    public static Boolean didRezerwacja(String name, int nr) {
        Baza b = new Baza();
        List<Rezerwacja> rezer = b.selectRezerwacji();
        List<Osoba> osoby = b.selectOsoby();
        Osoba osoba = null;
        for (Osoba o : osoby) {
            if (o.getImie().equalsIgnoreCase(name)){
                osoba = o;
            }
        }

        for (Rezerwacja r : rezer) {
            LocalDateTime appointment = LocalDateTime.of(LocalDate.parse(r.getData()), LocalTime.parse(r.getGodzina()));
            if (r.getNr_pracownika() == osoba.getNr() && r.getNr() == nr && b.teraz().isAfter(appointment.plusMinutes(30))) {
                if (b.didReservation(nr)) {
                    b.closeConnection();
                    return true;
                }
            }
        }
        b.closeConnection();
        return false;
    }
}
