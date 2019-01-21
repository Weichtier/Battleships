package de.slowloris.battleships.core.game;

import de.slowloris.battleships.core.Coordinates;
import de.slowloris.battleships.core.Main;
import de.slowloris.battleships.ships.Vessel;

import java.net.Socket;
import java.util.HashMap;

public class Game {

    private Socket socket;
    private HashMap<Vessel, Coordinates> ships = new HashMap<Vessel, Coordinates>();

    public Game(String ip, int port) {

        Main.consoleWriteln("Joining Gameserver...");

        //TODO: Create Socketserver

        Main.consoleWriteln("Joined Gameserver on " + ip + ":" + port + "!");

    }

    public boolean hit(Coordinates coordinates){

        return false;
    }

    public void leave(){

    }

}
