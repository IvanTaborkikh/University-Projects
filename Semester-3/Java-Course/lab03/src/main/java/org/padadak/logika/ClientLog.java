package org.padadak.logika;

import org.padadak.classes.Cennik;
import org.padadak.classes.Osoba;
import org.padadak.classes.Rezerwacja;
import org.padadak.classes.Zaklad;
import org.padadak.data.Baza;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ClientLog {

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
        return "Status Error";
    }

    public static String showUsers() {
        Baza b = new Baza();
        List<Osoba> osoby = b.selectOsoby();
        String output = "List of Clients:";
        for (Osoba osoba : osoby) {
            if (osoba.getRola().equalsIgnoreCase("Client")){
                output +=  "\n - " + osoba.getImie();
            }
        }
        b.closeConnection();
        if (output.equals("List of Clients:"))
        {
            output += "Nobody";
        }
        return output;
    }

    public static Boolean checkName(String name) {
        Baza b = new Baza();
        List<Osoba> osoby = b.selectOsoby();
        for (Osoba osoba : osoby) {
            if (osoba.getRola().equalsIgnoreCase("Client") && osoba.getImie().equalsIgnoreCase(name)){
                b.closeConnection();
                return true;
            }
        }
        b.closeConnection();
        return false;
    }

    public static String showRezerwacji(String me) {
        Baza b = new Baza();
        List<Rezerwacja> rezer = b.selectRezerwacji();
        List<Osoba> osoby = b.selectOsoby();
        Osoba osoba = null;
        for (Osoba o : osoby) {
            if (o.getImie().equalsIgnoreCase(me)){
                osoba = o;
            }
        }

        String rezerwacji = "";
        rezerwacji += "List of rezerwacji:" + "\n";
        for (Rezerwacja r : rezer)
            if (r.getNr_klienta() == osoba.getNr())
            {
                rezerwacji += r.toString() + "\n";
            }

        b.closeConnection();

        if (rezerwacji.equals("List of rezerwacji:" + "\n"))
        {
            b.closeConnection();
            rezerwacji += "Brak Rezerwacji";
        }
        b.closeConnection();
        return rezerwacji;
    }

    public static String showZaklady() {
        Baza b = new Baza();
        List<Zaklad> zak = b.selectZaklady();
        String zaklady = "";
        zaklady += "List of zaklads:\n";

        for (Zaklad z : zak)
            zaklady += " - " + z.getNazwa() + "\n";

        b.closeConnection();

        return zaklady;
    }

    public static String showCennik() {
        Baza b = new Baza();
        List<Cennik> cenniky = b.selectCenniky();
        String zaklady = "";
        zaklady += "List of Uslugs:\n";

        for (Cennik z : cenniky)
            zaklady += " - " + z.getNazwa_uslugi() + " cost: " + z.getCena() + "\n";

        b.closeConnection();

        return zaklady;
    }

    public static String showEmployee(String zaklad) {
        Baza b = new Baza();
        List<Osoba> osoby = b.selectOsoby();
        List<Zaklad> zak = b.selectZaklady();
        Zaklad za = null;
        String os= "";
        try {
            for (Zaklad z : zak)
                if (z.getNazwa().equals(zaklad))
                    za = z;


            os += "Who would you like to book an appointment with?\nEmployee list:\n";


            for (Osoba o : osoby)
                if (o.getNr_zakladu() == za.getNr() && o.getRola().equals("Employee"))
                    os += o.getImie() + "\n";

            b.closeConnection();
            return os;
        } catch (Exception e) {
            os = "Nie istnieje takiego zakladu";
            b.closeConnection();
            return os;
        }
    }

    public static boolean checkEmployee(String employee, String zaklad) {
        Baza b = new Baza();
        List<Osoba> osoby = b.selectOsoby();
        List<Zaklad> zak = b.selectZaklady();
        Zaklad za = null;
        try {
            for (Zaklad z : zak)
                if (z.getNazwa().equals(zaklad))
                    za = z;

            for (Osoba o : osoby)
                if (o.getNr_zakladu() == za.getNr() && o.getRola().equals("Employee") && o.getImie().equals(employee)){
                    b.closeConnection();
                    return true;
                }

            b.closeConnection();
            return false;
        } catch (Exception e) {
            b.closeConnection();
            return false;
        }
    }

    public static boolean checkUsluga(String usluga) {
        Baza b = new Baza();
        List<Cennik> cenniky = b.selectCenniky();

        try {
            for (Cennik o : cenniky)
                if (o.getNazwa_uslugi().equals(usluga)) {
                    b.closeConnection();
                    return true;
                }

            b.closeConnection();
            return false;
        } catch (Exception e) {
            b.closeConnection();
            return false;
        }
    }

    public static int checkTime(String date, String time, String zaklad, String Employee) {
        Baza b = new Baza();
        List<Rezerwacja> rezer = b.selectRezerwacji();
        List<Osoba> osoby = b.selectOsoby();
        List<Zaklad> zak = b.selectZaklady();

        LocalDate date_in;
        LocalTime time_in;
        Osoba found = null;
        Zaklad zak_found = null;

        try {
            date_in = LocalDate.parse(date);
        } catch (Exception e) {
            b.closeConnection();
            return 1;
        }

        try {
            time_in = LocalTime.parse(time);

            if (time_in.isBefore(LocalTime.of(9, 0)) || time_in.isAfter(LocalTime.of(18, 0))) {
                b.closeConnection();
                return 3;
            }

            if (time_in.getMinute() % 15 != 0) {
                b.closeConnection();
                return 4;
            }

        } catch (Exception e) {
            b.closeConnection();
            return 2;
        }

        LocalDateTime appointment = LocalDateTime.of(date_in, time_in);

        if (!appointment.isAfter(b.teraz())) {
            b.closeConnection();
            return 5;
        }

        for (Zaklad z : zak) {
            if (z.getNazwa().equalsIgnoreCase(zaklad)) {
                zak_found = z;
                break;
            }
        }

        for (Osoba osoba : osoby) {
            if (osoba.getImie().equalsIgnoreCase(Employee)
                    && osoba.getRola().equalsIgnoreCase("Employee") && osoba.getNr_zakladu() == zak_found.getNr()) {
                found = osoba;
                break;
            }
        }

        for (Rezerwacja r : rezer) {
            if (r.getNr_zakladu() == zak_found.getNr()
                    && r.getNr_pracownika() == found.getNr()
                    && r.getData().equals(date.toString())) {

                long diff = Math.abs(Duration.between(LocalTime.parse(r.getGodzina()), time_in).toMinutes());

                if (r.getGodzina().equals(time_in.toString())) {
                    b.closeConnection();
                    return 6;
                }

                if (diff < 30) {
                    b.closeConnection();
                    return 7;
                }
            }
        }
        b.closeConnection();
        return 0;
    }

    public static boolean RezerwacjaTime(String zaklad, String Employee, String date, String time, String nazwa_uslugi, String name) {
        Baza b = new Baza();
        List<Osoba> osoby = b.selectOsoby();
        List<Zaklad> zak = b.selectZaklady();
        Osoba found = null;
        Osoba client = null;
        Zaklad zak_found = null;

        for (Zaklad z : zak) {
            if (z.getNazwa().equalsIgnoreCase(zaklad)) {
                zak_found = z;
                break;
            }
        }

        for (Osoba osoba : osoby) {
            if (osoba.getImie().equalsIgnoreCase(Employee)
                    && osoba.getRola().equalsIgnoreCase("Employee") && osoba.getNr_zakladu() == zak_found.getNr()) {
                found = osoba;
                break;
            }
        }

        for (Osoba osoba : osoby) {
            if (osoba.getImie().equalsIgnoreCase(name)) {
                client = osoba;
                break;
            }
        }

        b.insertRezerwacja(zak_found.getNr(), date, time, nazwa_uslugi, found.getNr(), client.getNr(), false);
        b.closeConnection();
        return true;
    }

    public static Boolean deleteRezerwacja(String name, int nr) {
        Baza b = new Baza();
        List<Rezerwacja> rezer = b.selectRezerwacji();
        List<Osoba> osoby = b.selectOsoby();
        Osoba osoba = null;
        for (Osoba o : osoby) {
            if (o.getImie().equalsIgnoreCase(name)){
                osoba = o;
            }
        }

        for (Rezerwacja r : rezer)
            if (r.getNr_klienta() == osoba.getNr() && r.getNr() == nr)
            {
                if (b.deleteReservation(name, nr))
                {
                    b.closeConnection();
                    return true;
                }
            }
        b.closeConnection();
        return false;
    }

}
