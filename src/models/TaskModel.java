package models;

import java.io.Serializable;
import java.time.LocalDate;

public class TaskModel implements Serializable {
    private String name;
    private String priority;
    private String status;
    private String assignee;
    private String managerName;
    private LocalDate deadline;

    // Existing Constructor
    public TaskModel(String name, LocalDate deadline, String status, String priority, String managerName) {
        this.name = name;
        this.priority = priority;
        this.status = status;
        this.managerName = managerName;
        this.deadline = deadline;
    }

    // New Constructor for name-only initialization
    public TaskModel(String name) {
        this.name = name;
        this.priority = "Medium"; // Default priority
        this.status = "To Do";   // Default status
        this.deadline = LocalDate.now().plusDays(7); // Default deadline
        this.managerName = "Unassigned"; // Default manager
    }

    // Getters and setters...

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    @Override
    public String toString() {
        return name + " [" + status + "]";
    }
}
