package org.padadak.apps;

import org.padadak.logic.OwnerLog;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class OwnerApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final OwnerLog logic = new OwnerLog();

    public static void main(String[] args) {

        String name;
        while (true)
        {
            System.out.println(logic.showUsers());
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
            System.out.print("To do(show reservations, update price, show income, system time, leave): ");
            String input = scanner.nextLine();

            if (!input.toLowerCase().equalsIgnoreCase("show reservations")
                    && !input.toLowerCase().equalsIgnoreCase("update price")
                    && !input.toLowerCase().equalsIgnoreCase("leave")
                    && !input.toLowerCase().equalsIgnoreCase("show income")
                    && !input.toLowerCase().equalsIgnoreCase("system time")){
                System.out.println("You entered an invalid command. Please enter a valid command. \n");
                continue;
            }

            if (input.toLowerCase().equalsIgnoreCase("show reservations")) {
                System.out.println(logic.showReservation());

            }else if (input.toLowerCase().equalsIgnoreCase("system time")) {
                while (true)
                {
                    System.out.println("Time Settings (set time, back): ");
                    input = scanner.nextLine();
                    if (!input.toLowerCase().equalsIgnoreCase("set time")
                            && !input.toLowerCase().equalsIgnoreCase("back"))
                    {
                        System.out.println("You entered an invalid command. Please enter a valid command. \n");}
                    else if (input.toLowerCase().equalsIgnoreCase("set time"))
                    {
                        try {
                            System.out.println("Write date (yyyy-MM-dd): ");
                            String date = scanner.nextLine();
                            System.out.println("Write time (HH:mm): ");
                            String time = scanner.nextLine();
                            logic.setTime(LocalDate.parse(date), LocalTime.parse(time));
                            System.out.println("System time updated successfully." );
                        } catch (Exception e) {
                            System.out.println("Invalid date format! Use yyyy-MM-dd and HH:mm.");
                        }
                    }
                    else if (input.toLowerCase().equalsIgnoreCase("back"))
                    {
                        break;
                    } else {
                        System.out.println("Invalid word. Use 'set time' or 'back'.");
                    }
                }

            }
            else if (input.toLowerCase().equalsIgnoreCase("show income")) {
                System.out.println("Total income across all facilities: " + logic.getTotalBalance());

            } else if (input.toLowerCase().equalsIgnoreCase("update price")) {

                System.out.println(logic.showPrices());
                System.out.println("Enter service name to edit: ");
                String nazwa = scanner.nextLine();
                System.out.println("Enter new price: ");
                try
                {
                    int cenna = Integer.parseInt(scanner.nextLine());
                    System.out.println(logic.changePrice(nazwa, cenna));
                } catch (Exception e)
                {
                    System.out.println("Error: Please enter a valid information.");
                }



            }else if (input.toLowerCase().equalsIgnoreCase("leave")) {

                break;
            }
        }
    }
}