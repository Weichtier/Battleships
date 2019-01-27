package de.slowloris.battleships.game;

import de.slowloris.battleships.core.Main;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer {

    private ServerSocket socket;
    private Thread serverThread;
    private ArrayList<Player> players = new ArrayList<Player>();

    public GameServer(int port) {
        Main.consoleWriteln("Creating Gameserver...");


        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        serverThread = new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        Socket client = socket.accept();
                        DataInputStream reader = new DataInputStream(client.getInputStream());
                        JSONObject obj = new JSONObject(reader.readUTF());
                        if(obj.get("data").equals("client_ready") && obj.get("value").equals("true")){
                            Main.consoleWriteln("User Joined!");
                            players.add(new Player(client));
                        }

                        if(players.size() == 2){
                            sendToAllPlayers("game_ready", "true");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        serverThread.start();



    }

    public void shutdown(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToPlayer(Player player, String data, String value){
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(player.getConnection().getOutputStream()));
            JSONObject obj = new JSONObject();
            obj.put("data", data);
            obj.put("value", value);
            writer.write(obj.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToAllPlayers(String data, String value){
        for (Player player : players) {
            sendToPlayer(player, data, value);
        }
    }

}
