package org.padadak.apps;

import org.padadak.logic.CashierLog;

import java.util.Scanner;

public class CashierApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CashierLog logic = new CashierLog();

    public static void main(String[] args) {
        String name;
        while (true)
        {
            System.out.println(logic.showCashiers());
            System.out.println("Welcome, please enter your name or leave: ");
            name = scanner.nextLine();
            if (logic.checkName(name))
                break;
            else if (name.equals("leave"))
                return;
            else
                System.out.println("Sorry, please enter a valid name: \n");
        }

        while (true) {

            System.out.println(logic.status(name));
            System.out.print("To do(show reservations, settle up, leave): ");
            String input = scanner.nextLine();

            if (!input.toLowerCase().equalsIgnoreCase("show reservations")
                    && !input.toLowerCase().equalsIgnoreCase("settle up")
                    && !input.toLowerCase().equalsIgnoreCase("leave")){
                System.out.println("Your choice is invalid. Please try again: \n");
                continue;
            }
            if (input.toLowerCase().equalsIgnoreCase("show reservations")) {
                System.out.println(logic.showReservation());

            }else if (input.toLowerCase().equalsIgnoreCase("settle up")) {
                try {
                    System.out.println(logic.showReservation());
                    System.out.println("Give me number of reservation: ");
                    int nr = Integer.parseInt(scanner.nextLine());
                    if (!logic.delReservation(nr))
                    {
                        System.out.println("Your data is incorrect \n");
                        continue;
                    }
                    System.out.println("Reservation was settled. \n ");
                } catch (NumberFormatException e) {
                    System.out.println("Please give me integer \n");
                }
            }else if (input.toLowerCase().equalsIgnoreCase("leave")) {

                break;
            }
        }
    }
}
