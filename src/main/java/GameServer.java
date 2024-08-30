import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

/**
 * Created by tmjon on 7/29/2017.
 *
 * Contains the code that enables the user to allow another client to connect to them.
 *
 */
public class GameServer {

    private final GameHelper gameHelper;
    private static ServerSocket serverSocket;
    private static Socket socket;
    GameGui gameGui;

    public GameServer(GameGui gameGui) {
        this.gameGui = gameGui;
        runServer();
        gameHelper = gameGui.getGameHelper();
        gameHelper.setIsHost(true);
    }

    private void runServer() {
        Thread connectThread = new Thread(new acceptConnections());
        connectThread.setDaemon(true);
        connectThread.start();
    }

    public void closeConnection() {
        try {
            if (socket != null) {
                socket.close();
            }
            serverSocket.close();
            System.out.println("Disconnected Server");
        } catch (IOException ex) {

            System.out.println("Failed to close Server Socket.");
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public class acceptConnections implements Runnable {
        public void run() {
            try {
                serverSocket = new ServerSocket(4242);
                gameGui.setStageTitle("Waiting for Connection");
                System.out.println("Server");
                socket = serverSocket.accept();
                gameGui.setStageTitle("Client Connected");
                gameGui.enableButtons();
                gameGui.enableRollButton();
                gameHelper.setOutputStream(new PrintWriter(socket.getOutputStream()));
                gameHelper.setInputStream(new BufferedReader(new InputStreamReader(socket.getInputStream())));
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    try {
                        socket.close();
                        serverSocket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }));
                gameHelper.startListening();
            } catch (IOException ex) {
                Platform.runLater(() -> new ErrorBox("Connection Failed."));
            }
        }
    }
}
