package client_server.client;

import javafx.application.Platform;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MyClient extends AbstractClient {

    private final BlockingQueue<Object> messageQueue = new LinkedBlockingQueue<>();

    public MyClient(String host, int port) {
        super(host, port);
    }

    @Override
    protected void connectionEstablished() {
        System.out.println("Connection established successfully.");
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        System.out.println("Message received from server: " + msg);
        try {
            messageQueue.put(msg); // Queue the received message
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error queuing message from server: " + e.getMessage());
        }
    }


    /**
     * Retrieves the latest message from the server.
     * Blocks until a message is available.
     */
    public Object receiveMessage() {
        try {
            return messageQueue.take(); // Retrieve and remove the next message
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error receiving message: " + e.getMessage());
            return null;
        }
    }

    /**
     * Processes server messages on the JavaFX thread if needed.
     */
    private void processServerMessage(String message) {
        if (message.startsWith("Projects:")) {
            String[] projects = message.substring(9).split(",");
            // Example: Update a global list or observable collection
            System.out.println("Projects received: " + String.join(", ", projects));
        } else if (message.startsWith("Tasks:")) {
            String[] tasks = message.substring(6).split(",");
            // Example: Update a global list or observable collection
            System.out.println("Tasks received: " + String.join(", ", tasks));
        } else {
            System.out.println("Server: " + message);
        }
    }
    
    

    // Sends a new task to the server
    public void addTask(String project, String task) {
        try {
            sendToServer("ADD_TASK;" + project + ";" + task);
        } catch (Exception e) {
            System.err.println("Error sending task to server: " + e.getMessage());
        }
    }

    // Requests all tasks for a specific project from the server
    public void getTasks(String project) {
        if (project == null || project.isEmpty()) {
            System.err.println("Invalid project name.");
            return;
        }
        try {
            sendToServer("GET_TASKS;" + project);
        } catch (Exception e) {
            System.err.println("Error requesting tasks: " + e.getMessage());
        }
    }



    // Sends a new project to the server
    public void addProject(String project) {
        try {
            sendToServer("ADD_PROJECT;" + project);
        } catch (Exception e) {
            System.err.println("Error adding project to server: " + e.getMessage());
        }
    }

    // Requests all projects from the server
    public void getProjects() {
        try {
            sendToServer("GET_PROJECTS;");
        } catch (Exception e) {
            System.err.println("Error retrieving projects: " + e.getMessage());
        }
    }
}
