package de.slowloris.battleships.core;

public class Coordinates {
    private int x;
    private int y;

    public boolean equals(Object o) {
        Coordinates c = (Coordinates) o;
        return c.x == x && c.y == y;
    }

    public Coordinates(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int hashCode() {
        return new Integer(x + "0" + y);
    }
}
