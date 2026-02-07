package org.padadak.logic;

import org.padadak.data.Database;
import org.padadak.model.Facility;
import org.padadak.model.Price;
import org.padadak.model.Reservation;
import org.padadak.model.User;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ClientLog {

    public static String status(String name) {
        Database db = new Database();
        List<User> users = db.selectUsers();
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)){
                db.closeConnection();
                return "Name and Surname: " + user.getName() + "\nRole: " + user.getRole() + "\n-------------------------";
            }
        }
        db.closeConnection();
        return "Status Error";
    }

    public static String showClients() {
        Database db = new Database();
        List<User> users = db.selectUsers();
        StringBuilder output = new StringBuilder("List of Clients:");

        for (User user : users) {
            if (user.getRole().equalsIgnoreCase("Client")){
                output.append("\n - " + user.getName());
            }
        }
        db.closeConnection();
        if (users.isEmpty())
        {
            output.append(" Nobody");
        }
        return output.toString();
    }

    public static Boolean checkName(String name) {
        Database db = new Database();
        List<User> users = db.selectUsers();
        for (User user : users) {
            if (user.getRole().equalsIgnoreCase("Client") && user.getName().equalsIgnoreCase(name)){
                db.closeConnection();
                return true;
            }
        }
        db.closeConnection();
        return false;
    }

    public static String showReservations(String me) {
        Database db = new Database();
        List<Reservation> reservations = db.selectReservations();
        List<User> users = db.selectUsers();
        User user = null;

        for (User u : users) {
            if (u.getName().equalsIgnoreCase(me)){
                user = u;
            }
        }

        String output = "List of reservations:" + "\n";

        for (Reservation r : reservations)
            if (r.getClientId() == user.getId())
            {
                output += r.toString() + "\n";
            }

        db.closeConnection();

        if (output.equals("List of reservations:" + "\n"))
        {
            db.closeConnection();
            output += "No reservations found";
        }
        db.closeConnection();
        return output;
    }

    public static String showFacilities() {
        Database db = new Database();
        List<Facility> facilities = db.selectFacilities();
        String output = "List of facilities:\n";

        for (Facility f : facilities)
            output += " - " + f.getName() + "\n";

        db.closeConnection();

        return output;
    }

    public static String showPrices() {
        Database db = new Database();
        List<Price> prices = db.selectPriceList();
        String output = "List of prices:\n";

        for (Price pr : prices)
            output += " - " + pr.getServiceName() + " cost: " + pr.getPriceValue() + "\n";

        db.closeConnection();

        return output;
    }

    public static String showEmployee(String facilityInput) {
        Database db = new Database();
        List<User> users = db.selectUsers();
        List<Facility> facilities = db.selectFacilities();
        Facility facility = null;
        String output = "";
        try {
            for (Facility f : facilities)
                if (f.getName().equals(facilityInput))
                    facility = f;


            output  += "Who would you like to book an appointment with?\nEmployee list:\n";


            for (User u : users)
                if (u.getFacilityId() == facility.getId() && u.getRole().equals("Employee"))
                    output  += u.getName() + "\n";

            db.closeConnection();
            return output ;
        } catch (Exception e) {
            output  = "Facility does not exist";
            db.closeConnection();
            return output ;
        }
    }

    public static boolean checkEmployee(String employee, String facilityInput) {
        Database db = new Database();
        List<User> users = db.selectUsers();
        List<Facility> facilities = db.selectFacilities();
        Facility facility = null;

        try {
            for (Facility f : facilities)
                if (f.getName().equals(facilityInput))
                    facility = f;

            for (User u : users)
                if (u.getFacilityId() == facility.getId() && u.getRole().equals("Employee") && u.getName().equals(employee)){
                    db.closeConnection();
                    return true;
                }

            db.closeConnection();
            return false;
        } catch (Exception e) {
            db.closeConnection();
            return false;
        }
    }

    public static boolean checkServiceName(String serviceNameInput) {
        Database db = new Database();
        List<Price> prices = db.selectPriceList();

        try {
            for (Price p : prices)
                if (p.getServiceName().equals(serviceNameInput)) {
                    db.closeConnection();
                    return true;
                }

            db.closeConnection();
            return false;
        } catch (Exception e) {
            db.closeConnection();
            return false;
        }
    }

    public static int checkTime(String date, String time, String facilityInput, String Employee) {
        Database db = new Database();
        List<Reservation> reservation = db.selectReservations();
        List<User> users = db.selectUsers();
        List<Facility> facilities = db.selectFacilities();

        LocalDate date_in;
        LocalTime time_in;
        User userFound = null;
        Facility facilityFound = null;

        try {
            date_in = LocalDate.parse(date);
        } catch (Exception e) {
            db.closeConnection();
            return 1;
        }

        try {
            time_in = LocalTime.parse(time);

            if (time_in.isBefore(LocalTime.of(9, 0)) || time_in.isAfter(LocalTime.of(18, 0))) {
                db.closeConnection();
                return 3;
            }

            if (time_in.getMinute() % 15 != 0) {
                db.closeConnection();
                return 4;
            }

        } catch (Exception e) {
            db.closeConnection();
            return 2;
        }

        LocalDateTime appointment = LocalDateTime.of(date_in, time_in);

        if (!appointment.isAfter(db.getNow())) {
            db.closeConnection();
            return 5;
        }

        for (Facility f : facilities) {
            if (f.getName().equalsIgnoreCase(facilityInput)) {
                facilityFound = f;
                break;
            }
        }

        for (User u : users) {
            if (u.getName().equalsIgnoreCase(Employee)
                    && u.getRole().equalsIgnoreCase("Employee") && u.getFacilityId() == facilityFound.getId()) {
                userFound = u;
                break;
            }
        }

        for (Reservation r : reservation) {
            if (r.getFacilityId() == facilityFound.getId()
                    && r.getEmployeeId() == userFound.getId()
                    && r.getDate().equals(date.toString())) {

                long diff = Math.abs(Duration.between(LocalTime.parse(r.getTime()), time_in).toMinutes());

                if (r.getTime().equals(time_in.toString())) {
                    db.closeConnection();
                    return 6;
                }

                if (diff < 30) {
                    db.closeConnection();
                    return 7;
                }
            }
        }
        db.closeConnection();
        return 0;
    }

    public static boolean reservationTime(String facilityInput, String Employee, String date, String time, String serviceNameInput, String name) {
        Database db = new Database();
        List<User> users = db.selectUsers();
        List<Facility> facilities = db.selectFacilities();
        User userFound = null;
        User client = null;
        Facility facilityFound = null;

        for (Facility f : facilities) {
            if (f.getName().equalsIgnoreCase(facilityInput)) {
                facilityFound = f;
                break;
            }
        }

        for (User u : users) {
            if (u.getName().equalsIgnoreCase(Employee)
                    && u.getRole().equalsIgnoreCase("Employee") && u.getFacilityId() == facilityFound.getId()) {
                userFound = u;
                break;
            }
        }

        for (User u : users) {
            if (u.getName().equalsIgnoreCase(name)) {
                client = u;
                break;
            }
        }

        db.insertReservation(facilityFound.getId(), date, time, serviceNameInput, userFound.getId(), client.getId(), false);
        db.closeConnection();
        return true;
    }

    public static Boolean deleteReservation(String name, int id) {
        Database db = new Database();
        List<Reservation> reservations = db.selectReservations();
        List<User> users = db.selectUsers();
        User user = null;
        for (User u : users) {
            if (u.getName().equalsIgnoreCase(name)){
                user = u;
            }
        }

        for (Reservation r : reservations)
            if (r.getClientId() == user.getId() && r.getId() == id)
            {
                if (db.deleteReservationforClient(name, id))
                {
                    db.closeConnection();
                    return true;
                }
            }
        db.closeConnection();
        return false;
    }

}
