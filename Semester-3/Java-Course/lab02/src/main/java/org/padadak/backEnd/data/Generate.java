package org.padadak.backEnd.data;

import java.io.FileWriter;
import java.io.IOException;

public class Generate {
    public static void create() {
        try {
            FileWriter myWriter = new FileWriter("pierscienie.txt");
            myWriter.write("# nr pierscienia, promien zewnetrzy, promien wewnetrzny, wysokosc\n1, 13, 10, 3\n2, 15, 9, 4");
            myWriter.close();
            System.out.println("File created");
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}
