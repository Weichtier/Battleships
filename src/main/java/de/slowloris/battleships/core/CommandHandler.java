package de.slowloris.battleships.core;

import java.util.HashMap;

public class CommandHandler {

    private HashMap<String, Command> commands = new HashMap<String, Command>();


    public void handleCommand(String command, String[] args){
        if(commands.containsKey(command)){
            boolean error = commands.get(command).execute(command, args);
            if(error){
                Main.consoleWriteln("ERROR WHILE EXECUTING COMMAND \"" + command + "\"");
            }
        }else {
            Main.consoleWriteln("Command not found! Try \"help\" for help!");
        }
    }

    public void registerCommand(String s, Command command){
        if(!commands.containsKey(s)){
            commands.put(s, command);
        }
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }
}
