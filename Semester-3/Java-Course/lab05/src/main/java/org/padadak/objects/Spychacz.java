package org.padadak.objects;

public class Spychacz extends Figure {

    public Spychacz(Plansza plansza)
    {
        super(plansza);
    }

    @Override
    public char getSymbol()
    {
        return 'P';
    }

    @Override
    public void run() {
        System.out.println("Spychacz started");

        while (alive) {

            changeRandDir();
            movePush();

            try {
                Thread.sleep(1500);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    synchronized void movePush() {
        int x = this.getX();
        int y = this.getY();

        int dx = this.getDir().dx;
        int dy = this.getDir().dy;

        int x1 = x + dx;
        int y1 = y + dy;

        if (!inside(x1, y1)) return;

        synchronized (plansza) {
            if (!canPush(x1, y1, dx, dy)) {
                return;
            }

            plansza.move(this, x1, y1);
        }
    }

    synchronized boolean canPush(int x, int y, int dx, int dy) {

        if (plansza.getInfo(x, y).getFigure() == null) {
            return true;
        }

        int x1 = x + dx;
        int y1 = y + dy;

        if (!inside(x1, y1)) return false;

        if (!canPush(x1, y1, dx, dy)) return false;

        Figure f = plansza.getInfo(x, y).getFigure();
        plansza.move(f, x1, y1);
        System.out.println("Spychaje figure: " + this.getX() + ", " + this.getY());

        return true;
    }
}







