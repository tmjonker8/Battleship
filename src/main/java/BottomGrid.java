import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * Created by tmjon on 7/27/2017.
 *
 * This class represents your game board that you set your pieces up on.
 */
public class BottomGrid {

    private final Rectangle[][] grid2 = new Rectangle[12][12];
    private final GridPane bottomGrid = new GridPane();
    private final MouseGestures mouseGestures;
    private final ArrayList<Rectangle> shipList = new ArrayList<>();
    private final ShipManipulator shipManipulator;
    private final int[][][] shipLocations = new int[6][12][12];
    private final GameGui gameGui;
    private int[] lastTypeDropped;

    public BottomGrid(ShipManipulator sm, MouseGestures mg, GameGui gameGui) {
        this.gameGui = gameGui;
        shipManipulator = sm;
        mouseGestures = mg;
    }


    public void drawGrid() {

        for (int i = 0; i <= 11; i++) {
            for (int j = 0; j <= 11; j++) {

                int colIndex = i;
                int rowIndex = j;

                grid2[i][j] = new Rectangle();
                grid2[i][j].setStroke(Color.BLACK);
                grid2[i][j].setFill(Color.WHITE);
                grid2[i][j].setStrokeWidth(1);
                grid2[i][j].setWidth(30);
                grid2[i][j].setHeight(30);

                grid2[i][j].setOnMouseEntered(e -> {
                    //System.out.printf("Mouse entered cell [%d, %d]%n", colIndex, rowIndex);
                    mouseGestures.transferColRowIndex(colIndex, rowIndex);
                });
                //Does not work! Want to print index of each grid2[][] upon mouse drag enter.
                grid2[i][j].setOnMouseDragEntered(e -> {
                    //System.out.printf("Mouse entered cell [%d, %d]%n", colIndex, rowIndex);
                    mouseGestures.transferColRowIndex(colIndex, rowIndex);
                });

                grid2[i][j].setOnMouseDragOver(e -> {
                    //System.out.printf("Mouse entered cell [%d, %d]%n", colIndex, rowIndex);
                    mouseGestures.transferColRowIndex(colIndex, rowIndex);
                });

                bottomGrid.setAlignment(Pos.TOP_CENTER);
                bottomGrid.add(grid2[i][j], i, j);
            }
        }

        bottomGrid.setPadding(new Insets(10, 5, 0, 5));
    }

    public void removeGrid() {

        bottomGrid.getChildren().clear();

        bottomGrid.setPadding(new Insets(0,0,0,0));
    }

    public void checkBoundsAndDropShip(int col, int row, Rectangle shipSelected) {

        boolean tf = shipManipulator.getShipSelectedIsHorizontal(shipSelected);

        if (shipSelected.equals(shipManipulator.getCarrier()) && tf) {
            if (col + 5 > 12) {
                new ErrorBox("Out of Bounds");
            } else {
                add(shipManipulator.getCarrier(), col, row, 5, 1, 1);
                Carrier.setHasBeenDropped(true);
                gameGui.checkButtons();
            }
        } else if (shipSelected.equals(shipManipulator.getCarrier()) && !tf) {
            if (row + 5 > 12) {
                new ErrorBox("Out of Bounds");
            } else {
                add(shipManipulator.getCarrier(), col, row, 1, 5, 1);
                Carrier.setHasBeenDropped(true);
                gameGui.checkButtons();
            }


        } else if (shipSelected.equals(shipManipulator.getBattleship()) && tf) {
            if (col + 4 > 12) {
                new ErrorBox("Out of Bounds");
            } else {
                add(shipManipulator.getBattleship(), col, row, 4, 1, 2);
                Battleship.setHasBeenDropped(true);
                gameGui.checkButtons();
            }
        } else if (shipSelected.equals(shipManipulator.getBattleship()) && !tf) {
            if (row + 4 > 12) {
                new ErrorBox("Out of Bounds");
            } else {
                add(shipManipulator.getBattleship(), col, row, 1, 4, 2);
                Battleship.setHasBeenDropped(true);
                gameGui.checkButtons();
            }

        } else if (shipSelected.equals(shipManipulator.getCruiser()) && tf) {
            if (col + 3 > 12) {
                new ErrorBox("Out of Bounds");
            } else {
                add(shipManipulator.getCruiser(), col, row, 3, 1, 3);
                Cruiser.setHasBeenDropped(true);
                gameGui.checkButtons();
            }
        } else if (shipSelected.equals(shipManipulator.getCruiser()) && !tf) {
            if (row + 3 > 12) {
                new ErrorBox("Out of Bounds");
            } else {
                add(shipManipulator.getCruiser(), col, row, 1, 3, 3);
                Cruiser.setHasBeenDropped(true);
                gameGui.checkButtons();
            }
        }  else if (shipSelected.equals(shipManipulator.getSubmarine()) && tf) {
            if (col + 3 > 12) {
                new ErrorBox("Out of Bounds");
            } else {
                add(shipManipulator.getSubmarine(), col, row, 3, 1, 4);
                Submarine.setHasBeenDropped(true);
                gameGui.checkButtons();
            }
        } else if (shipSelected.equals(shipManipulator.getSubmarine()) && !tf) {
            if (row + 3 > 12) {
                new ErrorBox("Out of Bounds");
            } else {
                add(shipManipulator.getSubmarine(), col, row, 1, 3, 4);
                Submarine.setHasBeenDropped(true);
                gameGui.checkButtons();
            }
        } else if (shipSelected.equals(shipManipulator.getDestroyer()) && tf) {
            if (col + 2 > 12) {
                new ErrorBox("Out of Bounds");
            } else {
                add(shipManipulator.getDestroyer(), col, row, 2, 1, 5);
                Destroyer.setHasBeenDropped(true);
                gameGui.checkButtons();
            }
        } else if (shipSelected.equals(shipManipulator.getDestroyer()) && !tf) {
            if (row + 2 > 12) {
                new ErrorBox("Out of Bounds");
            } else {
                add(shipManipulator.getDestroyer(), col, row, 1, 2, 5);
                Destroyer.setHasBeenDropped(true);
                gameGui.checkButtons();
            }
        }
    }

    public GridPane getGridPane() {
        return bottomGrid;
    }

    private void add(Rectangle ship, int col, int row, int colSpan, int rowSpan, int type) {
        lastTypeDropped = new int[5];
        if (!checkCellFilled(col, row, colSpan, rowSpan)) {
            bottomGrid.add(ship, col, row, colSpan, rowSpan);
            shipList.add(ship);
            gameGui.setShipSelected(null);
            lastTypeDropped[shipList.size()-1] = type;
            for (int i = col, j = 0; j < colSpan; i++, j++) {
                for (int k = row, l = 0; l < rowSpan; k++, l++) {
                    shipLocations[type][k][i] = 1;
                }
            }
        } else {
            gameGui.placeShip(ship);
        }
    }

    private boolean checkCellFilled(int col, int row, int colSpan, int rowSpan) {
        boolean cellFilled = false;
        outer_loop:
        for (int i = 1; i < 6; i++) {
            for (int j = col, k = 0; k < colSpan; j++, k++) {
                for (int l = row, m = 0; m < rowSpan; l++, m++) {

                    if (shipLocations[i][l][j] == 1) {

                        cellFilled = true;
                        break outer_loop;
                    }
                }
            }
        }
        return cellFilled;
    }

    private void clearShipList() {
        shipList.clear();
    }

    private void decrementShipList() {

        if (shipList.size() > 0) {
            shipList.remove(shipList.size() - 1);
        }
        if (shipList.size() == 0) {
            gameGui.disableClearButtons();
        }
    }

    public void removeAll() {
        bottomGrid.getChildren().removeAll(shipList);
        resetAllShipLocations();
        clearShipList();
        shipManipulator.resetShipDroppedFlags();
        shipManipulator.resetIsHorizontalFlags();
        gameGui.disableClearButtons();
    }

    public void removeLast() {
        bottomGrid.getChildren().remove(shipList.get(shipList.size()-1));
        shipManipulator.selectiveStatReset(shipList.get(shipList.size()-1));
        resetLastShipLocations();
        decrementShipList();
    }

    public ArrayList<Rectangle> getShipList() {
        return shipList;
    }

    private void resetAllShipLocations() {
        for (int h = 1; h < 6; h++) {
            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < 12; j++) {
                    shipLocations[h][i][j] = 0;
                }
            }
        }
    }

    private void resetLastShipLocations() {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                shipLocations[lastTypeDropped[shipList.size() - 1]][i][j] = 0;
            }
        }
    }

    public int[][][] getShipLocations() {
        return shipLocations;
    }

    public void markHit(int col, int row, int hitOrMiss) {
        if (hitOrMiss == 1) {
            //Creates RED rectangle and assigns it to bottomGrid on the Application thread.
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Rectangle hit = new Rectangle(30, 30);
                    hit.setFill(Color.RED);
                    bottomGrid.add(hit, col, row);
                }
            });
        } else {
            grid2[col][row].setFill(Color.BLUE);
        }
    }
}