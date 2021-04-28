package ClientsFiles;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LogIn extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root= FXMLLoader.load(getClass().getResource("Login.fxml"));
        root.setId("pane");
        stage.setTitle("Login");
        Scene scene = new Scene(root,400,500);
        scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
