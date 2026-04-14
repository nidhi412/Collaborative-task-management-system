package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import client_server.client.MyClient;
import models.TaskModel;
import controllers.TaskItemController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

    private String currentUser;
    private MyClient client;

    @FXML
    private Button logOutButton;

    @FXML
    private VBox vTaskItems;

    @FXML
    private TextField tfSearch;
    
    @FXML
    private VBox displayView;

    public void setCurrentUser(String username) {
        this.currentUser = username;
    }
   

    public void setClient(MyClient client) {
        this.client = client;
        if (client != null) {
            refreshTasks(); // Automatically load tasks when client is set
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("DashBoardController initialized.");
    }

    private void loadTasksFromServer(String projectName) {
        if (client == null) {
            showError("Error", "Client not connected to the server.");
            return;
        }

        try {
            client.sendToServer("GET_TASKS;" + projectName);
            Object response = client.receiveMessage();

            if (response instanceof String) {
                String message = (String) response;
                if (message.startsWith("Tasks:")) {
                    String[] taskDetails = message.substring(6).split(",");
                    Platform.runLater(() -> {
                        vTaskItems.getChildren().clear();
                        for (String taskDetail : taskDetails) {
                            try {
                                String[] fields = taskDetail.split(";");
                                if (fields.length != 3) {
                                    System.err.println("Invalid task data: " + taskDetail);
                                    continue; // Skip invalid task entries
                                }

                                String taskName = fields[0];
                                String taskStatus = fields[1];
                                String iconPath = fields[2];

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/TaskItem.fxml"));
                                Node taskNode = loader.load();

                                TaskItemController controller = loader.getController();
                                if (controller != null) {
                                    Image icon = null;
                                    if (iconPath != null && !iconPath.isEmpty()) {
                                        try {
                                            icon = new Image(getClass().getResourceAsStream(iconPath));
                                        } catch (Exception e) {
                                            System.err.println("Failed to load icon for task: " + iconPath);
                                            // Optionally assign a default icon if loading fails
                                            icon = new Image(getClass().getResourceAsStream("/default-icon.png"));
                                        }
                                    }
                                   // controller.setTaskName(new TaskModel(taskName, taskStatus, icon));
                                }
                                vTaskItems.getChildren().add(taskNode);
                            } catch (IOException e) {
                                System.err.println("Error loading task item: " + e.getMessage());
                            }
                        }
                    });

                } else {
                    showError("Server Error", "Unexpected server response: " + message);
                }
            } else {
                showError("Server Error", "Invalid server response.");
            }
        } catch (IOException e) {
            showError("Connection Error", "Failed to fetch tasks from the server.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProjectButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Project.fxml"));
            Parent root = loader.load();

            ProjectController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            controller.setClient(client);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showError("Navigation Error", "Failed to load Project view.");
            e.printStackTrace();
        }
    }

    @FXML
    private void refreshTasks() {
        String defaultProject = "Default Project"; // Replace with dynamic project retrieval
        loadTasksFromServer(defaultProject);
    }

    
    
    @FXML
    private void logOut() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            LogIn controller = loader.getController();
            controller.setClient(client);

            Stage stage = (Stage) logOutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showError("Logout Error", "Failed to navigate to login screen.");
            e.printStackTrace();
        }
    }

    private void showError(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
