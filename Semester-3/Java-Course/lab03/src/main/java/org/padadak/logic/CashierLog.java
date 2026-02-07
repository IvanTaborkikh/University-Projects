package org.padadak.logic;

import org.padadak.data.Database;
import org.padadak.model.Price;
import org.padadak.model.Reservation;
import org.padadak.model.User;

import java.util.List;

public class CashierLog {

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
        return "Error";
    }

    public static String showCashiers() {
        Database db = new Database();
        List<User> users = db.selectUsers();
        String output = "List of Cashiers:";
        for (User user : users) {
            if (user.getRole().equalsIgnoreCase("Cashier")){
                output +=  "\n - " + user.getName();
            }
        }
        db.closeConnection();
        if (output.equals("List of Cashiers:"))
        {
            output += "Nobody";
        }
        return output;
    }

    public static Boolean checkName(String name) {
        Database db = new Database();
        List<User> users = db.selectUsers();
        for (User user : users) {
            if (user.getRole().equalsIgnoreCase("Cashier") && user.getName().equalsIgnoreCase(name)){
                db.closeConnection();
                return true;
            }
        }
        db.closeConnection();
        return false;
    }

    public static String showReservation() {
        Database db = new Database();
        List<Reservation> reservations = db.selectReservations();
        StringBuilder output = new StringBuilder("List of reservations:\n");
        for (Reservation r : reservations)
            output.append(r.toString() + "\n");
        db.closeConnection();

        if (reservations.isEmpty())
        {
            return "No reservations";
        }
        return output.toString();
    }

    public static boolean delReservation(int nr) {
        Database db = new Database();
        List<Reservation> reservations = db.selectReservations();
        List<Price> prices = db.selectPriceList();
        String serviceName;

        for (Reservation r : reservations) {
            if (r.getId() == nr && r.getCompleted()) {
                if (db.deleteReservation(nr)) {
                    serviceName = r.getServiceName();
                    for (Price c : prices) {
                        if (c.getServiceName().equals(serviceName)) {
                            db.addMoney(r.getFacilityId(), c.getPriceValue());
                        }
                    }
                    db.closeConnection();
                    return true;
                }
            }
        }
        db.closeConnection();
        return false;
    }
}
