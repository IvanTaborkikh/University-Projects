package org.padadak.objects;

public enum Direction {
    RIGHT(1, 0),
    LEFT(-1, 0),
    DOWN(0, 1),
    UP(0, -1),
    DOWN_RIGHT(1, 1),
    UP_RIGHT(1, -1),
    DOWN_LEFT(-1, 1),
    UP_LEFT(-1, -1);

    public int dx;
    public int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Direction random() {
        return values()[new java.util.Random().nextInt(values().length)];
    }
}



