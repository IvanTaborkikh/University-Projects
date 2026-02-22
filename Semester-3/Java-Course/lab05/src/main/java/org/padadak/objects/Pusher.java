package org.padadak.objects;

public class Pusher extends Figure {

    public Pusher(Board board)
    {
        super(board);
    }

    @Override
    public char getSymbol()
    {
        return 'P';
    }

    @Override
    public void run() {
        System.out.println("Pusher started");

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

        synchronized (board) {
            if (!canPush(x1, y1, dx, dy)) {
                return;
            }

            board.move(this, x1, y1);
        }
    }

    synchronized boolean canPush(int x, int y, int dx, int dy) {

        if (board.getInfo(x, y).getFigure() == null) {
            return true;
        }

        int x1 = x + dx;
        int y1 = y + dy;

        if (!inside(x1, y1)) return false;

        if (!canPush(x1, y1, dx, dy)) return false;

        Figure f = board.getInfo(x, y).getFigure();
        board.move(f, x1, y1);
        System.out.println("Pushing figure: " + this.getX() + ", " + this.getY());

        return true;
    }
}
