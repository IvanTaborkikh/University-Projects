package org.padadak.frontEnd;

import java.io.File;
import java.util.Scanner;

public class FileReceiving {

    public static File[] fileReader()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the file plyta path : ");
        String path_plyta = scanner.nextLine();
        System.out.println("Enter the file pierscienie path : ");
        String path_pierscienie = scanner.nextLine();
        try {
            File plyta = new File(path_plyta);
            File pierscienie = new File(path_pierscienie);

            File[] list_of_files = new File[2];
            list_of_files[0] = plyta;
            list_of_files[1] = pierscienie;
            return list_of_files;
        }
        catch(Exception e)
        {
            System.out.println("File not found");
            return null;
        }
    }
}
