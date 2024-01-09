package ohmyquiz.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ohmyquiz.App;
import ohmyquiz.bussinesses.UserBussiness;
import javafx.scene.Node;

public class LoginController {
    @FXML
    BorderPane borderPane;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void loginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("")) {
            showErrorAlert("Username must be not empty!");
        } else if (password.equals("")) {
            showErrorAlert("Password must be not empty!");
        } else {
            UserBussiness userBusiness = new UserBussiness();
            boolean result = userBusiness.getByUsernamePassword(username, password);

            if (result) {
                Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);
                successAlert.setContentText("Login Successfully");
                successAlert.show();
            } else {
                showErrorAlert("Username or password is incorrect!");
            }
        }
    }

    @FXML
    public void handleForgotPasswordLink(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("/fxml/Forgotpassword.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleRegisterLink(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("/fxml/Register.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void showErrorAlert(String contentText) {
        Alert errAlert = new Alert(AlertType.ERROR);
        errAlert.setContentText(contentText);
        errAlert.show();
    }
}
