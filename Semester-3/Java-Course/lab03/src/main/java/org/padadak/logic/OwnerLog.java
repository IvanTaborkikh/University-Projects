package org.padadak.logic;

import org.padadak.data.Database;
import org.padadak.model.Facility;
import org.padadak.model.Price;
import org.padadak.model.Reservation;
import org.padadak.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class OwnerLog {

    public static String status(String name) {
        Database db = new Database();
        List<User> users = db.selectUsers();
        db.printCurrentTime();
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)){
                db.closeConnection();
                return "Name and Surname: " + user.getName() + "\nRole: " + user.getRole() + "\n-------------------------";
            }
        }
        db.closeConnection();
        return "Error";
    }

    public static String showUsers() {
        Database db = new Database();
        List<User> users = db.selectUsers();
        String output = "List of Owners:";
        for (User user : users) {
            if (user.getRole().equalsIgnoreCase("Owner")){
                output +=  "\n - " + user.getName();
            }
        }
        db.closeConnection();
        if (output.equals("List of Owners:"))
        {
            output += "Nobody";
        }
        return output;
    }

    public static Boolean checkName(String name) {
        Database db = new Database();
        List<User> users = db.selectUsers();
        for (User user : users) {
            if (user.getRole().equalsIgnoreCase("Owner") && user.getName().equalsIgnoreCase(name)){
                db.closeConnection();
                return true;
            }
        }
        db.closeConnection();
        return false;
    }

    public static void setTime(LocalDate date, LocalTime time){
        Database db = new Database();
        db.setSystemTime(date, time);
        db.closeConnection();
    }

    public static String showReservation() {
        Database db = new Database();
        List<Reservation> reservations = db.selectReservations();
        String output = "List of reservations:" + "\n";
        for (Reservation r : reservations)
            output += r.toString() + "\n";
        db.closeConnection();

        if (output.equals(""))
        {
            return "No reservations found";
        }
        return output;
    }

    public static String showPrices() {
        Database db = new Database();
        List<Price> prices = db.selectPriceList();
        String output = "List of cost:" + "\n";
        for (Price c : prices)
            output += c.toString() + "\n";
        db.closeConnection();

        if (output.equals("List of cost:" + "\n"))
        {
            output += "No price lists found";
        }
        return output;
    }

    public static String changePrice(String name, int price) {
        Database db = new Database();
        List<Price> prices = db.selectPriceList();
        for (Price c : prices)
            if(c.getServiceName().equals(name))
            {
                db.updatePrice(name, price);
                db.closeConnection();
                return "Price updated successfully";
            }
        return "Incorrect service name";
    }

    public static int getTotalBalance() {
        Database db = new Database();
        List<Facility> facilities = db.selectFacilities();
        int totalBalance = 0;
        for (Facility f : facilities)
            totalBalance += f.getBalance();

        db.closeConnection();
        return totalBalance;
    }
}
