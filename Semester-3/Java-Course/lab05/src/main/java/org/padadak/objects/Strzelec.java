package org.padadak.objects;

public class Strzelec extends Figure {

    public Strzelec(Plansza plansza)
    {
        super(plansza);
    }

    @Override public char getSymbol()
    {
        return 'S';
    }


    @Override
    public void run() {
        System.out.println("Strzelec started");
        while(alive) {

            changeRandDir();

            int x = this.getX();
            int y = this.getY();
            int dx = this.getDir().dx;
            int dy = this.getDir().dy;
            shoot(true, x, y, dx, dy);

            try {
                Thread.sleep(2000);
            } catch (Exception e) {}

            shoot(false, x, y, dx, dy);

        }

    }

    synchronized void shoot(boolean wh, int x, int y, int dx, int dy)
    {
        int x1 = x + dx;
        int y1 = y + dy;
        int x2 = x + 2 * dx;
        int y2 = y + 2 * dy;
        int x3 = x + 3 * dx;
        int y3 = y + 3 * dy;


        if (inside(x1, y1)){
            Figure f1 = plansza.getInfo(x1, y1).getFigure();
            if(f1!=null && f1.getClass() != Spychacz.class && wh && !plansza.getInfo(x1, y1).getKreator())
            {
                f1.setAlive(false);
                plansza.getInfo(x1, y1).setDead(wh);
                return;
            }
            plansza.getInfo(x1, y1).setShoot(wh);
        }
        if (inside(x2, y2)){
            Figure f2 = plansza.getInfo(x2, y2).getFigure();
            if(f2!=null && f2.getClass() != Spychacz.class && wh && !plansza.getInfo(x2, y2).getKreator())
            {
                f2.setAlive(false);
                plansza.getInfo(x2, y2).setDead(wh);
                return;
            }
            plansza.getInfo(x2, y2).setShoot(wh);
        }
        if (inside(x3, y3)) {
            Figure f3 = plansza.getInfo(x3, y3).getFigure();
            if(f3!=null && f3.getClass() != Spychacz.class && wh && !plansza.getInfo(x3, y3).getKreator())
            {
                f3.setAlive(false);
                plansza.getInfo(x3, y3).setDead(wh);
                return;
            }
            plansza.getInfo(x3, y3).setShoot(wh);
        }
    }
}






