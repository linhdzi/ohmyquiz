package ohmyquiz.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class UiGameController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private HBox flowPane;

    @FXML
    private VBox nextVbox;

    @FXML
    private HBox answerHbox;

    @FXML
    private HBox quizHbox;

    @FXML
    private HBox currentQuestionHbox;

    @FXML
    private HBox timeHbox;

    @FXML
    private Label questionLabel;

    @FXML
    private ChoiceBox sectionChoicebox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        borderPane.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            responseWidth(newWidth.doubleValue());
        });

        borderPane.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            responseHeight(newHeight.doubleValue());
        });

        ObservableList<String> options = FXCollections.observableArrayList("Section 1", "Section 2","Section 3","Section 4");
        sectionChoicebox.setItems(options);
        sectionChoicebox.setValue("Section 1");
        
        

    }

    public void responseWidth(double totalWidth) {
        double questionLabelWidth = totalWidth * 0.55;

        questionLabel.setPrefWidth(questionLabelWidth);

    }

    public void responseHeight(double totalHeight) {
        double flowPaneHeight = totalHeight * 0.11;
        double nextVBoxHeight = totalHeight * 0.15;
        double answerHboxHeight = answerHbox.getPrefHeight();
        double quizVboxHeight = totalHeight - nextVBoxHeight - answerHboxHeight - flowPaneHeight;
        double questionLabelHeight = quizVboxHeight * 0.66;

        nextVbox.setPrefHeight(nextVBoxHeight);
        timeHbox.setPrefHeight(questionLabelHeight * 0.17);
        currentQuestionHbox.setPrefHeight(questionLabelHeight*0.17);
        quizHbox.setPrefHeight(quizVboxHeight);
        questionLabel.setPrefHeight(questionLabelHeight);
        flowPane.setPrefHeight(flowPaneHeight);

    }
}
