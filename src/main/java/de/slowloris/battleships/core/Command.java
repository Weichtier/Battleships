package de.slowloris.battleships.core;

public interface Command {
    public boolean execute(String command, String[] args);
}
