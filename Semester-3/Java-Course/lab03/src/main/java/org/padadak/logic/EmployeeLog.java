package org.padadak.logic;

import org.padadak.data.Database;
import org.padadak.model.Reservation;
import org.padadak.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class EmployeeLog {

    public static String status(String name) {
        Database db = new Database();
        List<User> users = db.selectUsers();
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)){
                db.closeConnection();
                return "Name and Surname: " + user.getName() + "\nRole: " + user.getRole() + "\n-------------------------" ;
            }
        }
        db.closeConnection();
        return "Error";
    }

    public static String showEmployees() {
        Database db = new Database();
        List<User> users = db.selectUsers();
        String output = "List of Employees:";

        for (User user : users) {
            if (user.getRole().equalsIgnoreCase("Employee")){
                output +=  "\n - " + user.getName();
            }
        }
        db.closeConnection();
        if (output.equals("List of Employees:"))
        {
            output += "Nobody";
        }
        return output;
    }

    public static Boolean checkName(String name) {
        Database db = new Database();
        List<User> users = db.selectUsers();
        for (User user : users) {
            if (user.getRole().equalsIgnoreCase("Employee") && user.getName().equalsIgnoreCase(name)){
                db.closeConnection();
                return true;
            }
        }
        db.closeConnection();
        return false;
    }

    public static String showReservation(String name) {
        Database db = new Database();
        List<Reservation> reservations = db.selectReservations();
        List<User> users = db.selectUsers();
        String output = "Lista of reservations:" + "\n";
        User user_found = null;

        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)) {
                user_found = user;
                break;
            }
        }

        for (Reservation r : reservations)
            if (r.getEmployeeId() == user_found.getId())
                output += r.toString() + "\n";
        db.closeConnection();

        if (output.equals(""))
        {
            return "No reservations";
        }
        return output;
    }


    public static Boolean completeReservation(String name, int id) {
        Database db = new Database();
        List<Reservation> reservations = db.selectReservations();
        List<User> users = db.selectUsers();
        User user_found = null;

        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)) {
                user_found = user;
                break;
            }
        }

        for (Reservation r : reservations) {
            LocalDateTime appointment = LocalDateTime.of(LocalDate.parse(r.getDate()), LocalTime.parse(r.getTime()));
            if (r.getEmployeeId() == user_found.getId() && r.getId() == id && db.getNow().isAfter(appointment.plusMinutes(30))) {
                if (db.didReservation(id)) {
                    db.closeConnection();
                    return true;
                }
            }
        }
        db.closeConnection();
        return false;
    }
}
