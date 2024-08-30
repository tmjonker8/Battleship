import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class acts as a bridge between the Main class and the implementation of the GUI.
 *
 */

public class Bridge extends Application {

    @Override
    public void start(Stage stage) {
        GameGui gameGui = new GameGui();
        gameGui.drawGui(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
