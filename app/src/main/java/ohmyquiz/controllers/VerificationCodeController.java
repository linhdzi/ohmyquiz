package ohmyquiz.controllers;

import java.io.IOException;

import org.bson.Document;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ohmyquiz.App;
import ohmyquiz.bussinesses.UserBussiness;
import ohmyquiz.models.User;

public class VerificationCodeController {
    @FXML
    private BorderPane borderPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField token1Field;

    @FXML
    private TextField token2Field;

    @FXML
    private TextField token3Field;

    @FXML
    private TextField token4Field;

    @FXML
    private TextField token5Field;

    @FXML
    private TextField token6Field;

    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> token1Field.requestFocus());
    }

    @FXML
    private void moveToNextInput(KeyEvent event) {
        TextField input = (TextField) event.getSource();
        int currentLength = input.getText().length();

        if (event.getCharacter().matches("[^0-9]")) {
            event.consume();
            input.setText("");
        } else {
            if (currentLength == 1) {
                Node nextField = input.getParent().getChildrenUnmodifiable().stream()
                        .filter(node -> node instanceof TextField
                                && GridPane.getColumnIndex(node) > GridPane.getColumnIndex(input))
                        .findFirst()
                        .orElse(null);

                if (nextField instanceof TextField) {
                    ((TextField) nextField).requestFocus();
                }
            }
        }
    }

    @FXML
    private void moveToPreviousInput(KeyEvent event) {
        TextField input = (TextField) event.getSource();
        int currentLength = input.getText().length();

        if (event.getCode() == KeyCode.BACK_SPACE && currentLength == 0) {
            Node previousInput = input.getParent().getChildrenUnmodifiable().stream()
                    .filter(node -> node instanceof TextField
                            && GridPane.getColumnIndex(node) < GridPane.getColumnIndex(input))
                    .reduce((first, second) -> second)
                    .orElse(null);

            if (previousInput instanceof TextField) {
                ((TextField) previousInput).requestFocus();
            }
        }
    }

    @FXML
    private void backToForgotPasswordUI(ActionEvent event) {
        try {
            double width = borderPane.getScene().getWidth();
            double height = borderPane.getScene().getHeight();

            Parent root = FXMLLoader.load(App.class.getResource("/fxml/ForgotPassword.fxml"));
            Scene scene = new Scene(root, width, height);

            Stage stage = (Stage) borderPane.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void nextToResetPasswordUI(ActionEvent event) {
        String token1 = token1Field.getText();
        String token2 = token2Field.getText();
        String token3 = token3Field.getText();
        String token4 = token4Field.getText();
        String token5 = token5Field.getText();
        String token6 = token6Field.getText();

        if (token1.equals("") || token2.equals("") || token3.equals("") || token4.equals("") || token5.equals("")
                || token6.equals("")) {
            showErrorAlert("Please enter verification code!");
        } else {
            String token = "";
            token = token1
                    + token2
                    + token3
                    + token4
                    + token5
                    + token6;

            UserBussiness userBussiness = new UserBussiness();
            Document checkToken = userBussiness.checkToken(token);

            if (checkToken != null) {

                try {
                    double width = borderPane.getScene().getWidth();
                    double height = borderPane.getScene().getHeight();

                    FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/ResetPassword.fxml"));
                    Parent root = loader.load();

                    ResetPasswordController resetPasswordController = loader.getController();
                    resetPasswordController.setEmail((String) checkToken.get("email"));

                    Scene scene = new Scene(root, width, height);
                    Stage stage = (Stage) borderPane.getScene().getWindow();
                    stage.setScene(scene);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showErrorAlert("Verification is invalid or expired! " + token);
            }

        }

    }

    @FXML
    private void resendCode(ActionEvent event) {
        UserBussiness userBussiness = new UserBussiness();
        Boolean checkEmail = userBussiness.checkEmail(email);

        if (checkEmail == true) {
            ForgotPasswordController forgotPasswordController = new ForgotPasswordController();
            forgotPasswordController.sendEmail(email, userBussiness);

            boolean isEmailSent = forgotPasswordController.sendEmail(email, userBussiness);
            if (isEmailSent == false) {
                showErrorAlert("Can not send verification code. Please try again!");
            }
        }

    }

    public void showErrorAlert(String contentText) {
        Alert errAlert = new Alert(AlertType.ERROR);
        errAlert.setContentText(contentText);
        errAlert.show();
    }
}
