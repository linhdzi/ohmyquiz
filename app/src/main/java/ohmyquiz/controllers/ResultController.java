package ohmyquiz.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ResultController implements Initializable {
    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox top;

    @FXML
    private VBox gotoActivitiesVbox;

    @FXML
    private VBox alertVbox;

    @FXML
    private HBox congraturationHbox;

    @FXML
    private HBox percentHbox;

    @FXML
    private HBox scoreHbox;

    @FXML
    private HBox reviewHbox;

    @FXML
    private HBox sumUpHbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        borderPane.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            responseWidth(newWidth.doubleValue());
        });

        borderPane.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            responseHeight(newHeight.doubleValue());
        });
    }

    public void responseWidth(double totalWidth) {
        double alertVboxWidth = totalWidth * 0.57;

        alertVbox.setPrefWidth(alertVboxWidth);
        
    }

    public void responseHeight(double totalHeight) {

        double gotoActivitiesVBoxHeight = totalHeight * 0.15;
        double topHeght = top.getPrefHeight();
        double alertVboxHeight = totalHeight - gotoActivitiesVBoxHeight - topHeght;
        double scoreHboxHeight = alertVboxHeight * 0.16;
        double congraturationHboxHeight = alertVboxHeight * 0.18;
        double sumUpHboxHeight = alertVboxHeight * 0.30;
        double reviewHboxHeight = alertVboxHeight * 0.12;
        double percentHboxHeight = alertVboxHeight * 0.24;

        gotoActivitiesVbox.setPrefHeight(gotoActivitiesVBoxHeight);
        congraturationHbox.setPrefHeight(congraturationHboxHeight);
        scoreHbox.setPrefHeight(scoreHboxHeight);
        alertVbox.setPrefHeight(alertVboxHeight);
        reviewHbox.setPrefHeight(reviewHboxHeight);
        sumUpHbox.setPrefHeight(sumUpHboxHeight);
        percentHbox.setPrefHeight(percentHboxHeight);

    }
}
