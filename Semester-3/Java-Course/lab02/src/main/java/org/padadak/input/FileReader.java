package org.padadak.input;

import org.padadak.model.Plate;
import org.padadak.model.Ring;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    public static List<Plate> readPlates(File file) {
        List<Plate> plates = new ArrayList<Plate>();
        try (BufferedReader fr = new BufferedReader(new java.io.FileReader(file))) {
            String line;
            while ((line = fr.readLine()) != null)
            {
                if(line.trim().length() == 0 || line.startsWith("#")) continue;
                String[] tokens = line.split(",");
                if(tokens.length == 2)
                {
                    int id = Integer.parseInt(tokens[0].trim());
                    float radius = Float.parseFloat(tokens[1].trim());
                    plates.add(new Plate(id, radius));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error with plate's file reading, try again");
            e.printStackTrace();
        }
        return plates;
    }

    public static List<Ring> readRings(File file) {
        List<Ring> rings = new ArrayList<Ring>();
        try (BufferedReader fr = new BufferedReader(new java.io.FileReader(file))){
            String line;
            while ((line = fr.readLine()) != null)
            {
                if(line.trim().length() == 0 || line.startsWith("#")) continue;
                String[] tokens = line.split(",");
                if(tokens.length == 4)
                {
                    int id = Integer.parseInt(tokens[0].trim());
                    float outerRadius = Float.parseFloat(tokens[1].trim());
                    float innerRadius = Float.parseFloat(tokens[2].trim());
                    float height = Float.parseFloat(tokens[3].trim());
                    rings.add(new Ring(id, outerRadius, innerRadius, height));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error with ring's file reading, try again");
            e.printStackTrace();
        }
        return rings;
    }
}
