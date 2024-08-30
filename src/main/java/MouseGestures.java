import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 * Created by tmjon on 7/24/2017.
 *
 * This class controls the dragging of game pieces across the game board.
 */
public class MouseGestures {

    private Rectangle shipSelected;
    private int realRow;
    private int realCol;
    private ShipManipulator shipManipulator;
    private BottomGrid bottomGrid;


    public MouseGestures(ShipManipulator sm) {

        shipManipulator = sm;
    }

    public void setBottomGrid(BottomGrid bg) {

        bottomGrid = bg;
    }

    class DragContext {
        double x;
        double y;
    }

    private final DragContext dragContextA = new DragContext();

    public void selectShip(Node node) {

        shipSelected = (Rectangle) node;
        shipSelected.setOnMousePressed(onMousePressedEventHandlerA);
        shipSelected.setOnDragDetected(onMouseDragDetectedEventHandlerA);
        shipSelected.setOnMouseDragged(onMouseDraggedEventHandlerA);
        shipSelected.setOnMouseReleased(onMouseReleasedEventHandlerA);
    }

    private final EventHandler<MouseEvent> onMousePressedEventHandlerA = event -> {
        if (event.getSource() instanceof Rectangle) {
                if (shipManipulator.hasBeenDropped((Rectangle) event.getSource())) {

                    new ErrorBox("Ship already dropped.");

            } else {
                    if (shipManipulator.getShipSelectedIsHorizontal(shipSelected)) {

                        dragContextA.x = shipSelected.getTranslateX() - shipSelected.getWidth() / 10;
                        dragContextA.y = shipSelected.getTranslateY() - shipSelected.getHeight() / 2;
                    } else {
                        dragContextA.x = shipSelected.getTranslateX() - shipSelected.getWidth() / 2;
                        dragContextA.y = shipSelected.getTranslateY() - shipSelected.getHeight() / 10;
                    }
            }
        }

        event.consume();
    };

    private final EventHandler<MouseEvent> onMouseDragDetectedEventHandlerA = event -> {

        shipSelected.startFullDrag();
        shipSelected.setMouseTransparent(true);

        event.consume();
    };

    private final EventHandler<MouseEvent> onMouseDraggedEventHandlerA = event -> {

        if (event.getSource() instanceof Rectangle) {

            shipSelected = (Rectangle) (event.getSource());
            shipSelected.setX(dragContextA.x + event.getX());
            shipSelected.setY(dragContextA.y + event.getY());

            event.consume();
        }
    };

    private final EventHandler<MouseEvent> onMouseReleasedEventHandlerA = event -> {

        try {
            bottomGrid.checkBoundsAndDropShip(realCol, realRow, shipSelected);

        } catch (IllegalArgumentException ex) {
            new ErrorBox("Ship already dropped at this location.");
        }
        shipSelected.setMouseTransparent(false);

        event.consume();
    };

    public void transferColRowIndex(int col, int row) {

        realRow = row;
        realCol = col;
    }
}