package ohmyquiz.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ohmyquiz.App;
import javafx.scene.layout.HBox;

public class AreYouReadyController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private VBox readyLabelVbox;

    @FXML
    private VBox chooseModeVbox;
    @FXML
    private HBox chooseModeHbox;

    @FXML
    private HBox instructionHbox;

    @FXML
    private VBox instructionVbox;

    @FXML
    private VBox startQuizVbox;

    @FXML
    public void startButtonAction() {
        try {
            double width = anchorPane.getScene().getWidth();
            double height = anchorPane.getScene().getHeight();
            
            Parent root = FXMLLoader.load(App.class.getResource("/fxml/UiGame.fxml"));
            Scene scene = new Scene(root,width,height);

            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        anchorPane.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            responseWidth(newWidth.doubleValue());
        });

        anchorPane.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            responseHeight(newHeight.doubleValue());
        });
    }

    public void responseWidth(double totalWidth) {
        double instructionWidth = totalWidth * 0.52;

        readyLabelVbox.setPrefWidth(totalWidth);
        instructionHbox.setPrefWidth(instructionWidth);
        chooseModeVbox.setPrefWidth(totalWidth);
        chooseModeHbox.setPrefWidth(instructionWidth);
        instructionVbox.setPrefWidth(totalWidth);
        startQuizVbox.setPrefWidth(totalWidth);
    }

    public void responseHeight(double totalHeight) {
        double readyLabelHeight = totalHeight * 0.15;
        double instructionHeight = totalHeight * 0.58;
        double chooseModeHeight = totalHeight * 0.13;
        double startQuizHeight = totalHeight * 0.14;

        readyLabelVbox.setPrefHeight(readyLabelHeight);
        instructionVbox.setPrefHeight(instructionHeight);
        instructionHbox.setPrefHeight(instructionHeight);
        chooseModeVbox.setPrefHeight(chooseModeHeight);
        startQuizVbox.setPrefHeight(startQuizHeight);
    }
}
