package org.padadak;

import org.padadak.data.Database;
import org.padadak.model.Facility;
import org.padadak.model.Price;
import org.padadak.model.Reservation;
import org.padadak.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Database db = new Database();
//
//        db.insertUser(1, "Ivan Taborskykh", "Owner");
//        db.insertUser(1, "John Smith", "Client");
//        db.insertUser(1, "Alex Rivera", "Employee");
//        db.insertUser(1, "Sarah Connor", "Cashier");
//        db.insertUser(1, "Mila Kunis", "Client");
//        db.insertUser(2, "James Bond", "Employee");
//
//        db.insertPrice("Classic Haircut", 150);
//        db.insertPrice("Beard Trim", 70);
//        db.insertPrice("Luxury Grooming", 300);
//
//        db.insertReservation(1, "2027-10-20", "17:00", "Classic Haircut", 3, 2, false);
//        db.insertReservation(1, "2026-02-06", "18:00", "Beard Trim", 6, 5, true);
//
//        db.insertFacility("Downtown Salon & Spa", 5, 1);
//        db.insertFacility("Grand Royal Barbershop", 10, 1);

        List<User> users = db.selectUsers();
        List<Price> prices = db.selectPriceList();
        List<Reservation> reservations = db.selectReservations();
        List<Facility> facilities = db.selectFacilities();

        System.out.println("Lista rezer");
        for (Reservation i : reservations)
            System.out.println(i);
        System.out.println("Lista cennik");
        for (Price i : prices)
            System.out.println(i);
        System.out.println("Lista zak");
        for (Facility i : facilities)
            System.out.println(i);
        System.out.println("Lista osob");
        for (User i : users)
            System.out.println(i);

        db.closeConnection();
    }
}