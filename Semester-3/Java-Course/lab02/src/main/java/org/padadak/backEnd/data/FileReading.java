package org.padadak.backEnd.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileReading {

    public static List<Plyta> readPlyta(File file) {
        List<Plyta> plytas = new ArrayList<Plyta>();
        try {
            BufferedReader fr = new BufferedReader(new FileReader(file));
            String line;
            while ((line = fr.readLine()) != null)
            {
                if(line.trim().length() == 0 || line.startsWith("#")) continue;
                String[] tokens = line.split(",");
                if(tokens.length == 2)
                {
                    int nr = Integer.parseInt(tokens[0].trim());
                    float promient = Float.parseFloat(tokens[1].trim());
                    plytas.add(new Plyta(nr, promient));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error z plyta, try again");
        }
        return plytas;
    }

    public static List<Pierscien> readPiersien(File file) {
        List<Pierscien> piersciens = new ArrayList<Pierscien>();
        try {
            BufferedReader fr = new BufferedReader(new FileReader(file));
            String line;
            while ((line = fr.readLine()) != null)
            {
                if(line.trim().length() == 0 || line.startsWith("#")) continue;
                String[] tokens = line.split(",");
                if(tokens.length == 4)
                {
                    int nr = Integer.parseInt(tokens[0].trim());
                    float promienZew = Float.parseFloat(tokens[1].trim());
                    float promienWew = Float.parseFloat(tokens[2].trim());
                    float wysokosc = Float.parseFloat(tokens[3].trim());
                    piersciens.add(new Pierscien(nr, promienZew, promienWew, wysokosc));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error z piersien, try again");
        }
        return piersciens;
    }
}
