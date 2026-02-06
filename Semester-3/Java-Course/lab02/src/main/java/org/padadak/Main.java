package org.padadak;

import org.padadak.backEnd.algorithm.Calculating;
import org.padadak.backEnd.data.FileReading;
import org.padadak.backEnd.data.Pierscien;
import org.padadak.backEnd.data.Plyta;
import org.padadak.frontEnd.FileReceiving;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        Generate gen = new Generate();
//        gen.create();
        FileReceiving frc = new FileReceiving();
        FileReading fr = new FileReading();
        Calculating cal = new Calculating();
        File[] listOfFiles = frc.fileReader();
        List<Plyta> plytas = fr.readPlyta(listOfFiles[0]);
        List<Pierscien> piersciens = fr.readPiersien(listOfFiles[1]);
        cal.Found(plytas, piersciens);
    }
}