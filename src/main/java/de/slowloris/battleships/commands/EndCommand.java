package de.slowloris.battleships.commands;

import de.slowloris.battleships.core.Command;

public class EndCommand implements Command {
    @Override
    public boolean execute(String command, String[] args) {
        System.exit(0);
        return false;
    }
}
