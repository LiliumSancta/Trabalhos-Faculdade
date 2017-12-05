package musiclib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/MusicLib.fxml"));
        primaryStage.setTitle("MusicLib");
        primaryStage.setScene(new Scene(root, 620, 417));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
