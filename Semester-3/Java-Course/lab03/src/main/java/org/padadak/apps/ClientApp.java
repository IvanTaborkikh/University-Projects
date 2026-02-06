package org.padadak.apps;

import org.padadak.Eceptions.*;
import org.padadak.logika.ClientLog;

import java.util.Scanner;

public class ClientApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ClientLog log = new ClientLog();

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
            System.out.print("To do(make rezerwacja, my rezerwacja, leave): ");
            String input = scanner.nextLine();

            if (!input.toLowerCase().equalsIgnoreCase("make rezerwacja")
                    && !input.toLowerCase().equalsIgnoreCase("my rezerwacja")
                    && !input.toLowerCase().equalsIgnoreCase("leave")){
                System.out.println("You entered an invalid word. Please enter a valid word. \n");
                continue;
            } else if (input.toLowerCase().equalsIgnoreCase("make rezerwacja")) {
                System.out.println("Where you want make rezerwacja?");
                System.out.println(log.showZaklady());
                System.out.print("Write name of zaklad: ");
                String zak = scanner.nextLine();
                if(log.showEmployee(zak).equals("You entered an invalid word. Please enter a valid word."))
                {
                    System.out.println(log.showEmployee(zak));
                    continue;
                }else {
                    System.out.println(log.showEmployee(zak));
                }

                System.out.print("Write name: ");
                String employee = scanner.nextLine();
                if (!log.checkEmployee(employee, zak))
                {
                    System.out.println("Invalid name");
                    continue;
                }

                System.out.print("Write date (yyyy-MM-dd): ");
                String date = scanner.nextLine();
                System.out.print("Write time (HH:mm): ");
                String time = scanner.nextLine();

                try{
                    int day = log.checkTime(date, time, zak, employee);

                    switch (day) {
                        case 1:
                            throw new InvalidRezerwacjaDateFormatException("Invalid date format! Please try again.");
                        case 2:
                            throw new RezerwacjaTimeRangeException("Rezerwacja is allowed only between 09:00 and 18:00.");
                        case 3:
                            throw new InvalidRezerwacjaTimeStepException("Time must be a multiple of 15 minutes (00, 15, 30, 45).");
                        case 4:
                            throw new InvalidRezerwacjaTimeFormatException("Invalid time format! Please try again.");
                        case 5:
                            throw new PastRezerwacjaTimeException("Cannot make Rezerwacja at time in the past.");
                        case 6:
                            throw new RezerwacjaAlreadyExistsException("There is already a reservation at this time!");
                        case 7:
                            throw new TooCloseRezerwacjaException("There must be at least 30 minutes between rezerwacje.");
                        case 0:
                            break;
                    }
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }




                System.out.println(log.showCennik());
                System.out.print("Write name of usluga: ");

                String nazwa_uslugi = scanner.nextLine();

                if (!log.checkUsluga(nazwa_uslugi))
                {
                    System.out.println("Invalid name of usluga");
                    continue;
                }

                if (log.RezerwacjaTime(zak, employee, date, time, nazwa_uslugi, name))
                {
                    System.out.println("Rezerwacja was made");
                }


            }else if (input.toLowerCase().equalsIgnoreCase("my rezerwacja")) {

                System.out.println(log.showRezerwacji(name));
                while (true)
                {
                    System.out.println("To do(delete rezerwacja, back): ");
                    input = scanner.nextLine();
                    if (!input.toLowerCase().equalsIgnoreCase("delete rezerwacja")
                            && !input.toLowerCase().equalsIgnoreCase("back"))
                    {
                        System.out.println("You entered an invalid word. Please enter a valid word. \n ");
                    } else if (input.toLowerCase().equalsIgnoreCase("delete rezerwacja"))
                    {
                        System.out.println("Write number of rezerwacja: ");
                        try {
                            int nr = Integer.parseInt(scanner.nextLine());
                            if (!log.deleteRezerwacja(name, nr))
                            {
                                System.out.println("Invalid number (or not your rezrwacja)");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Write int");
                        }
                    }else if (input.toLowerCase().equalsIgnoreCase("back"))
                    {
                        break;
                    }
                }

            }else if (input.toLowerCase().equalsIgnoreCase("leave")) {

                break;
            }
        }

    }



}
