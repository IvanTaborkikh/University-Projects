package org.padadak.objects;

public class Szperacz extends Figure {

    private int treasures = 0;

    public Szperacz(Plansza plansza) {
        super(plansza);
    }

    @Override
    public char getSymbol()
    {
        return 'C';
    }

    @Override
    protected void moveRandom() {
        int[] target = plansza.findNearTreasure(x, y);

        int x1 = x;
        int y1 = y;

        if (target != null) {
            if (target[0] > x) x1++;
            if (target[0] < x) x1--;
            if (target[1] > y) y1++;
            if (target[1] < y) y1--;
        } else {
            x1 = x + rand.nextInt(3) - 1;
            y1 = y + rand.nextInt(3) - 1;
        }

        plansza.move(this, x1, y1);

        if (plansza.getInfo(x, y).getTreasure()) {
            plansza.collectTreasure(x, y);
            treasures++;
        }
        if (treasures >= 10) {
            System.out.println("Szperacz został Strzelcom");

            alive = false;

            plansza.removeFigure(x, y);

            Strzelec newStrzelec = new Strzelec(plansza);
            plansza.placeFigure(newStrzelec, x, y);
            new Thread(newStrzelec).start();
        }


    }

}



