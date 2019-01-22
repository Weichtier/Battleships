package de.slowloris.battleships.game;

import de.slowloris.battleships.core.Main;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class GameServer {

    private ServerSocket socket;
    private OutputStream out;
    private InputStream in;
    private BufferedReader reader;
    private ArrayList<String> players = new ArrayList<String>();

    public GameServer(int port) {
        Main.consoleWriteln("Creating Gameserver...");


        //TODO: Create Socketserver

        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Main.consoleWriteln("Created Gameserver on " + InetAddress.getLocalHost().getHostAddress() + ":" + port + "!");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        /*
         while (true){
            try {
                Socket client = socket.accept();
                players.add(client.getInetAddress().getHostAddress());


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        * */

    }

    public void shutdown(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
