import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Created by tmjon on 7/20/2017.
 */
public class Battleship extends Rectangle{

    private final static int S_W = 123;
    private final static int S_H = 30;
    private double startingXPlacement;
    private double startingYPlacement;
    private static boolean isHorizontal = true;
    private static boolean hasBeenDropped;

    public Rectangle create(MouseEvent event) {
        Rectangle newBattleship = new Rectangle(startingXPlacement = event.getSceneX() - 60,
                startingYPlacement = event.getSceneY() - 180, S_W, S_H);
        newBattleship.setFill(Color.rgb(0, 200, 200, 0.5));
        return newBattleship;
    }

    public static void setHasBeenDropped(boolean tf) {
        hasBeenDropped = tf;
    }

    public static boolean getHasBeenDropped() {
        return hasBeenDropped;
    }

    public static void rotateShip(Rectangle ship) {
        if (!hasBeenDropped) {
            if (isHorizontal) {
                ship.setWidth(S_H);
                ship.setHeight(S_W);
                isHorizontal = false;
            } else {
                ship.setWidth(S_W);
                ship.setHeight(S_H);
                isHorizontal = true;
            }
        } else {
            new ErrorBox("Cannot rotate after dropped.");
        }
    }

    public static boolean getIsHorizontal() {
        return isHorizontal;
    }


    public static void setIsHorizontal(boolean tf) {
        isHorizontal = tf;
    }
}