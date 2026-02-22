package org.padadak.objects;

public class Searcher extends Figure {

    private int treasures = 0;

    public Searcher(Board board) {
        super(board);
    }

    @Override
    public char getSymbol()
    {
        return 'C';
    }

    @Override
    protected void moveRandom() {
        int[] target = board.findNearTreasure(x, y);

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

        board.move(this, x1, y1);

        if (board.getInfo(x, y).getTreasure()) {
            board.collectTreasure(x, y);
            treasures++;
        }
        if (treasures >= 10) {
            System.out.println("Searcher became Shooter");

            alive = false;

            board.removeFigure(x, y);

            Shooter newShooter = new Shooter(board);
            board.placeFigure(newShooter, x, y);
            new Thread(newShooter).start();
        }


    }

}
