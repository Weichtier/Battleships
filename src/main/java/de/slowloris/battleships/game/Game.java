package de.slowloris.battleships.game;

import de.slowloris.battleships.core.Coordinates;
import de.slowloris.battleships.core.Main;
import de.slowloris.battleships.ships.Speedboat;
import de.slowloris.battleships.ships.Vessel;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {

    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;
    private boolean ingame;
    private HashMap<Coordinates, Vessel> vessels = new HashMap<Coordinates, Vessel>();
    private ArrayList<Coordinates> hitted = new ArrayList<Coordinates>();

    public Game(String ip, int port) {


        //TODO: Set ingame to false when working
        ingame = true;

        Main.consoleWriteln("Joining Gameserver...");

        try {
            socket = new Socket(ip, port);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            Main.consoleWriteln("Error while joining Gameserver on " + ip + ":" + port);
            return;
        }

        Main.clearTerminal();

        Main.consoleWriteln("------------");
        Main.consoleWriteln("New Game");
        Main.consoleWriteln("------------\n");
        Main.consoleWriteln("Input first Speedboat Coordinates: (Example for X=2 and Y=6: 2x6)");
        shipInput(new Speedboat());
        Main.consoleWriteln("Input second Speedboat Coordinates: ");
        shipInput(new Speedboat());
        Main.consoleWriteln("Input third Speedboat Coordinates: ");
        shipInput(new Speedboat());
        Main.consoleWriteln("Input fourth Speedboat Coordinates: ");
        shipInput(new Speedboat());
        Main.consoleWriteln("Input fifth Speedboat Coordinates: ");
        shipInput(new Speedboat());

        createGameScreenWithMessage("Waiting for Server to start...");

        try {
            writer.write(new JSONObject().put("data", "client_ready").put("value", "true").toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        final String[] line = new String[1];

        Thread clientThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (((line[0] = reader.readLine()) != null)) {

                            JSONObject obj = new JSONObject(line[0]);
                            String data = obj.getString("data");

                            if (data.equals("game_ready") && !ingame) {
                                Main.createGameScreenWithMessage("Game Started!");
                                ingame = true;
                            } else if (data.equals("your_turn")) {
                                Main.createGameScreenWithMessage("You are Playing");
                            } else if (data.equals("other_turn")) {
                                Main.createGameScreenWithMessage("Enemy is Playing");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        clientThread.start();

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

        Coordinates coordinates = new Coordinates(x, y);

        if(isVessel(coordinates)){
            Main.consoleWriteln("There is already a Ship at X=" + x + " and Y=" + y + "!");
            shipInput(vessel);
        }

        createVessel(vessel, coordinates);
    }

    private void createGameScreenWithMessage(String message){
        buildBattlefieldAIO();
        Main.consoleWriteln("Last Message: " + message);
    }

}
