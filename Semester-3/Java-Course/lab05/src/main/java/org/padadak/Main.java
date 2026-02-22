package org.padadak;

import org.padadak.objects.Board;
import org.padadak.objects.Creator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Max Shooters: ");
        int maxShooters = sc.nextInt();

        System.out.print("Max Pushers: ");
        int maxPushers = sc.nextInt();

        System.out.print("Max Searchers: ");
        int maxScouts = sc.nextInt();

        System.out.print("Width: ");
        int width = sc.nextInt();

        System.out.print("Height: ");
        int height = sc.nextInt();

        Board board = new Board(width, height);

        Creator creator = new Creator(board, maxShooters, maxPushers, maxScouts);
        new Thread(creator).start();

        Render renderer = new Render(board);
        new Thread(renderer).start();
    }
}
