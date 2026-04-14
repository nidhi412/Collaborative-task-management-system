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
import client_server.client.MyClient;       // your client class
import controllers.DashBoardController;     // dashboard controller
import controllers.Main;                    // to grab the shared client
import client_server.client.MyClient;

import java.io.IOException;
import java.sql.SQLException;

public class LogIn {

    @FXML private TextField    username;
    @FXML private PasswordField password;
    @FXML private Label         wrongLogin;
    @FXML private Button        login;
    @FXML private Button        signUp;
    
    
    private MyClient client;

    /** Called by DashBoardController on logout to re‑inject the client */
    public void setClient(MyClient client) {
        this.client = client;
    }

    /** Pressing Enter in the password field attempts login */
    @FXML
    void logInOnEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            userLogIn(new ActionEvent(login, null));
        }
    }

    /** Moves focus from username → password when Enter is hit in username */
    @FXML
    void moveToPassword(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            password.requestFocus();
        }
    }

    /** Called when “Login” button is clicked */
    @FXML
    void userLogIn(ActionEvent event) {
        String user = username.getText().trim();
        String pass = password.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            wrongLogin.setText("Please enter username & password.");
            return;
        }

        try {
            UserDAO dao = new UserDAO();
            User   u   = dao.findByUsername(user);

            if (u != null && u.getPassword().equals(pass)) {
                wrongLogin.setText("");

                // 1) Load the dashboard FXML
                FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/DashBoard.fxml"));
                Parent dashRoot = loader.load();

                // 2) Initialize its controller
                DashBoardController dc = loader.getController();
                dc.setCurrentUser(user);
                MyClient client = Main.getClient();  // from your Main.java
                dc.setClient(client);

                // 3) Swap the scene
                Stage stage = (Stage)((Node)event.getSource())
                                    .getScene().getWindow();
                stage.setScene(new Scene(dashRoot));
                stage.setTitle("Dashboard");

            } else {
                wrongLogin.setText("Invalid username or password.");
            }

        } catch (SQLException e) {
            wrongLogin.setText("DB error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            wrongLogin.setText("Navigation error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Called when “Sign Up” button is clicked */
    @FXML
    void userSignUp(ActionEvent event) {
        try {
            Parent signUpRoot = FXMLLoader.load(
                getClass().getResource("/SignUp.fxml"));
            Stage stage = (Stage)((Node)event.getSource())
                .getScene().getWindow();
            stage.setScene(new Scene(signUpRoot));
            stage.setTitle("Sign Up");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
