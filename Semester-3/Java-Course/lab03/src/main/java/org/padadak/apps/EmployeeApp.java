package org.padadak.apps;

import org.padadak.logic.EmployeeLog;

import java.util.Scanner;

public class EmployeeApp {


    private static final Scanner scanner = new Scanner(System.in);
    private static final EmployeeLog logic = new EmployeeLog();

    public static void main(String[] args) {

        String name;
        while (true)
        {
            System.out.println(logic.showEmployees());
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
            System.out.print("To do(show my reservations, complete reservation, leave): ");
            String input = scanner.nextLine();

            if (!input.toLowerCase().equalsIgnoreCase("show my reservations")
                    && !input.toLowerCase().equalsIgnoreCase("complete reservation")
                    && !input.toLowerCase().equalsIgnoreCase("leave")){
                System.out.println("You entered an invalid command. Please enter a valid word. \n");
                continue;
            }

            if (input.toLowerCase().equalsIgnoreCase("show my reservations")) {
                System.out.println(logic.showReservation(name));

            }else if (input.toLowerCase().equalsIgnoreCase("complete reservation")) {

                System.out.println("Enter reservation ID: ");
                try {
                    int nr = Integer.parseInt(scanner.nextLine());
                    if (!logic.completeReservation(name, nr))
                    {
                        System.out.println("Invalid reservation ID or reservation is in the future. Please enter a valid ID. \n");
                        continue;
                    }
                    System.out.println("Reservation successfully completed. \n");
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a numeric integer. \n");
                }

            }else if (input.toLowerCase().equalsIgnoreCase("leave")) {
                break;
            }
        }
    }
}
