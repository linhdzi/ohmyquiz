/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ohmyquiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ohmyquiz.controllers.RegisterController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/Register.fxml"));
        Parent root = fxmlLoader.load();
        RegisterController controller = fxmlLoader.getController();
        
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);

    }
}