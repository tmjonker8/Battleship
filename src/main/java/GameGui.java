import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * Created by tmjon on 7/19/2017.
 *
 * The main GUI code for the game.
 *
 */
public class GameGui {

    private final Group root = new Group();
    private Rectangle shipSelected;
    private final ShipManipulator shipManipulator = new ShipManipulator(this);
    private final MouseGestures mouseGestures = new MouseGestures(shipManipulator);
    private final BottomGrid bottomGrid = new BottomGrid(shipManipulator, mouseGestures, this);
    private Button carrierButton;
    private Button battleshipButton;
    private Button cruiserButton;
    private Button submarineButton;
    private Button destroyerButton;
    private Button clearLastButton;
    private Button clearAllButton;
    private Button finalizeButton;
    private Button rotateButton;
    private Button rollButton;
    private Menu newGame;
    private Menu currentGame;
    private final Label p1Turn = new Label();
    private final Label p2Turn = new Label();
    private final Label roll1 = new Label();
    private final Label roll2 = new Label();
    private final Label p1Ready = new Label("You");
    private final Label p2Ready = new Label("Them");
    private final HBox gridBox = new HBox();
    private final HBox p1StatBox = new HBox();
    private final HBox p2StatBox = new HBox();
    private Stage stage;
    private double initialStageWidth;
    private double initialStageHeight;
    private GameClient gameClient;
    private GameServer gameServer;
    private final GameHelper gameHelper = new GameHelper(bottomGrid, this, gameClient, gameServer);
    private final TopGrid topGrid = new TopGrid(gameHelper);

    public void drawGui(Stage primaryStage) {

        stage = primaryStage;
        stage.setTitle("Battleship -");
        stage.setResizable(false);


        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(stage.widthProperty());

        Menu gameMenu = new Menu("_Game");
        newGame = new Menu("_New Game");

        MenuItem newGameHost = new MenuItem("_Host");
        MenuItem newGameConnect = new MenuItem("_Connect");

        newGameHost.setOnAction( e -> {
            newGame();
            gameServer = new GameServer(this);
        });
        newGameConnect.setOnAction( e -> {
            newGame();
            gameClient = new IpBox(stage.getX(), stage.getY(), this).returnGameClient();
        });

        newGame.getItems().addAll(newGameHost, newGameConnect);
        currentGame = new Menu("_Current Game");
        MenuItem currentGameDisconnect = new MenuItem("_Disconnect");
        currentGameDisconnect.setOnAction( e-> {
            disconnectMenuItemAction();
        });

        currentGame.getItems().add(currentGameDisconnect);
        gameMenu.getItems().addAll(newGame,currentGame);
        menuBar.getMenus().add(gameMenu);

        carrierButton = new Button("Carrier");
        battleshipButton = new Button("Battleship");
        cruiserButton = new Button("Cruiser");
        submarineButton = new Button("Submarine");
        destroyerButton = new Button("Destroyer");

        carrierButton.setOnMouseClicked(event -> {
            shipManipulator.addCarrier(new Carrier().create(event));
            placeShip(shipManipulator.getCarrier());
        });

        battleshipButton.setOnMouseClicked(event -> {
            shipManipulator.addBattleship(new Battleship().create(event));
            placeShip(shipManipulator.getBattleship());
        });

        cruiserButton.setOnMouseClicked(event -> {
            shipManipulator.addCruiser(new Cruiser().create(event));
            placeShip(shipManipulator.getCruiser());
        });

        submarineButton.setOnMouseClicked(event -> {
            shipManipulator.addSubmarine(new Submarine().create(event));
            placeShip(shipManipulator.getSubmarine());
        });

        destroyerButton.setOnMouseClicked(event -> {
            shipManipulator.addDestroyer(new Destroyer().create(event));
                placeShip(shipManipulator.getDestroyer());
        });

        HBox shipBox = new HBox();
        shipBox.getChildren().addAll(carrierButton,battleshipButton,cruiserButton,submarineButton,destroyerButton);
        shipBox.setAlignment(Pos.CENTER);
        shipBox.setPadding(new Insets(20, 0, 0, 0));

        clearLastButton = new Button("Clear Last");
        clearAllButton = new Button("Clear All");
        finalizeButton = new Button("Finalize");
        rotateButton = new Button("Rotate");
        rollButton = new Button("Roll");


        rotateButton.setOnMouseClicked(event -> rotateButtonAction());

        clearLastButton.setOnMouseClicked(event -> clearLastButtonAction());

        clearAllButton.setOnMouseClicked(event -> clearAllButtonAction());

        finalizeButton.setOnMouseClicked(event -> finalizeButtonAction());

        rollButton.setOnMouseClicked(event -> rollButtonAction());

        disableButtons();
        disableMenuItem(currentGame);

        p1Ready.setTextFill(Color.RED);
        p2Ready.setTextFill(Color.RED);

        HBox rotateBox = new HBox();
        rotateBox.getChildren().addAll(rotateButton, clearLastButton, clearAllButton, finalizeButton, rollButton);
        rotateBox.setAlignment(Pos.CENTER);

        HBox p1TurnBox = new HBox();
        p1TurnBox.getChildren().add(p1Turn);
        p1TurnBox.setPadding(new Insets(0, 0, 0, 10));

        HBox p1ReadyBox = new HBox();
        p1ReadyBox.getChildren().add(p1Ready);

        HBox roll1Box = new HBox();
        roll1Box.getChildren().add(roll1);
        roll1Box.setPadding(new Insets(0, 10, 0, 0));

        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        p1StatBox.getChildren().addAll(p1TurnBox, region1, p1ReadyBox, region2, roll1Box);

        VBox bottomGridBox = new VBox();
        bottomGridBox.getChildren().addAll(bottomGrid.getGridPane(), p1StatBox);

        Region region3 = new Region();
        HBox.setHgrow(region3, Priority.ALWAYS);

        Region region4 = new Region();
        HBox.setHgrow(region4, Priority.ALWAYS);

        HBox p2TurnBox = new HBox();
        p2TurnBox.getChildren().add(p2Turn);
        p2TurnBox.setPadding(new Insets(0, 0, 0, 10));

        HBox p2ReadyBox = new HBox();
        p2ReadyBox.getChildren().add(p2Ready);
        p2ReadyBox.setPadding(new Insets(0, 0, 0, 10));

        HBox roll2Box = new HBox();
        roll2Box.getChildren().add(roll2);
        roll2Box.setPadding(new Insets(0, 10, 0, 0));

        p2StatBox.getChildren().addAll(p2TurnBox, region3, p2ReadyBox, region4, roll2Box);

        VBox topGridBox = new VBox();
        topGridBox.getChildren().addAll(topGrid.getGridPane(), p2StatBox);

        gridBox.getChildren().addAll(bottomGridBox, topGridBox);
        gridBox.setVisible(false);

        VBox combined = new VBox();
        combined.getChildren().addAll(menuBar, gridBox, shipBox, rotateBox);
        combined.setAlignment(Pos.CENTER);
        combined.setPadding(new Insets(0, 0, 10, 0));
        root.getChildren().addAll(combined);

        Scene newScene = new Scene(root, Color.LIGHTGRAY);
        stage.setScene(newScene);
        stage.show();
        initialStageWidth = stage.getWidth();
        initialStageHeight = stage.getHeight();
        System.out.println(newScene.getHeight());
    }


    private void rollButtonAction() {

        int roll = ThreadLocalRandom.current().nextInt(10);
        gameHelper.sendRoll(roll);
        setRoll1(roll);
        rollButton.setDisable(true);
    }

    public void disconnectMenuItemAction() {

        if (gameHelper.getIsHost() && gameServer.getServerSocket() != null) {
            gameServer.closeConnection();
        } else if (!gameHelper.getIsHost() && gameClient.getSocket() != null) {
            gameClient.closeConnection();
        }
        setStageTitle("");
        enableNewGameMenu();
        disableMenuItem(currentGame);
        bottomGrid.removeAll();
        bottomGrid.removeGrid();
        topGrid.removeGrid();
        togglePaneVisibility(gridBox, false);
        gameHelper.newGameSetup();
        resizeAndCenterStageDisconnect();
    }

    private void resizeAndCenterStageNewGame() {
        stage.sizeToScene();
        stage.centerOnScreen();
    }

    private void resizeAndCenterStageDisconnect() {

        stage.setWidth(initialStageWidth);
        stage.setHeight(initialStageHeight);
        stage.centerOnScreen();
        stage.sizeToScene();
    }

    private void clearAllButtonAction() {

        root.getChildren().remove(shipSelected);
        bottomGrid.removeAll();
        enableButtons();
    }

    private void clearLastButtonAction() {

        if (shipSelected == null) {
            if (bottomGrid.getShipList().size() > 0) {
                bottomGrid.removeLast();
            }
        } else {
            removeShip(shipSelected);
            enableButtons();
            selectiveDisableButtons();
        }
    }

    public void removeShip(Rectangle ship) {
        root.getChildren().remove(ship);
    }

    private void rotateButtonAction() {
        if (shipSelected != null) {
            shipManipulator.rotateShipSelected(shipSelected);
        } else {
            new ErrorBox("Please select a battleship.");
        }
    }

    private void finalizeButtonAction() {

        if (bottomGrid.getShipList().size() == 5) {

            if (gameHelper.getP1Rolled()) {
                disableButtons();
                gameHelper.sendReady();
                setP1Ready(true);
            } else {
                new ErrorBox("Please roll for first move first.");
            }
        } else {
            new ErrorBox("Please drop all 5 ships onto your grid first.");
        }
    }

    public void setP1Turn() {
        Platform.runLater(() -> {
            p1Turn.setText("My Turn");
            p2Turn.setText("");
        });
    }

    public void setP2Turn() {
        Platform.runLater(() -> {
            p2Turn.setText("Their Turn");
            p1Turn.setText("");
        });
    }

    private void newGame() {
        setStageTitle("");
        topGrid.drawGrid();
        bottomGrid.drawGrid();
        togglePaneVisibility(gridBox, true);
        mouseGestures.setBottomGrid(bottomGrid);
        disableMenuItem(newGame);
        enableMenuItem(currentGame);
        resetRolls();
        resetTurnLabels();
        setP1Ready(false);
        setP2Ready(false);
        bottomGrid.removeAll();
        shipManipulator.resetShipDroppedFlags();
        resizeAndCenterStageNewGame();
    }

    private void resetTurnLabels() {
        p1Turn.setText("");
        p2Turn.setText("");
    }

    public void setStageTitle(String title) {
        Platform.runLater(() -> stage.setTitle("Battleship " + "- " + title));
    }

    private void setP1Ready(boolean ready) {
        if (ready) p1Ready.setTextFill(Color.GREEN);
        else p1Ready.setTextFill(Color.RED);
    }

    public void setP2Ready(boolean ready) {
        if (ready) p2Ready.setTextFill(Color.GREEN);
        else p2Ready.setTextFill(Color.RED);
    }

    private void setRoll1(int roll) {
        roll1.setText("My Roll: " + roll);
    }

    private void resetRolls() {
        roll1.setText("");
        roll2.setText("");
    }

    public void setRoll2(int roll) {
        roll2.setText("Their Roll: " + roll);
    }

    public void placeShip(Rectangle ship) {
        root.getChildren().addAll(ship);
        mouseGestures.selectShip(ship); //This adds the MousePressed, Dragged, and Released gestures to each ship.
        setShipSelected(ship);
        disableButtons();
        enableRotateButton();
        enableClearButtons();
    }

    public void setShipSelected(Rectangle ship) {
        shipSelected = ship;
    }

    public void selectiveDisableButtons() {
        if (Carrier.getHasBeenDropped()) {
            carrierButton.setDisable(true);
        } if(Battleship.getHasBeenDropped()) {
            battleshipButton.setDisable(true);
        }  if(Cruiser.getHasBeenDropped()) {
            cruiserButton.setDisable(true);
        }  if(Submarine.getHasBeenDropped()) {
            submarineButton.setDisable(true);
        } if(Destroyer.getHasBeenDropped()) {
            destroyerButton.setDisable(true);
        }
    }

    public void toggleCarrierButton(boolean tf) {
        carrierButton.setDisable(tf);
    }

    public void toggleBattleshipButton(boolean tf) {
        battleshipButton.setDisable(tf);
    }

    public void toggleCruiserButton(boolean tf) {
        cruiserButton.setDisable(tf);
    }

    public void toggleDestroyerButton(boolean tf) {
        destroyerButton.setDisable(tf);
    }

    public void toggleSubmarineButton(boolean tf) {
        submarineButton.setDisable(tf);
    }

    public void enableButtons() {
        carrierButton.setDisable(false);
        battleshipButton.setDisable(false);
        cruiserButton.setDisable(false);
        submarineButton.setDisable(false);
        destroyerButton.setDisable(false);
        finalizeButton.setDisable(false);
        if (bottomGrid.getShipList().size() != 5) {
            rotateButton.setDisable(false);
        }
    }

    public void enableRollButton() {
        rollButton.setDisable(false);
    }

    public void enableNewGameMenu() {
        newGame.setDisable(false);
    }

    private void disableMenuItem(MenuItem menuItem) {
        menuItem.setDisable(true);
    }

    private void enableMenuItem(MenuItem menuItem) {
        menuItem.setDisable(false);
    }

    private void enableClearButtons() {
        clearAllButton.setDisable(false);
        clearLastButton.setDisable(false);
    }

    private void togglePaneVisibility(Pane pane, boolean tf) {
        pane.setVisible(tf);
    }

    public void disableClearButtons() {
        clearLastButton.setDisable(true);
        clearAllButton.setDisable(true);
    }

    private void disableButtons() {
        carrierButton.setDisable(true);
        battleshipButton.setDisable(true);
        cruiserButton.setDisable(true);
        submarineButton.setDisable(true);
        destroyerButton.setDisable(true);
        rotateButton.setDisable(true);
        clearLastButton.setDisable(true);
        clearAllButton.setDisable(true);
        finalizeButton.setDisable(true);
        rollButton.setDisable(true);
    }

    private void enableRotateButton() {
        rotateButton.setDisable(false);
    }

    public void checkButtons() {
        enableButtons();
        selectiveDisableButtons();
        if(!gameHelper.getP1Rolled()) enableRollButton();
    }

    public TopGrid getTopGrid() {
        return topGrid;
    }

    public GameHelper getGameHelper() {
        return gameHelper;
    }

    public GameGui getGameGui() {

        return this;
    }
}
