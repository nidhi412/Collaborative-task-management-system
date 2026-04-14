package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.application.Platform;
import models.TaskModel;
import client_server.client.MyClient;

import java.util.ArrayList;
import java.util.List;

public class TaskController {

    @FXML
    private ListView<String> taskListView;

    private MyClient client;

    private List<TaskModel> tasks = new ArrayList<>();

    public void setClient(MyClient client) {
        this.client = client;
        if (client != null) {
            loadTasks();
        }
    }

    @FXML
    public void initialize() {
        System.out.println("TaskController initialized.");
    }

    @FXML
    private void addTask() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Task");
        dialog.setHeaderText("Enter Task Name:");
        dialog.setContentText("Task Name:");

        String taskName = dialog.showAndWait().orElse(null);

        if (taskName == null || taskName.trim().isEmpty()) {
            showAlert("Error", "Task name cannot be empty.");
            return;
        }

        try {
            client.sendToServer("ADD_TASK;" + taskName);
            Object response = client.receiveMessage();

            if ("SUCCESS".equals(response)) {
                showAlert("Success", "Task added successfully.");
                loadTasks();
            } else {
                showAlert("Error", "Unexpected response: " + response);
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to add task. " + e.getMessage());
        }
    }

    @FXML
    private void loadTasks() {
        if (client == null) {
            showAlert("Error", "Client not set.");
            return;
        }

        try {
            client.sendToServer("GET_TASKS");
            Object response = client.receiveMessage();

            if (response instanceof List<?>) {
                tasks = (List<TaskModel>) response;
                Platform.runLater(() -> taskListView.setItems(FXCollections.observableArrayList(
                        tasks.stream().map(TaskModel::getName).toList()
                )));
            } else {
                showAlert("Error", "Unexpected response from server.");
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to load tasks. " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
