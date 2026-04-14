package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.ProjectModel;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import client_server.client.MyClient;

public class ProjectController implements Initializable {
	 private String currentUser;

	    public void setCurrentUser(String username) {
	        this.currentUser = username;
	    }

	@FXML
    private Button chatButton;

    @FXML
    private AnchorPane displayView;// where we want to display the contents of chatButton,teamButton,projectButtton,reportsButton,taskButton

    @FXML
    private Button exitButton;// for returning back to DashBoard.fxml

    @FXML
    private Button projectButtton;

    @FXML
    private Button reportsButton;

    @FXML
    private Button taskButton;

    @FXML
    private Button teamButton;

    private Map<String, ProjectModel> projects = new HashMap<>();

	private MyClient client;
  

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Remove focus from all buttons by setting it to a non-interactive element
        displayView.requestFocus();
    }


	    @FXML
	    void handleChat(ActionEvent event) {
	    	clearDisplayView();
	    }

	    @FXML
	    void handleProjectButton(ActionEvent event) {
	    	try {
	            // Load the TaskSection FXML
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProjectSection.fxml"));
	            Parent taskView = loader.load();

	            // Clear displayView and add TaskSection content
	            displayView.getChildren().clear();
	            displayView.getChildren().add(taskView);

	        } catch (IOException e) {
	            System.err.println("Error loading TaskSection.fxml: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	    @FXML
	    private void handleTaskButton(ActionEvent event) {
	        try {
	            // Load TaskSection FXML
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TaskSection.fxml"));
	            Parent taskView = loader.load();

	            // Add TaskSection content to displayView
	            displayView.getChildren().clear();
	            displayView.getChildren().add(taskView);

	        } catch (IOException e) {
	            System.err.println("Error loading TaskSection.fxml: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }



	    
	    @FXML
	    void handleReport(ActionEvent event) {
	    	clearDisplayView();
	    }

	    @FXML
	    void handleTask(ActionEvent event) {
	    	clearDisplayView();
	    }

	    @FXML
	    private void handleTeamButton(ActionEvent event) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TeamMembers.fxml"));
	            Parent teamView = loader.load();

	            displayView.getChildren().clear();
	            displayView.getChildren().add(teamView);
	        } catch (IOException e) {
	            System.err.println("Error loading TeamMembers.fxml: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    
	    @FXML
	    private void exit(ActionEvent event) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashBoard.fxml"));
	            Parent root = loader.load();

	            Stage stage = (Stage) displayView.getScene().getWindow();
	            stage.setScene(new Scene(root));
	            stage.show();
	        } catch (IOException e) {
	            System.err.println("Error loading DashBoard.fxml: " + e.getMessage());
	        }
	    }
	    
	    private void clearDisplayView() {
	        displayView.getChildren().clear();
	    }


	    public void setClient(MyClient client) {
	        this.client = client;
	    }

}
