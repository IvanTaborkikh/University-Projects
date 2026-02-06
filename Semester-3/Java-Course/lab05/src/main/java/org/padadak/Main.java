package org.padadak;

import org.padadak.objects.Kreator;
import org.padadak.objects.Plansza;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Max Strzelec: ");
        int maxShooters = sc.nextInt();

        System.out.print("Max Spychacz: ");
        int maxPushers = sc.nextInt();

        System.out.print("Max Szperacz: ");
        int maxScouts = sc.nextInt();

        System.out.print("Width: ");
        int width = sc.nextInt();

        System.out.print("Height: ");
        int height = sc.nextInt();

        Plansza plansza = new Plansza(width, height);

        Kreator kreator = new Kreator(plansza, maxShooters, maxPushers, maxScouts);
        new Thread(kreator).start();

        Render renderer = new Render(plansza);
        new Thread(renderer).start();
    }
}



