package ohmyquiz.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ohmyquiz.App;
import ohmyquiz.bussinesses.UserBussiness;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.*;

public class ForgotPasswordController {
    @FXML
    private BorderPane borderPane;

    @FXML
    private Button backToLoginButton;

    @FXML
    private TextField emailField;

    @FXML
    private void initialize() {
        Platform.runLater(() -> emailField.requestFocus());
    }

    @FXML
    private void backToLogin(ActionEvent event) {
        try {
            double width = borderPane.getScene().getWidth();
            double height = borderPane.getScene().getHeight();

            Parent root = FXMLLoader.load(App.class.getResource("/fxml/Login.fxml"));
            Scene scene = new Scene(root, width, height);

            Stage stage = (Stage) borderPane.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void nextToVerificationUI(ActionEvent event) {
        String email = emailField.getText();

        if (email.equals("")) {
            showErrorAlert("Please enter your email address");
        } else {
            UserBussiness userBussiness = new UserBussiness();
            Boolean checkEmail = userBussiness.checkEmail(email);

            if (checkEmail == true) {
                
                boolean isEmailSent = sendEmail(email, userBussiness);
                
                if (isEmailSent == true) {
                    try {

                        double width = borderPane.getScene().getWidth();
                        double height = borderPane.getScene().getHeight();

                        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/VerificationCode.fxml"));
                        Parent root = loader.load();

                        VerificationCodeController verificationController = loader.getController();
                        verificationController.setEmail(email);

                        Scene scene = new Scene(root, width, height);
                        Stage stage = (Stage) borderPane.getScene().getWindow();
                        stage.setScene(scene);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                showErrorAlert("No email found! Please enter your email address again!");
            }
        }
    }
    // }

    public boolean sendEmail(String email, UserBussiness userBussiness) {
        // create token
        SecureRandom random = new SecureRandom();
        StringBuilder tokenBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            tokenBuilder.append(random.nextInt(10));
        }
        String token = tokenBuilder.toString();

        // create expiration Timetoi
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plusMinutes(30);

        // update token and expirationTime in database
        userBussiness.setVerificationCodeAndExpirationTime(email, token, expirationTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'th\u00E1ng' MM, yyyy HH:mm:ss 'UTC'");
        String formattedTime = expirationTime.format(formatter);

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String username = "nvy1621@gmail.com";
        String password = "ocinttzxalhymith";

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }

        });

        boolean isEmailSent = false;
        try {
            Message message = new MimeMessage(session);
            try {
                message.setFrom(new InternetAddress(username, "Oh My Quiz"));
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Verification Codes");
            String htmlContent = "<p>Vui l\u00F2ng nh\u1EADp m\u00E3 sau \u0111\u1EC3 ho\u00E0n t\u1EA5t qu\u00E1 tr\u00ECnh \u0111\u0103ng nh\u1EADp:</p><div style=\"background:#faf9fa;border:1px solid #dad8de;text-align:center;padding:5px;margin:0 0 5px 0;font-size:24px;line-height:1.5;width:80%\">"
                    +
                    token +
                    "</div>" +
                    "<div style=\"text-align:center;padding:0 0 20px 0;font-size:12px;font-style:italic;font-weight:bold;line-height:1.5;width:80%\">Xin l\u01B0u \u00FD r\u1EB1ng m\u00E3 n\u00E0y s\u1EBD h\u1EBFt h\u1EA1n v\u00E0o "
                    +
                    formattedTime +
                    ".</div>";

            message.setContent(htmlContent, "text/html;charset=UTF-8");

            try {
                Transport.send(message);
                isEmailSent = true;

            } catch (MessagingException e) {

                e.printStackTrace();
            }
        } catch (MessagingException e) {
            showErrorAlert("Can not send verification code. Please try again!");
        }
        return isEmailSent;
    }

    public void showErrorAlert(String contentText) {
        Alert errAlert = new Alert(AlertType.ERROR);
        errAlert.setContentText(contentText);
        errAlert.show();
    }
}
