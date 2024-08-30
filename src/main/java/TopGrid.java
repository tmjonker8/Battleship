import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Created by tmjon on 7/27/2017.
 *
 * This is the class that represents your opponents game board.
 */
public class TopGrid extends GridPane {

    private final Rectangle[][] grid1 = new Rectangle[12][12];
    private final GridPane topGrid = new GridPane();
    private final GameHelper gameHelper;


    public TopGrid (GameHelper gh) {
        gameHelper = gh;
    }

    public void drawGrid() {

        for (int i = 0; i <= 11; i++) {
            for (int j = 0; j <= 11; j++) {
                int colIndex = i;
                int rowIndex = j;
                grid1[i][j] = new Rectangle();
                grid1[i][j].setStroke(Color.BLACK);
                grid1[i][j].setFill(Color.LIGHTBLUE);
                grid1[i][j].setStrokeWidth(1);
                grid1[i][j].setWidth(30);
                grid1[i][j].setHeight(30);
                grid1[i][j].setOnMouseClicked( e -> getCoordinates(colIndex, rowIndex));
                topGrid.setAlignment(Pos.TOP_CENTER);
                topGrid.add(grid1[i][j], i, j);
            }
        }
        topGrid.setPadding(new Insets(10, 5, 0, 0));
    }

    public void removeGrid() {
        topGrid.getChildren().clear();
        topGrid.setPadding(new Insets(0,0,0,0));
    }

    public GridPane getGridPane() {
        return topGrid;
    }

    private int colIndex, rowIndex;

    private void getCoordinates(int col, int row) {
        if (gameHelper.getBothReady()) {
            if (gameHelper.getMyTurn()) {
                if (grid1[col][row].getFill() == Color.LIGHTBLUE) {
                    colIndex = col;
                    rowIndex = row;
                    gameHelper.sendCoordinates(col, row);
                } else {
                    Platform.runLater(() -> new ErrorBox("Pick another coordinate.",
                            "Already been targeted."));
                }
            }
       }
    }

    public void fillRectangle(String result) {
        if (result.contains("1")) {
            grid1[colIndex][rowIndex].setFill(Color.GREEN);
        } else {
            grid1[colIndex][rowIndex].setFill(Color.BLUE);
        }
    }
}
