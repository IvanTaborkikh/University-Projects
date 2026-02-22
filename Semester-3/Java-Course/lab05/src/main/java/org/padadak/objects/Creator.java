package org.padadak.objects;

import java.util.Random;

public class Creator implements Runnable {

    private final Board board;
    private final Random rand = new Random();

    private final int maxShooters;
    private final int maxPushers;
    private final int maxSearchers;

    private int curShooters = 0;
    private int curPushers = 0;
    private int curSearchers = 0;

    public Creator(Board board, int maxShooters, int maxPushers, int maxSearchers) {
        this.board = board;
        this.maxShooters = maxShooters;
        this.maxPushers = maxPushers;
        this.maxSearchers = maxSearchers;
    }

    @Override
    public void run() {
        while (true) {
            try { Thread.sleep(1000); } catch (Exception e) {}

            int x = rand.nextInt(board.getWidth());
            int y = rand.nextInt(board.getHeight());


            int random = rand.nextInt(100);

            if (random < 30) {
                board.getInfo(x, y).setCreator(true);
                try { Thread.sleep(700); } catch (Exception e) {}
                if (board.placeTreasure(x, y)) {
                    System.out.println("[Creator] Treasure at " + x + "," + y);
                }
                board.getInfo(x, y).setCreator(false);
                continue;
            }

            int t = rand.nextInt(3);
            Figure f = null;

            if (t == 0 && curShooters < maxShooters) {
                f = new Shooter(board);
                curShooters++;
            }
            else if (t == 1 && curPushers < maxPushers) {
                f = new Pusher(board);
                curPushers++;
            }
            else if (t == 2 && curSearchers < maxSearchers) {
                f = new Searcher(board);
                curSearchers++;
            }

            if (f == null) {
                continue;
            }

            board.getInfo(x, y).setCreator(true);
            try { Thread.sleep(700); } catch (Exception e) {}
            if (board.placeFigure(f, x, y)) {
                new Thread(f).start();
                System.out.println("[Creator] Created " + f.getClass().getSimpleName()
                        + " at " + x + "," + y);
            } else {
                if (f instanceof Shooter) curShooters--;
                if (f instanceof Pusher)  curPushers--;
                if (f instanceof Searcher)   curSearchers--;
            }
            board.getInfo(x, y).setCreator(false);
        }
    }
}
