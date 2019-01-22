package de.slowloris.battleships.commands;

import de.slowloris.battleships.core.Command;
import de.slowloris.battleships.core.Coordinates;
import de.slowloris.battleships.core.Main;

public class ShootCommand implements Command {
    @Override
    public boolean execute(String command, String[] args) {
        if(Main.getGame().isIngame()){
            if(args.length == 1){

                String[] coords = args[0].split("x");

                if(coords.length != 2){
                    Main.createGameScreenWithMessage("Wrong Format!");
                    return false;
                }

                try {
                    Integer.parseInt(coords[0]);
                    Integer.parseInt(coords[1]);
                }catch (NumberFormatException exc){
                    Main.createGameScreenWithMessage("Coordinates must be Integers! (Whole numbers)");
                    return false;
                }

                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);


                if(!Main.isPositive(x) || !Main.isPositive(y) || Main.isBiggerThan(x, 10) || Main.isBiggerThan(y, 10)){
                    Main.createGameScreenWithMessage("Coordinates must be between 1 and 10!");
                    return false;
                }

                boolean hitted = Main.getGame().hit(new Coordinates(x, y));

                if(hitted){
                    Main.createGameScreenWithMessage("You hitted an Enemys Ship at X=" + x + " and Y=" + y + "!");
                }else {
                    Main.createGameScreenWithMessage("You missed the Enemys Ships!");
                }


            }else {
                Main.createGameScreenWithMessage(syntax());
            }
        }else {
            Main.consoleWriteln("Only available if you're Ingame!");
        }
        return false;
    }

    private String syntax(){
        return "Wrong Format! Example: X=1 and Y=6 would be \"shoot 1x6\"";
    }
}
