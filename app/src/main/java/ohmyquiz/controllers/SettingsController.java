package ohmyquiz.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class SettingsController implements Initializable {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox mainVbox;

    @FXML
    private VBox menuBarVbox;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            responseWidth(newWidth.doubleValue());
        });
    }

    public void responseWidth(double totalWidth) {
        menuBarVbox.setPrefWidth(totalWidth);
        mainVbox.setPrefWidth(totalWidth);
        
    }
}
