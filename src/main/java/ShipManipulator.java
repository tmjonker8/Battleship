import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * Created by tmjon on 7/27/2017.
 *
 * This class controls and handles the ship (game piece) creation, implementation, and maniuplation.
 */
public class ShipManipulator  {

    private Rectangle carrier;
    private Rectangle battleship;
    private Rectangle cruiser;
    private Rectangle submarine;
    private Rectangle destroyer;
    private final GameGui gameGui;

    public ShipManipulator(GameGui gameGui) {
        this.gameGui = gameGui;
    }

    public void resetShipDroppedFlags() {
        Carrier.setHasBeenDropped(false);
        Battleship.setHasBeenDropped(false);
        Cruiser.setHasBeenDropped(false);
        Submarine.setHasBeenDropped(false);
        Destroyer.setHasBeenDropped(false);
    }

    public void rotateShipSelected(Rectangle ship) {
        if (ship.equals(carrier)) {
            Carrier.rotateShip(ship);
        } else if (ship.equals(battleship)) {
            Battleship.rotateShip(ship);
        } else if (ship.equals(cruiser)) {
            Cruiser.rotateShip(ship);
        } else if (ship.equals(submarine)) {
            Submarine.rotateShip(ship);
        } else if (ship.equals(destroyer)) {
            Destroyer.rotateShip(ship);

        }
    }

    public boolean getShipSelectedIsHorizontal(Rectangle ship) {
        if (ship.equals(carrier)) {
            return Carrier.getIsHorizontal();
        } else if (ship.equals(battleship)) {
            return Battleship.getIsHorizontal();
        } else if (ship.equals(cruiser)) {
            return Cruiser.getIsHorizontal();
        } else if (ship.equals(submarine)) {
            return Submarine.getIsHorizontal();
        } else if (ship.equals(destroyer)) {
            return Destroyer.getIsHorizontal();
        } else {
            return false;
        }
    }

    public void resetIsHorizontalFlags() {
        Carrier.setIsHorizontal(true);
        Battleship.setIsHorizontal(true);
        Cruiser.setIsHorizontal(true);
        Submarine.setIsHorizontal(true);
        Destroyer.setIsHorizontal(true);
    }

    public boolean hasBeenDropped(Rectangle ship) {
        if (ship.equals(destroyer)) {
            return Destroyer.getHasBeenDropped();
        } else if (ship.equals(carrier)) {
            return Carrier.getHasBeenDropped();
        } else if (ship.equals(battleship)) {
            return Battleship.getHasBeenDropped();
        } else if (ship.equals(cruiser)) {
            return Cruiser.getHasBeenDropped();
        } else if (ship.equals(submarine)) {
            return Submarine.getHasBeenDropped();
        } else {

            return false;
        }
    }

    public void selectiveStatReset(Rectangle ship) {
        if (ship.equals(carrier)) {
            Carrier.setHasBeenDropped(false);
            Carrier.setIsHorizontal(true);
            gameGui.toggleCarrierButton(false);
        } else if (ship.equals(battleship)) {
            Battleship.setHasBeenDropped(false);
            Battleship.setIsHorizontal(true);
            gameGui.toggleBattleshipButton(false);
        } else if (ship.equals(cruiser)) {
            Cruiser.setHasBeenDropped(false);
            Cruiser.setIsHorizontal(true);
            gameGui.toggleCruiserButton(false);
        } else if (ship.equals(destroyer)) {
            Destroyer.setHasBeenDropped(false);
            Destroyer.setIsHorizontal(true);
            gameGui.toggleDestroyerButton(false);
        } else if (ship.equals(submarine)) {
            Submarine.setHasBeenDropped(false);
            Submarine.setIsHorizontal(true);
            gameGui.toggleSubmarineButton(false);
        }
    }

    public void addCarrier(Rectangle ship) {
        carrier = ship;
    }

    public void addBattleship(Rectangle ship) {
        battleship = ship;
    }

    public void addCruiser(Rectangle ship) {
        cruiser = ship;
    }

    public void addSubmarine(Rectangle ship) {
        submarine = ship;
    }

    public void addDestroyer(Rectangle ship) {
        destroyer = ship;
    }

    public Rectangle getCarrier() {
        return carrier;
    }

    public Rectangle getBattleship() {
        return battleship;
    }

    public Rectangle getCruiser() {
        return cruiser;
    }

    public Rectangle getSubmarine() {
        return submarine;
    }

    public Rectangle getDestroyer() {
        return destroyer;
    }
}
