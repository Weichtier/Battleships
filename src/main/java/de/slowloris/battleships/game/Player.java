package de.slowloris.battleships.game;

import java.net.Socket;

public class Player {
    private Socket connection;

    public Player(Socket socket) {
        this.connection = socket;
    }

    public Socket getConnection() {
        return connection;
    }
}
