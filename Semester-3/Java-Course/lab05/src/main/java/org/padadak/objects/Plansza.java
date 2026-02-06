package org.padadak.objects;

public class Plansza {
    private int width;
    private int height;
    private Cell[][] cells;

    public Plansza(int width, int height)
    {
        this.width = width;
        this.height = height;
        cells = new Cell[height][width];
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                cells[y][x] = new Cell();
            }
        }
    }

    public synchronized boolean placeTreasure(int x, int y)
    {
        if (cells[y][x].getTreasure() || cells[y][x].getFigure() != null)
            return false;

        cells[y][x].setTreasure(true);
        return true;
    }

    public synchronized void collectTreasure(int x, int y)
    {
        cells[y][x].setTreasure(false);
    }


    public synchronized boolean placeFigure(Figure f, int x, int y)
    {
        if (cells[y][x].getFigure() == null && cells[y][x].getTreasure())
            return false;
        cells[y][x].setFigure(f);
        f.setPos(x, y);
        return true;
    }

    public synchronized void removeFigure(int x, int y)
    {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return;
        cells[y][x].setFigure(null);
    }



    public synchronized int[] findNearTreasure(int x, int y)
    {
        int bestD = Integer.MAX_VALUE;
        int[] best = null;

        for (int iy = 0; iy < height; iy++) {
            for (int ix = 0; ix < width; ix++) {
                if (cells[iy][ix].getTreasure()) {
                    int d = Math.abs(ix - x) + Math.abs(iy - y);
                    if (d < bestD) {
                        bestD = d;
                        best = new int[]{ix, iy};
                    }
                }
            }
        }
        return best;
    }


    public synchronized void move(Figure f, int newX, int newY)
    {
        if (newX < 0 || newY < 0 || newX >= width || newY >= height)
            return;
        if (cells[newY][newX].getKreator())
            return;
        if (cells[newY][newX].getFigure() != null)
            return;


        if (cells[newY][newX].getTreasure() && !(f instanceof Szperacz))
            return;

        if (newX == 1 && newY == 0) {
            f.setDir(Direction.RIGHT);
        } else if (newX == -1 && newY == 0) {
            f.setDir(Direction.LEFT);
        } else if (newX == 0 && newY == 1) {
            f.setDir( Direction.DOWN);
        } else if (newX == 0 && newY == -1) {
            f.setDir(Direction.UP);
        } else if (newX == 1 && newY == 1) {
            f.setDir(Direction.DOWN_RIGHT);
        } else if (newX == 1 && newY == -1) {
            f.setDir(Direction.UP_RIGHT);
        } else if (newX == -1 && newY == 1) {
            f.setDir(Direction.DOWN_LEFT);
        } else if (newX == -1 && newY == -1) {
            f.setDir(Direction.UP_LEFT);
        }

        cells[f.getY()][f.getX()].setFigure(null);
        cells[newY][newX].setFigure(f);
        f.setPos(newX, newY);
    }


    public synchronized Cell[][] getCells()
    {
        return cells;
    }

    public Cell getInfo(int x, int y)
    {
        return this.cells[y][x];
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

}
