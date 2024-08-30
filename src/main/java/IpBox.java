import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by tmjon on 7/5/2017.
 */
public class IpBox {

    private final Stage stage;
    private GameClient gameClient;

    public IpBox(double x, double y, GameGui gameGui) {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Enter IP Address");
        stage.setResizable(false);
        stage.setX(x + 170);
        stage.setY(y + 170);

        Label mainText = new Label("Enter IP Address:");

        TextField ipTextField1 = new TextField();
        ipTextField1.setPrefWidth(60);
        TextField ipTextField2 = new TextField();
        ipTextField2.setPrefWidth(60);
        TextField ipTextField3 = new TextField();
        ipTextField3.setPrefWidth(60);
        TextField ipTextField4 = new TextField();
        ipTextField4.setPrefWidth(60);

        HBox ipBox = new HBox(20);
        ipBox.getChildren().addAll(ipTextField1, new Label("."), ipTextField2,
                new Label("."), ipTextField3, new Label("."), ipTextField4);
        ipBox.setAlignment(Pos.CENTER);

        Button cancelButton = new Button("Cancel");
        Button okButton = new Button("OK");

        cancelButton.setOnAction(e -> {
            gameGui.disconnectMenuItemAction();
            stage.close();
        });
        okButton.setOnAction(e -> {
            gameClient = new GameClient(ipTextField1.getText() +"."+ ipTextField2.getText() +"."+
                    ipTextField3.getText() +"."+ ipTextField4.getText(), gameGui);
            stage.close();
        });

        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(okButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox mainBox = new VBox(20);
        mainBox.getChildren().addAll(mainText, ipBox, buttonBox);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(10,10,10,10));

        Scene setScene = new Scene(mainBox);
        stage.setScene(setScene);
        stage.show();
        returnGameClient();
    }

    public GameClient returnGameClient() {
        return gameClient;
    }
}

