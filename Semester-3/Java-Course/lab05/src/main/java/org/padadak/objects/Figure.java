package org.padadak.objects;

import java.util.Random;

public abstract class Figure implements Runnable {
    protected int x, y;
    protected Board board;
    protected boolean alive = true;
    protected Random rand = new Random();
    private Direction dir;

    public Figure(Board board) {
        this.board = board;
    }

    public abstract char getSymbol();

    @Override
    public void run() {
        System.out.println(getClass().getSimpleName() + " started");
        while(alive) {
            moveRandom();
            try { Thread.sleep(500); } catch (Exception e) {}
        }
    }

    protected void moveRandom() {
        int nx = x + rand.nextInt(3) - 1;
        int ny = y + rand.nextInt(3) - 1;
        if(!board.getInfo(nx, ny).getCreator())
            board.move(this, nx, ny);
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
        board.removeFigure(this.getX(), this.getY());
        System.out.println("Delete fig: " + this.getX() + ", " + this.getY());
    }

    public void changeRandDir() {
        this.dir = Direction.random();
    }

    public boolean inside(int x, int y) {
        return x >= 0 && x < board.getWidth() &&
                y >= 0 && y < board.getHeight();
    }
}
