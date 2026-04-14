package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import models.ProjectModel;
import models.TaskModel;
import models.TaskWrapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectSectionController {

    @FXML
    private javafx.scene.layout.AnchorPane AnchorPane;

    @FXML
    private ListView<ProjectModel> viewProjectList;

    @FXML
    private TableView<TaskWrapper> viewTaskList;

    @FXML
    private TableColumn<TaskWrapper, String> taskNameColumn;

    @FXML
    private TableColumn<TaskWrapper, String> priorityColumn;

    @FXML
    private TableColumn<TaskWrapper, String> statusColumn;

    @FXML
    private TableColumn<TaskWrapper, String> managerNameColumn;

    @FXML
    private TableColumn<TaskWrapper, String> deadlineColumn;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private Pane dialogPaneParent;

    @FXML
    private TextField taskTitleField;

    @FXML
    private MenuButton priorityMenu;

    @FXML
    private MenuButton statusMenu;

    @FXML
    private TextField managerNameField;

    @FXML
    private DatePicker deadline;

    @FXML
    private TextField projectNameTextfield;

    private final ObservableList<ProjectModel> projects = FXCollections.observableArrayList();
    private final ObservableList<TaskWrapper> taskWrappers = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Load projects from the server
        loadProjectsFromServer();

        // Setup ListView for projects
        viewProjectList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(ProjectModel project, boolean empty) {
                super.updateItem(project, empty);
                setText(empty || project == null || project.getName() == null ? null : project.getName());
            }
        });

        // Handle project selection to load tasks
        viewProjectList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadTasksForProjectView(newValue);
            }
        });

        // Configure Task TableView columns
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        managerNameColumn.setCellValueFactory(new PropertyValueFactory<>("managerName"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));

        // Populate Priority and Status MenuButtons
        populateMenuButton(priorityMenu, List.of("Low", "Medium", "High"));
        populateMenuButton(statusMenu, List.of("To Do", "In Progress", "Done"));
    }

    private void populateMenuButton(MenuButton menuButton, List<String> options) {
        menuButton.getItems().clear(); // Clear any existing items
        for (String option : options) {
            MenuItem menuItem = new MenuItem(option);
            menuItem.setOnAction(event -> menuButton.setText(option));
            menuButton.getItems().add(menuItem);
        }
    }

    @FXML
    private void addProject(ActionEvent event) {
        String projectName = projectNameTextfield.getText().trim();
        if (projectName.isEmpty()) {
            showAlert("Error", "Project name cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        // Send project to the server
        Main.getClient().addProject(projectName);

        // Add project locally and update the ListView
        ProjectModel newProject = new ProjectModel(projectName);
        projects.add(newProject);
        viewProjectList.setItems(projects);

        projectNameTextfield.clear();
        showAlert("Success", "Project added successfully and saved to the server.", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void addTask(ActionEvent event) {
        dialogPane.setVisible(true);
        dialogPaneParent.setVisible(true);
        AnchorPane.setVisible(true);
        clearTaskDialogFields();
    }

    @FXML
    private void saveTask(ActionEvent event) {
        ProjectModel selectedProject = viewProjectList.getSelectionModel().getSelectedItem();
        if (selectedProject == null) {
            showAlert("Error", "Please select a project.", Alert.AlertType.ERROR);
            return;
        }

        String taskName = taskTitleField.getText().trim();
        String priority = priorityMenu.getText();
        String status = statusMenu.getText();
        String managerName = managerNameField.getText().trim();
        LocalDate deadlineDate = deadline.getValue();

        if (taskName.isEmpty() || "Priority".equals(priority) || "Status".equals(status) || managerName.isEmpty() || deadlineDate == null) {
            showAlert("Error", "All fields must be completed.", Alert.AlertType.ERROR);
            return;
        }

        // Send task to the server
        String taskData = String.join(";", taskName, priority, status, managerName, deadlineDate.toString());
        Main.getClient().addTask(selectedProject.getName(), taskData);

        // Update TableView with the new task
        TaskWrapper newTask = new TaskWrapper(new TaskModel(taskName, deadlineDate, status, priority, managerName));
        taskWrappers.add(newTask);
        viewTaskList.setItems(taskWrappers);

        clearTaskDialogFields();
        dialogPane.setVisible(false);
        dialogPaneParent.setVisible(false);
        AnchorPane.setVisible(false);

        showAlert("Success", "Task added successfully and saved to the server.", Alert.AlertType.INFORMATION);
    }

    private void loadProjectsFromServer() {
        Platform.runLater(() -> {
            Main.getClient().getProjects();
            String serverResponse = (String) Main.getClient().receiveMessage();
            if (serverResponse != null && serverResponse.startsWith("Projects:")) {
                String[] projectNames = serverResponse.substring(9).split(",");
                projects.clear();
                for (String projectName : projectNames) {
                    projects.add(new ProjectModel(projectName));
                }
                viewProjectList.setItems(projects);
            }
        });
    }

    private void loadTasksForProjectView(ProjectModel project) {
        Main.getClient().getTasks(project.getName());
        String serverResponse = (String) Main.getClient().receiveMessage();
        if (serverResponse != null && serverResponse.startsWith("Tasks:")) {
            String[] taskDetails = serverResponse.substring(6).split(",");
            taskWrappers.clear();
            for (String taskDetail : taskDetails) {
                String[] fields = taskDetail.split(";");
                if (fields.length == 5) {
                    taskWrappers.add(new TaskWrapper(new TaskModel(fields[0], LocalDate.parse(fields[1]), fields[2], fields[3], fields[4])));
                }
            }
            viewTaskList.setItems(taskWrappers);
        }
    }

    @FXML
    private void cancelTask(ActionEvent event) {
        clearTaskDialogFields();
        dialogPane.setVisible(false);
        dialogPaneParent.setVisible(false);
        AnchorPane.setVisible(false);

        showAlert("Info", "Task creation canceled.", Alert.AlertType.INFORMATION);
    }

    private void clearTaskDialogFields() {
        taskTitleField.clear();
        priorityMenu.setText("Priority");
        statusMenu.setText("Status");
        managerNameField.clear();
        deadline.setValue(null);
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
