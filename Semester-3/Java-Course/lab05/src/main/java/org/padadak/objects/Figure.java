package org.padadak.objects;

import java.util.Random;

public abstract class Figure implements Runnable {
    protected int x, y;
    protected Plansza plansza;
    protected boolean alive = true;
    protected Random rand = new Random();
    private Direction dir;

    public Figure(Plansza plansza) {
        this.plansza = plansza;
    }

    public abstract char getSymbol();

    @Override
    public void run() {
        System.out.println(getClass() + " started");
        while(alive) {
            moveRandom();
            try { Thread.sleep(500); } catch (Exception e) {}
        }
    }

    protected void moveRandom() {
        int nx = x + rand.nextInt(3) - 1;
        int ny = y + rand.nextInt(3) - 1;
        if(!plansza.getInfo(nx, ny).getKreator())
            plansza.move(this, nx, ny);
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public Direction getDir() {
        return this.dir;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setAlive(boolean alive) {
        this.alive = alive;
        plansza.removeFigure(this.getX(), this.getY());
        System.out.println("Delete fig: " + this.getX() + ", " + this.getY());
    }

    public void changeRandDir() {
        this.dir = Direction.random();
    }

    public boolean inside(int x, int y) {
        return x >= 0 && x < plansza.getWidth() &&
                y >= 0 && y < plansza.getHeight();
    }
}




