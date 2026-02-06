package org.padadak.apps;

import org.padadak.logika.OwnerLog;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class OwnerApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final OwnerLog log = new OwnerLog();

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
            System.out.print("To do(show rezerwacji, edit costs of usluga, show income, time, leave): ");
            String input = scanner.nextLine();

            if (!input.toLowerCase().equalsIgnoreCase("show rezerwacji")
                    && !input.toLowerCase().equalsIgnoreCase("edit costs of usluga")
                    && !input.toLowerCase().equalsIgnoreCase("leave")
                    && !input.toLowerCase().equalsIgnoreCase("show income")
                    && !input.toLowerCase().equalsIgnoreCase("time")){
                System.out.println("You entered an invalid word. Please enter a valid word. \n");
                continue;
            } else if (input.toLowerCase().equalsIgnoreCase("show rezerwacji")) {
                System.out.println(log.showRezerwacji());

            }else if (input.toLowerCase().equalsIgnoreCase("time")) {
                while (true)
                {
                    System.out.println("To do(set time, back): ");
                    input = scanner.nextLine();
                    if (!input.toLowerCase().equalsIgnoreCase("set time")
                            && !input.toLowerCase().equalsIgnoreCase("back"))
                    {
                        System.out.println("You entered an invalid word. Please enter a valid word. \n");}
                    else if (input.toLowerCase().equalsIgnoreCase("set time"))
                    {
                        try {
                            System.out.println("Write date (yyyy-MM-dd): ");
                            String date = scanner.nextLine();
                            System.out.println("Write time (HH:mm): ");
                            String time = scanner.nextLine();
                            log.set(LocalDate.parse(date), LocalTime.parse(time));
                            System.out.println("You have successfully edited time." );
                        } catch (Exception e) {
                            System.out.println("Invalid date format! Please try again.");
                        }
                    }
                    else if (input.toLowerCase().equalsIgnoreCase("back"))
                    {
                        break;
                    }
                }

            }
            else if (input.toLowerCase().equalsIgnoreCase("show income")) {
                System.out.println(log.money());

            } else if (input.toLowerCase().equalsIgnoreCase("edit costs of usluga")) {

                System.out.println(log.showCenniky());
                System.out.println("Write name of usluga: ");
                String nazwa = scanner.nextLine();
                System.out.println("Write new costs of usluga: ");
                try
                {
                    int cenna = Integer.parseInt(scanner.nextLine());
                    System.out.println(log.changeCenna(nazwa, cenna));
                } catch (Exception e)
                {
                    System.out.println("Error " + e.getMessage());
                }



            }else if (input.toLowerCase().equalsIgnoreCase("leave")) {

                break;
            }
        }
    }
}
