package org.padadak.objects;

import java.util.Random;

public class Kreator implements Runnable {

    private final Plansza plansza;
    private final Random rand = new Random();

    private final int maxStrzelec;
    private final int maxSpyczhac;
    private final int maxSzperacz;

    private int curStrzelec = 0;
    private int curSpyczhac = 0;
    private int curSzperacz = 0;

    public Kreator(Plansza plansza, int maxStrzelec, int maxSpyczhac, int maxSzperacz) {
        this.plansza = plansza;
        this.maxStrzelec = maxStrzelec;
        this.maxSpyczhac = maxSpyczhac;
        this.maxSzperacz = maxSzperacz;
    }

    @Override
    public void run() {
        while (true) {
            try { Thread.sleep(1000); } catch (Exception e) {}

            int x = rand.nextInt(plansza.getWidth());
            int y = rand.nextInt(plansza.getHeight());


            int random = rand.nextInt(100);

            if (random < 30) {
                plansza.getInfo(x, y).setKreator(true);
                try { Thread.sleep(700); } catch (Exception e) {}
                if (plansza.placeTreasure(x, y)) {
                    System.out.println("[Kreator] Treasure at " + x + "," + y);
                }
                plansza.getInfo(x, y).setKreator(false);
                continue;
            }

            int t = rand.nextInt(3);
            Figure f = null;

            if (t == 0 && curStrzelec < maxStrzelec) {
                f = new Strzelec(plansza);
                curStrzelec++;
            }
            else if (t == 1 && curSpyczhac < maxSpyczhac) {
                f = new Spychacz(plansza);
                curSpyczhac++;
            }
            else if (t == 2 && curSzperacz < maxSzperacz) {
                f = new Szperacz(plansza);
                curSzperacz++;
            }

            if (f == null) {
                continue;
            }

            plansza.getInfo(x, y).setKreator(true);
            try { Thread.sleep(700); } catch (Exception e) {}
            if (plansza.placeFigure(f, x, y)) {
                new Thread(f).start();
                System.out.println("[Creator] Created " + f.getClass().getSimpleName()
                        + " at " + x + "," + y);
            } else {
                if (f instanceof Strzelec) curStrzelec--;
                if (f instanceof Spychacz)  curSpyczhac--;
                if (f instanceof Szperacz)   curSzperacz--;
            }
            plansza.getInfo(x, y).setKreator(false);
        }
    }
}






