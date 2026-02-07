package org.padadak.apps;

import org.padadak.exceptions.*;
import org.padadak.logic.ClientLog;

import java.util.Scanner;

public class ClientApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ClientLog logic = new ClientLog();

    public static void main(String[] args) {
        String name;
        while (true)
        {
            System.out.println(logic.showClients());
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
            System.out.print("To do(make reservation, my reservations, leave): ");
            String input = scanner.nextLine();

            if (!input.toLowerCase().equalsIgnoreCase("make reservation")
                    && !input.toLowerCase().equalsIgnoreCase("my reservations")
                    && !input.toLowerCase().equalsIgnoreCase("leave")){
                System.out.println("You entered an invalid command. Please try again. \n");
                continue;
            }

            if (input.toLowerCase().equalsIgnoreCase("make reservation")) {
                System.out.println("Where you want make reservation?");
                System.out.println(logic.showFacilities());
                System.out.print("Enter facility name: ");
                String facility = scanner.nextLine();

                String employeeList = logic.showEmployee(facility);

                if(employeeList.equals("Facility does not exist"))
                {
                    System.out.println(employeeList);
                    continue;
                }else {
                    System.out.println(logic.showEmployee(facility));
                }

                System.out.print("Write name: ");
                String employee = scanner.nextLine();
                if (!logic.checkEmployee(employee, facility))
                {
                    System.out.println("Invalid name");
                    continue;
                }

                System.out.print("Write date (yyyy-MM-dd): ");
                String date = scanner.nextLine();
                System.out.print("Write time (HH:mm): ");
                String time = scanner.nextLine();

                try{
                    int day = logic.checkTime(date, time, facility, employee);

                    switch (day) {
                        case 1:
                            throw new InvalidReservationDateFormatException("Invalid date format! Use yyyy-MM-dd.");
                        case 2:
                            throw new ReservationTimeRangeException("Reservations are allowed only between 09:00 and 18:00.");
                        case 3:
                            throw new InvalidReservationTimeStepException("Time must be in 15-minute intervals (00, 15, 30, 45).");
                        case 4:
                            throw new InvalidReservationTimeFormatException("Invalid time format! Use HH:mm.");
                        case 5:
                            throw new PastReservationTimeException("Cannot make a reservation in the past.");
                        case 6:
                            throw new ReservationAlreadyExistsException("There is already a reservation at this time!");
                        case 7:
                            throw new TooCloseReservationException("There must be at least 30 minutes between reservations.");
                        case 0:
                            break;
                    }
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }




                System.out.println(logic.showPrices());
                System.out.print("Enter service name: ");

                String serviceName = scanner.nextLine();

                if (!logic.checkServiceName(serviceName))
                {
                    System.out.println("Invalid service name.");
                    continue;
                }

                if (logic.reservationTime(facility, employee, date, time, serviceName, name))
                {
                    System.out.println("Reservation was successfully made.");
                }


            }else if (input.toLowerCase().equalsIgnoreCase("my reservations")) {

                System.out.println(logic.showReservations(name));
                while (true)
                {
                    System.out.println("To do(delete reservation, back): ");
                    input = scanner.nextLine();
                    if (!input.toLowerCase().equalsIgnoreCase("delete reservation")
                            && !input.toLowerCase().equalsIgnoreCase("back"))
                    {
                        System.out.println("Invalid command. Please enter a valid command. \n ");
                    } else if (input.toLowerCase().equalsIgnoreCase("delete reservation"))
                    {
                        System.out.println("Enter reservation ID: ");
                        try {
                            int id = Integer.parseInt(scanner.nextLine());
                            if (!logic.deleteReservation(name, id))
                            {
                                System.out.println("Invalid ID or the reservation does not belong to you.");
                                continue;
                            }
                            System.out.println("Reservation deleted.");
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid numeric ID.");
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
