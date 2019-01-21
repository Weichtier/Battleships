package de.slowloris.battleships.commands;

import de.slowloris.battleships.core.Command;
import de.slowloris.battleships.core.Main;

import java.util.Map;

public class HelpCommand implements Command {
    public boolean execute(String command, String[] args) {

        Main.consoleWriteln("Here is a list of Commands:");
        for(Map.Entry<String, Command> entry : Main.getCommandHandler().getCommands().entrySet()) {
            String key = entry.getKey();

            Main.consoleWriteln("- " + key);

        }

        return false;
    }
}
