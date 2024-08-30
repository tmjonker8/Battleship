import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by tmjon on 7/20/2017.
 */
public class Carrier extends Rectangle {

    private final static int S_W = 154;
    private final static int S_H = 30;
    private double startingXPlacement;
    private double startingYPlacement;
    private static boolean isHorizontal = true;
    private static boolean hasBeenDropped;

    public Rectangle create(MouseEvent event) {
        Rectangle newCarrier = new Rectangle(startingXPlacement = event.getSceneX(),
                startingYPlacement = event.getSceneY() - 200, S_W, S_H);
        newCarrier.setFill(Color.rgb(0, 200, 0, 0.5));
        return newCarrier;
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
