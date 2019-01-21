package de.slowloris.battleships.ships;

public abstract class Vessel {

    private int healthPerBlock;
    private int sizeX;
    private int sizeY;

    public Vessel(int healthPerBlock, int sizeX, int sizeY) {
        this.healthPerBlock = healthPerBlock;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }
}
