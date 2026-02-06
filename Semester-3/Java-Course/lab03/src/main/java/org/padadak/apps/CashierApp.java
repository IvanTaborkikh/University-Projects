package org.padadak.apps;

import org.padadak.logika.CashierLog;

import java.util.Scanner;

public class CashierApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CashierLog log = new CashierLog();

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
            System.out.print("To do(show rezerwacji, settle, leave): ");
            String input = scanner.nextLine();

            if (!input.toLowerCase().equalsIgnoreCase("show rezerwacji")
                    && !input.toLowerCase().equalsIgnoreCase("settle")
                    && !input.toLowerCase().equalsIgnoreCase("leave")){
                System.out.println("Your choice is invalid. Please try again: \n");
                continue;
            } else if (input.toLowerCase().equalsIgnoreCase("show rezerwacji")) {
                System.out.println(log.showRezerwacji());

            }else if (input.toLowerCase().equalsIgnoreCase("settle")) {
                try {
                    System.out.println(log.showRezerwacji());
                    System.out.println("Give me number of rezerwacji: ");
                    int nr = Integer.parseInt(scanner.nextLine());
                    if (!log.delRezerwacja(nr))
                    {
                        System.out.println("Your data is incorrect \n");
                        continue;
                    }
                    System.out.println("Rezerwacja was settled. \n ");
                } catch (NumberFormatException e) {
                    System.out.println("Please give me integer \n");
                }
            }else if (input.toLowerCase().equalsIgnoreCase("leave")) {

                break;
            }
        }
    }
}
