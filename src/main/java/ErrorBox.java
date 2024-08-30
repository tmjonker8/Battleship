import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * Created by tmjon on 7/5/2017.
 */
public class ErrorBox {

    private final Stage stage;

    public ErrorBox(String message, String title) {

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setResizable(false);

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> stage.close());

        Label errorMessage = new Label();
        errorMessage.setText(message);

        HBox messageBox = new HBox(20);
        messageBox.getChildren().add(errorMessage);
        messageBox.setAlignment(Pos.CENTER);

        VBox buttonBox = new VBox(20);
        buttonBox.getChildren().addAll(messageBox, okButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10,10,10,10));

        Scene setScene = new Scene(buttonBox);
        stage.setScene(setScene);
        stage.show();
    }

    public ErrorBox(String message) {

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Error");
        stage.setResizable(false);

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> stage.close());

        Label errorMessage = new Label();
        errorMessage.setText(message);

        HBox messageBox = new HBox(20);
        messageBox.getChildren().add(errorMessage);
        messageBox.setAlignment(Pos.CENTER);

        VBox buttonBox = new VBox(20);
        buttonBox.getChildren().addAll(messageBox, okButton);
        buttonBox.setAlignment(Pos.CENTER);

        Scene setScene = new Scene(buttonBox);
        stage.setScene(setScene);
        stage.show();
    }
}

