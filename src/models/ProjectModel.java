package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProjectModel implements Serializable {
    private String name;
    private List<TaskModel> tasks;

    public ProjectModel(String name) {
        this.name = name;
        this.tasks = new ArrayList<>(); // Initialize tasks list
    }
    
    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public List<TaskModel> getTasks() {
        if (tasks == null) {
            tasks = new ArrayList<>(); // Initialize if null
        }
        return tasks;
    }

    public void addTask(TaskModel task) {
        getTasks().add(task); // Use getter to ensure initialization
    }

    @Override
    public String toString() {
        return name; // For display in ListView
    }

    public void setTasks(List<TaskModel> tasks) {
        this.tasks = tasks;
    }



}