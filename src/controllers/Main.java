package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import client_server.client.MyClient;

public class Main extends Application {
    private static MyClient client;

    @Override
    public void start(Stage primaryStage) throws Exception {
        client = new MyClient("10.0.0.218", 50004);
        try {
            client.openConnection();
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource("/LogIn.fxml"));
        primaryStage.setTitle("Task Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        client.closeConnection();
    }

    public static MyClient getClient() {
        return client;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
