package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class TaskWrapper {
    private final TaskModel task;
    private final StringProperty name;
    private final StringProperty priority;
    private final StringProperty status;
    private final StringProperty managerName;
    private final StringProperty deadline;

    public TaskWrapper(TaskModel task) {
        this.task = task;
        this.name = new SimpleStringProperty(task.getName());
        this.priority = new SimpleStringProperty(task.getPriority());
        this.status = new SimpleStringProperty(task.getStatus());
        this.managerName = new SimpleStringProperty(task.getManagerName());
        this.deadline = new SimpleStringProperty(
            task.getDeadline() != null ? task.getDeadline().toString() : ""
        );
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty priorityProperty() {
        return priority;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty managerNameProperty() {
        return managerName;
    }

    public StringProperty deadlineProperty() {
        return deadline;
    }

    public TaskModel getTask() {
        return task;
    }
}
