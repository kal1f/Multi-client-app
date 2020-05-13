package chat.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/chat/client/templates/StartView.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Chat");
        stage.setScene(scene);
        stage.show();

    }
}
