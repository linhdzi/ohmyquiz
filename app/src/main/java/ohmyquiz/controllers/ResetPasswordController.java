package ohmyquiz.controllers;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ohmyquiz.App;
import ohmyquiz.bussinesses.UserBussiness;


public class ResetPasswordController {
    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField newPasswordField;

    @FXML
    private TextField confirmPasswordField;

    @FXML
    private void initialize() {
        Platform.runLater(() -> newPasswordField.requestFocus());
    }

    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    @FXML
    private void backToVerificationCodeUI(ActionEvent event) {
        try {
            double width = borderPane.getScene().getWidth();
            double height = borderPane.getScene().getHeight();

            Parent root = FXMLLoader.load(App.class.getResource("/fxml/VerificationCode.fxml"));
            Scene scene = new Scene(root, width, height);

            Stage stage = (Stage) borderPane.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void nextToLoginUI(ActionEvent event) {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (newPassword.equals("")) {
            showErrorAlert("New Password must be not empty!");
        } else if (confirmPassword.equals("")) {
            showErrorAlert("Confirm Password must be not empty!");
        } else if (!confirmPassword.equals(newPassword)) {
            showErrorAlert("Password and confirm password do not match!");
        } else {

            UserBussiness userBussiness = new UserBussiness();
            boolean result = userBussiness.resetPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()), email);

            if (result) {
                try {
                    Alert successAlert = new Alert(AlertType.INFORMATION);
                    successAlert.setTitle("Success");
                    successAlert.setContentText("Reset Password successful! Please login again!");
            
                    successAlert.showAndWait();

                    double width = borderPane.getScene().getWidth();
                    double height = borderPane.getScene().getHeight();

                    Parent root = FXMLLoader.load(App.class.getResource("/fxml/Login.fxml"));
                    Scene scene = new Scene(root, width, height);

                    Stage stage = (Stage) borderPane.getScene().getWindow();
                    stage.setScene(scene);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showErrorAlert("Something went wrong. Couldn't reset password!");
            }
        }
    }

    public void showErrorAlert(String contentText) {
        Alert errAlert = new Alert(AlertType.ERROR);
        errAlert.setContentText(contentText);
        errAlert.show();
    }
}
