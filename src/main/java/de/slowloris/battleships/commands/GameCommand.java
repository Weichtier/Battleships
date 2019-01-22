package de.slowloris.battleships.commands;

import de.slowloris.battleships.core.Command;
import de.slowloris.battleships.core.Main;

public class GameCommand implements Command {
    public boolean execute(String command, String[] args) {

        if(args.length > 0){
            if(args[0].equalsIgnoreCase("host")){
                if(args.length == 2){
                    try {
                        Integer.parseInt(args[1]);
                    }catch (NumberFormatException exc){
                        Main.consoleWriteln("Port must be a valid number");
                        return false;
                    }

                    Main.startNewHostGame(Integer.parseInt(args[1]));

                }else {
                    Main.consoleWriteln(syntax());
                }


            }else if(args[0].equalsIgnoreCase("join")){
                String[] target = args[1].split(":");
                if(!(target.length == 2)){
                    Main.consoleWriteln("Host format must be IP:Port");
                    return false;
                }
                String ip = target[0];
                String port = target[1];

                if(args.length == 2){
                    try {
                        Integer.parseInt(port);
                    }catch (NumberFormatException exc){
                        Main.consoleWriteln("Port must be a valid number");
                        return false;
                    }
                    Main.startNewGame(ip, Integer.parseInt(port));
                }else {
                    Main.consoleWriteln(syntax());
                }


            }else {
                Main.consoleWriteln(syntax());
            }
        }else {
            Main.consoleWriteln(syntax());
        }

        return false;
    }

    private String syntax(){
        return "Wrong Syntax! Try game (host | join) (port | server)";
    }

}
