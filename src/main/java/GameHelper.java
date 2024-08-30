import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by tmjon on 7/31/2017.
 *
 * This class contains and controls a lot of the gameplay mechanics.
 *
 */
public class GameHelper {


    private PrintWriter outputStream;
    private BufferedReader inputStream;
    private final int[][][] shipLocations;
    private boolean myTurn;
    private final BottomGrid bottomGrid;
    private boolean pOneReady;
    private boolean pTwoReady;
    private int pOneRoll;
    private int pTwoRoll;
    private boolean pOneRolled;
    private boolean pTwoRolled;
    private int[] typeCounter = new int[6];
    private int hitCounter;
    private boolean isHost;
    private volatile boolean shutdown = false;
    private final GameGui gameGui;
    private final GameClient gameClient;
    private final GameServer gameServer;


    public GameHelper(BottomGrid bg, GameGui gameGui, GameClient gameClient, GameServer gameServer) {
        this.gameClient = gameClient;
        this.gameServer = gameServer;
        this.gameGui = gameGui;
        shipLocations = bg.getShipLocations();
        bottomGrid = bg;
    }

    public boolean getMyTurn() {
        return myTurn;
    }

    public boolean getBothReady() {
        return pOneReady && pTwoReady;
    }

    private void checkForSinkP1(int type) {
        Platform.runLater(() -> {

            if (type == 1) {
                if (typeCounter[type] == 5) {
                    new ErrorBox("They sank your Carrier!", "Your Carrier Sank");
                    outputStream.println("sank/1");
                    outputStream.flush();
                }
            } else if (type == 2) {
                if (typeCounter[type] == 4) {
                    new ErrorBox("They sank your Battleship!", "Your Battleship Sank");
                    outputStream.println("sank/2");
                    outputStream.flush();
                }
            } else if (type == 3) {
                if (typeCounter[type] == 3) {
                    new ErrorBox("They sank your Cruiser!", "Your Cruiser Sank");
                    outputStream.println("sank/3");
                    outputStream.flush();
                }
            } else if (type == 4) {
                if (typeCounter[type] == 3) {
                    new ErrorBox("They sank your Submarine!", "Your Submarine Sank");
                    outputStream.println("sank/4");
                    outputStream.flush();
                }
            } else if (type == 5) {
                if (typeCounter[type] == 2) {
                    new ErrorBox("They sank your Destroyer!", "Your Destroyer Sank");
                    outputStream.println("sank/5");
                    outputStream.flush();
                }
            }
        });
    }

    private void checkForSinkP2(String data){
        String[] splitter = data.split("/");
        Platform.runLater(() -> {

            switch (Integer.parseInt(splitter[1])) {
                case 1:
                    new ErrorBox("You sank their Carrier!", "Their Carrier Sank!");
                    break;
                case 2:
                    new ErrorBox("You sank their Battleship!", "Their Battleship Sank!");
                    break;
                case 3:
                    new ErrorBox("You sank their Cruiser!", "Their Cruiser Sank!");
                    break;
                case 4:
                    new ErrorBox("You sank their Submarine!", "Their Submarine Sank!");
                    break;
                case 5:
                    new ErrorBox("You sank their Destroyer!", "Their Destroyer Sank!");
                    break;
            }
        });
    }

    public void newGameSetup() {
        setMyTurn(false);
        resetRolled();
        resetCounters();
        resetReady();

        gameGui.enableNewGameMenu();
        if (inputStream != null && outputStream != null) {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        shutdownThread();
    }

    private void resetCounters() {
        typeCounter = new int[6];
        hitCounter = 0;
    }

    private void resetReady() {
        pOneReady = false;
        pTwoReady = false;
    }

    private void gameOverP1() {
        outputStream.println("over");
        outputStream.flush();

        Platform.runLater(() -> new ErrorBox("You Lose!", "You have lost this game!"));
        disconnectSocket();
        newGameSetup();
    }

    private void disconnectSocket() {
        if (getIsHost()) {
            gameServer.closeConnection();
            System.out.println("Server closed");
        } else {
            gameClient.closeConnection();
        }
    }

    private void gameOverP2() {
        Platform.runLater(() -> new ErrorBox("You Win!", "You have won this game!"));
        disconnectSocket();
        newGameSetup();
    }

    private void shutdownThread() {
        shutdown = true;
    }

    private boolean getBothRolled() {
        return pOneRolled && pTwoRolled;
    }

    public boolean getP1Rolled() {
        return pOneRolled;
    }

    public void setIsHost(boolean tf) {
        isHost = tf;
    }

    public boolean getIsHost() {
        return isHost;
    }

    private void setMyTurn(boolean tf) {
        myTurn = tf;
    }

    public void sendCoordinates(int col, int row) {
        outputStream.println(col + "-" + row);
        outputStream.flush();
        setMyTurn(false);
        gameGui.setP2Turn();
    }

    private void setpTwoReady() {
        pTwoReady = true;
        Platform.runLater(() -> gameGui.setP2Ready(true));
    }

    public void sendReady() {
        outputStream.println("ready");
        outputStream.flush();
        pOneReady = true;
    }

    public void sendRoll(int roll) {
        pOneRoll = roll;
        pOneRolled = true;
        outputStream.println("roll" + "/" + roll);
        outputStream.flush();
        compareRolls();
    }

    private void resetRolled() {
        pOneRolled = false;
        pTwoRolled = false;
    }

    private void setpTwoRoll(String roll) {
        String[] splitter = roll.split("/");
        pTwoRolled = true;
        pTwoRoll = Integer.parseInt(splitter[1]);
        Platform.runLater(() -> gameGui.setRoll2(pTwoRoll));
        compareRolls();
    }

    private void compareRolls() {
        if (getBothRolled()) {
            Platform.runLater(() -> {
                if (pOneRoll > pTwoRoll) {
                    setMyTurn(true);
                    gameGui.setP1Turn();
                    new ErrorBox("You have first move.", "You " + pOneRoll +
                            " : " + "Opponent " + pTwoRoll);
                } else if (pOneRoll == pTwoRoll) {
                    pOneRolled = false;
                    pTwoRolled = false;
                    new ErrorBox("Please roll again.");
                    gameGui.enableRollButton();
                } else {
                    setMyTurn(false);
                    gameGui.setP2Turn();
                    new ErrorBox("Your opponent has first move.", "You " +
                            pOneRoll + " : " + "Opponent " + pTwoRoll);
                }
            });
        }
    }

    public void setOutputStream(PrintWriter pw) {
        outputStream = pw;
    }

    public void setInputStream(BufferedReader br) {
        inputStream = br;
    }

    public void startListening() {
        Thread listenThread = new Thread(new Listener());
        listenThread.start();
    }

    private void checkHitP1AndReport(String returned) {
        String[] coords = returned.split("-");
        int col = Integer.parseInt(coords[0]);
        int row = Integer.parseInt(coords[1]);
        int hitOrMiss = 0;
        int type = 0;

        for (int i = 1; i < 6; i++) {
            if (shipLocations[i][row][col] == 1) {
                hitOrMiss = 1;
                typeCounter[i]++;
                type = i;
                hitCounter++;
            }
        }

        bottomGrid.markHit(col, row, hitOrMiss);
        sendHit(hitOrMiss);
        checkForSinkP1(type);

        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            System.out.println("InterruptedException");
        }
        checkHitCounterForGameOver();
    }

    private void checkHitCounterForGameOver() {
        if (hitCounter == 17) {
            gameOverP1();
        }
    }

    public void checkHitP2(String returned) {
        gameGui.getTopGrid().fillRectangle(returned);
    }

    private void sendHit(int hitOrMiss) {
        outputStream.println("result " + hitOrMiss);
        outputStream.flush();
        setMyTurn(true);
        gameGui.setP1Turn();
    }

    public class Listener implements Runnable {
        String receivedData;
        public void run() {
            try {
                while (!shutdown && (receivedData = inputStream.readLine()) != null) {
                    if (receivedData.contains("-")) {
                        checkHitP1AndReport(receivedData);
                    } else if (receivedData.contains("result")) {
                        checkHitP2(receivedData);
                    } else if (receivedData.contains("roll")) {
                        setpTwoRoll(receivedData);
                    } else if (receivedData.contains("ready")) {
                        setpTwoReady();
                    } else if (receivedData.contains("sank")) {
                        checkForSinkP2(receivedData);
                    } else if (receivedData.contains("over")) {
                        gameOverP2();
                    }
                }
                if (inputStream.readLine() == null) {
                    Platform.runLater(() -> {
                        new GameGui().drawGui(new Stage());
                        new ErrorBox("Opponent Has Disconnected", "Opponent has Disconnected");
                    });
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
