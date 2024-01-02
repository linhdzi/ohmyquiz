package ohmyquiz.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ohmyquiz.bussinesses.UserBussiness;
import ohmyquiz.dataAccesses.Connection;
import ohmyquiz.models.User;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import org.mindrot.jbcrypt.BCrypt;

import com.mongodb.client.model.Filters;

public class RegisterController implements Initializable {
    @FXML
    BorderPane borderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private void signUpButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String email = emailField.getText();

        if (username.equals("")) {
            showErrorAlert("Username must be not empty!");
        } else if (password.equals("")) {
            showErrorAlert("Password must be not empty!");
        } else if (confirmPassword.equals("")) {
            showErrorAlert("Confirm Password must be not empty!");
        } else if (email.equals("")) {
            showErrorAlert("Email must be not empty!");
        } else if (!confirmPassword.equals(password)) {
            showErrorAlert("Password and confirm password do not match!");
        } else {

            var collection = Connection.collection("User");
            long usernameCount = collection.countDocuments(Filters.eq("name", username));
            long emailCount = collection.countDocuments(Filters.eq("email", email));

            if (usernameCount > 0) {
                showErrorAlert("Username is existed, please try again!");
            } else if (emailCount > 0) {
                showErrorAlert("Email is existed, please try again!");
            } else {
                User user = new User();
                user.setGuid(UUID.randomUUID().toString());
                user.setName(username);
                user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
                user.setEmail(email);
                user.setRole("learner");
                
                UserBussiness userBusiness = new UserBussiness();
                boolean result = userBusiness.createUser(user);

                if (result) {
                    Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    successAlert.setContentText("Register Successfully");
                    successAlert.show();
                } else {
                    showErrorAlert("something went wrong!");
                }
            }
        }
    }

    public void showErrorAlert(String contentText) {
        Alert errAlert = new Alert(AlertType.ERROR);
        errAlert.setContentText(contentText);
        errAlert.show();
    }
}
