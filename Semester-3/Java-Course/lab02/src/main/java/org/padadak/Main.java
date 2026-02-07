package org.padadak;

import org.padadak.algorithm.Calculator;
import org.padadak.input.ConsoleManager;
import org.padadak.input.FileReader;
import org.padadak.model.Plate;
import org.padadak.model.Ring;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConsoleManager con = new ConsoleManager();
        FileReader fr = new FileReader();
        Calculator cal = new Calculator();
        File[] listOfFiles = con.fileReader();
        List<Plate> plates = fr.readPlates(listOfFiles[0]);
        List<Ring> rings = fr.readRings(listOfFiles[1]);
        cal.Found(plates, rings);
    }
}