package de.slowloris.battleships.core;

import de.slowloris.battleships.commands.HelpCommand;
import de.slowloris.battleships.commands.GameCommand;
import de.slowloris.battleships.core.game.Game;
import de.slowloris.battleships.core.game.GameServer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    private static Terminal terminal;
    private static LineReader reader;
    private static CommandHandler commandHandler;
    private static Game game;
    private static GameServer gameServer;

    public static void main(String[] args){

        try {
            terminal = TerminalBuilder.builder().build();
            reader = LineReaderBuilder.builder().terminal(terminal).build();
            commandHandler = new CommandHandler();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        commandHandler.registerCommand("game", new GameCommand());
        commandHandler.registerCommand("help", new HelpCommand());

        consoleWriteln("Welcome to Sink!");
        consoleWriteln("----------------");

        while (true){
            String line = reader.readLine("sink> ");
            String[] input = line.split(" ");
            String command = input[0];
            String[] cmdargs = Arrays.copyOfRange(input, 1, input.length);


            commandHandler.handleCommand(command, cmdargs);

        }

    }

    public static void consoleWrite(String s){
        terminal.writer().print(s);
    }

    public static void consoleWriteln(String s){
        terminal.writer().println(s);
    }

    public static CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public static void startNewGame(String ip, int port){
        game = new Game(ip, port);
    }

    public static void startNewHostGame(int port){
        gameServer = new GameServer(port);
        startNewGame("127.0.0.1", port);
    }

}
