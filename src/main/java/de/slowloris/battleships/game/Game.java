package de.slowloris.battleships.game;

import de.slowloris.battleships.core.Coordinates;
import de.slowloris.battleships.core.Main;
import de.slowloris.battleships.ships.Speedboat;
import de.slowloris.battleships.ships.Vessel;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {

    private Socket socket;
    private boolean ingame;
    private HashMap<Coordinates, Vessel> vessels = new HashMap<Coordinates, Vessel>();
    private ArrayList<Coordinates> hitted = new ArrayList<Coordinates>();

    public Game(String ip, int port) {

        ingame = false;

        Main.consoleWriteln("Joining Gameserver...");

        //TODO: Create Socketserver

        Main.consoleWriteln("Joined Gameserver on " + ip + ":" + port + "!");


        Main.clearTerminal();

        Main.consoleWriteln("------------");
        Main.consoleWriteln("New Game");
        Main.consoleWriteln("------------\n");
        Main.consoleWriteln("Input second Speedboat Coordinates: (Example for X=2 and Y=6: 2x6)");
        shipInput(new Speedboat());
        Main.consoleWriteln("Input second Speedboat Coordinates: ");
        shipInput(new Speedboat());
        Main.consoleWriteln("Input third Speedboat Coordinates: ");
        shipInput(new Speedboat());
        Main.consoleWriteln("Input fourth Speedboat Coordinates: ");
        shipInput(new Speedboat());
        Main.consoleWriteln("Input fifth Speedboat Coordinates: ");
        shipInput(new Speedboat());

        buildBattlefieldAIO();

        ingame = true;

    }

    public boolean hit(Coordinates coordinates){
        if(isVessel(coordinates)){
            deleteVessel(coordinates);
            setHitted(coordinates);
            buildBattlefieldAIO();
            return true;
        }
        return false;
    }

    public void leave(){

    }

    public void createVessel(Vessel vessel, Coordinates coordinates){
        vessels.put(coordinates, vessel);
    }

    public void deleteVessel(Coordinates coordinates){
        if(vessels.containsKey(coordinates)) vessels.remove(coordinates);
    }

    public void setHitted(Coordinates coordinates){
        if(!hitted.contains(coordinates)) hitted.add(coordinates);
    }

    public void setIngame(boolean ingame) {
        this.ingame = ingame;
    }

    public String buildBattlefield(){

        String out = "     1  2  3  4  5  6  7  8  9  10\n\n";

        for (int y = 1; y < 11; y++){
            if(y == 10){
                out += y + "  ";
            }else {
                out += y + "   ";
            }
            for (int x = 1; x < 11; x++){
                Coordinates coordinates = new Coordinates(x, y);
                if(isVessel(coordinates)){
                    out += "  X";
                }else if(isHitted(coordinates)){
                    out += "  H";
                }else {
                    out += "  0";
                }

            }
            out += "\n";
        }

        return out;
    }

    public void buildBattlefieldAIO(){ //<-- AIO means All in one
        Main.clearTerminal();
        Main.consoleWriteln(buildBattlefield());
    }

    public boolean isVessel(Coordinates coordinates){

        for(Map.Entry<Coordinates, Vessel> entry : vessels.entrySet()) {
            Coordinates key = entry.getKey();

            if(key.getY() == coordinates.getY() && key.getX() == coordinates.getX()){
                return true;
            }
        }

        return false;
    }

    public boolean isHitted(Coordinates coordinates){
        return hitted.contains(coordinates);
    }

    public boolean isIngame() {
        return ingame;
    }

    public void shipInput(Vessel vessel){
        String coords = Main.getReader().readLine("battles> ");
        String[] coordsSplitted = coords.split("x");

        if(coordsSplitted.length != 2){
            Main.consoleWriteln("Wrong Format!");
            shipInput(vessel);
        }

        try {
            Integer.parseInt(coordsSplitted[0]);
            Integer.parseInt(coordsSplitted[1]);
        }catch (NumberFormatException exc){
            Main.consoleWriteln("Coordinates must be Integers! (Whole numbers)");
            shipInput(vessel);
        }

        int x = Integer.parseInt(coordsSplitted[0]);
        int y = Integer.parseInt(coordsSplitted[1]);

        
        if(!Main.isPositive(x) || !Main.isPositive(y) || Main.isBiggerThan(x, 10) || Main.isBiggerThan(y, 10)){
            Main.consoleWriteln("Coordinates must be between 1 and 10!");
            shipInput(vessel);
        }

        createVessel(vessel, new Coordinates(x, y));
    }

}
