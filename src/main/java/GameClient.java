import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

/**
 * Created by tmjon on 7/29/2017.
 *
 * Contains the code that enables the user to connect to another client.
 *
 */
public class GameClient {

    private Socket socket;
    private final GameHelper gameHelper;
    private final GameGui gameGui;

    public GameClient(String ip, GameGui gameGui) {
        this.gameGui = gameGui;
        gameHelper = gameGui.getGameHelper();
        gameHelper.setIsHost(false);
        connectToServer(ip);
    }


    private void connectToServer(String ip) {
        gameGui.setStageTitle("Connecting to " + ip);
        System.out.println("Client");
        Thread connectThread = new Thread(new MakeConnection(ip));
        connectThread.setDaemon(true);
        connectThread.start();
    }

    public void closeConnection() {
        try {
            socket.close();
            System.out.println("Client Connection Closed");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public class MakeConnection implements Runnable {
        String ipAddress;

        public MakeConnection(String ip) {
            ipAddress = ip;
        }
        @Override
        public void run() {
            try {
                socket = new Socket(ipAddress, 4242);
                gameHelper.setOutputStream(new PrintWriter(socket.getOutputStream()));
                gameHelper.setInputStream(new BufferedReader(new InputStreamReader(socket.getInputStream())));
                gameGui.setStageTitle("Connected to " + ipAddress);
                gameHelper.startListening();
                gameGui.enableButtons();
                gameGui.enableRollButton();
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }));

            } catch (IOException ex) {
                Platform.runLater(() -> {
                    new GameGui().drawGui(new Stage());
                    new ErrorBox("Connection Failed.");
                });
            }
        }
    }
}