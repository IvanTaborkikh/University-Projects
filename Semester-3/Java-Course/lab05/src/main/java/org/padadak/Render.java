package org.padadak;

import org.padadak.objects.Cell;
import org.padadak.objects.Plansza;

public class Render implements Runnable {

    private Plansza plansza;

    public Render(Plansza plansza) {
        this.plansza = plansza;
    }

    @Override
    public void run() {
        while(true) {
            try { Thread.sleep(700); } catch(Exception e) {}
            render();
        }
    }

    private void render() {
        System.out.println("\n\n");

        Cell[][] c = plansza.getCells();
        for(int y = 0; y < plansza.getHeight(); y++) {
            for(int x = 0; x < plansza.getWidth(); x++) {
                if (c[y][x].getFigure() != null)
                    System.out.print(c[y][x].getFigure().getSymbol() + " ");
                else if (c[y][x].getKreator())
                    System.out.print("K ");
                else if (c[y][x].getTreasure())
                    System.out.print("T ");
                else if (c[y][x].getShoot())
                    System.out.print("* ");
                else if (c[y][x].getDead()) {
                    System.out.print("0 ");
                    c[y][x].setDead(false);
                }else
                    System.out.print(". ");

            }
            System.out.println();
        }
    }
}
