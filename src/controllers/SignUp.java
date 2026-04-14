package controllers;

import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.User;
import util.DBUtil;

import java.io.IOException;
import java.sql.SQLException;

public class SignUp {

    @FXML private TextField    username;
    @FXML private PasswordField password;
    @FXML private Label         wrongSignUp;
    @FXML private Button        signUp;

    /** Pressing Enter in password field submits sign‑up */
    @FXML
    void logInOnEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            userSignUp(new ActionEvent(signUp, null));
        }
    }

    /** Moves focus from username → password on Enter */
    @FXML
    void moveToPassword(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            password.requestFocus();
        }
    }

    /** Called when “Sign Up” button is clicked */
    @FXML
    void userSignUp(ActionEvent event) {
        String user = username.getText().trim();
        String pass = password.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            wrongSignUp.setText("Username & password required.");
            return;
        }

        try {
            UserDAO dao = new UserDAO();
            if (dao.findByUsername(user) != null) {
                wrongSignUp.setText("That username is already taken.");
                return;
            }

            // insert new user (fullName left blank for now)
            User newUser = new User(user, pass, "");
            dao.insert(newUser);
            DBUtil.getConnection().commit();

            // back to Login screen
            Parent loginRoot = FXMLLoader.load(
                getClass().getResource("/login.fxml"));
            Stage stage = (Stage)((Node)event.getSource())
                .getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Login");

        } catch (SQLException | IOException e) {
            wrongSignUp.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
