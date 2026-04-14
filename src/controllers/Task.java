package controllers;

import java.io.Serializable;

public class Task implements Serializable {
    private String name;
    private String priority;
    private String status;
    private String assignee;

    public Task(String name, String priority, String status, String assignee) {
        this.name = name;
        this.priority = priority;
        this.status = status;
        this.assignee = assignee;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public String getAssignee() {
        return assignee;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    @Override
    public String toString() {
        return String.format("%s (Priority: %s, Status: %s, Assigned to: %s)", name, priority, status, assignee);
    }
}