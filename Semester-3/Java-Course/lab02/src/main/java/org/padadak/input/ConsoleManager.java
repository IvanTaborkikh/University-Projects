package org.padadak.input;

import java.io.File;
import java.util.Scanner;

public class ConsoleManager {

    public static File[] fileReader()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the plate file path: ");
        String platePath = scanner.nextLine();
        System.out.println("Enter the rings file path: ");
        String ringsPath = scanner.nextLine();
        try {
            File plateFile = new File(platePath);
            File ringsFile = new File(ringsPath);

            if (!plateFile.exists() || !ringsFile.exists()) {
                System.out.println("One of the files does not exist!");
                return null;
            }

            File[] list_of_files = new File[2];
            list_of_files[0] = plateFile;
            list_of_files[1] = ringsFile;
            return list_of_files;
        }
        catch(Exception e)
        {
            System.out.println("Error processing file paths");
            return null;
        }
    }
}
