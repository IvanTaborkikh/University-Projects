package org.padadak.apps;

import org.padadak.logika.EmployeeLog;

import java.util.Scanner;

public class EmployeeApp {


    private static final Scanner scanner = new Scanner(System.in);
    private static final EmployeeLog log = new EmployeeLog();

    public static void main(String[] args) {

        String name;
        while (true)
        {
            System.out.println(log.showUsers());
            System.out.println("Welcome, please enter your name or leave: ");
            name = scanner.nextLine();
            if (log.checkName(name))
                break;
            else if (name.equals("leave"))
                return;
            else
                System.out.println("Sorry, please enter a valid name: \n");
        }

        while (true) {

            System.out.println(log.status(name));
            System.out.print("To do(show rezerwacji to me, make rezerwacja done, leave): ");
            String input = scanner.nextLine();

            if (!input.toLowerCase().equalsIgnoreCase("show rezerwacji to me")
                    && !input.toLowerCase().equalsIgnoreCase("make rezerwacja done")
                    && !input.toLowerCase().equalsIgnoreCase("leave")){
                System.out.println("You entered an invalid word. Please enter a valid word. \n");
                continue;
            } else if (input.toLowerCase().equalsIgnoreCase("show rezerwacji to me")) {
                System.out.println(log.showRezerwacji(name));

            }else if (input.toLowerCase().equalsIgnoreCase("make rezerwacja done")) {

                System.out.println("Write number of rezerwacja: ");
                try {
                    int nr = Integer.parseInt(scanner.nextLine());
                    if (!log.didRezerwacja(name, nr))
                    {
                        System.out.println("Invalid number of rezerwacja. Please enter a valid number. \n");
                        continue;
                    }
                    System.out.println("Rezerwcja was successfully made done. \n");
                } catch (NumberFormatException e) {
                    System.out.println("Please write integer. \n");
                }

            }else if (input.toLowerCase().equalsIgnoreCase("leave")) {

                break;
            }
        }
    }
}
