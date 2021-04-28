package ClientsFiles;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.Socket;

public class Main extends Application {
//    static int role = 1;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("client.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene=new Scene(root, 1150, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


//    public static void main(String[] args) {
//        launch(args);
//    }

}
