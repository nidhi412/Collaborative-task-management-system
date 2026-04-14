package client_server.server;

import client_server.server.AbstractServer;
import client_server.server.ConnectionToClient;

import dao.UserDAO;
import dao.ProjectDAO;
import dao.TaskDAO;
import models.User;
import models.Project;
import models.Task;
import util.DBUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CTMSServer extends AbstractServer {

    public CTMSServer(int port) {
        super(port);
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        String message = (String) msg;
        String[] parts  = message.split(":", 2);
        String command  = parts[0];
        String data     = parts.length > 1 ? parts[1] : "";

        switch (command) {
            case "LOGIN":
                handleLogin(data, client);
                break;
            case "ADD_PROJECT":
                // data format: owner|projectName
                String[] proj = data.split("\\|", 2);
                handleAddProject(proj[0], proj[1], client);
                break;
            case "GET_PROJECTS":
                // data is owner
                handleGetProjects(data, client);
                break;
            case "ADD_TASK":
                // data format: projectName|title|description|dueDate|status|assignee
                handleAddTask(data, client);
                break;
            case "GET_TASKS":
                // data is projectName
                handleGetTasks(data, client);
                break;
            default:
                sendError(client, "Unknown command: " + command);
        }
    }

    private void handleLogin(String credentials, ConnectionToClient client) {
        String[] creds   = credentials.split("\\|", 2);
        String username  = creds[0];
        String password  = creds[1];

        try {
            UserDAO userDao = new UserDAO();
            User usr        = userDao.findByUsername(username);

            if (usr != null && usr.getPassword().equals(password)) {
            	client.sendToClient("LOGIN_SUCCESS");
                System.out.println("User '" + username + "' logged in successfully.");
            } else {
            	client.sendToClient("LOGIN_FAILURE");
                System.out.println("Failed login attempt for username: " + username);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            sendError(client, "LOGIN_ERROR: " + e.getMessage());
        }
    }

    private void handleAddProject(String owner, String projectName, ConnectionToClient client) {
        if (projectName == null || projectName.isBlank()) {
            sendError(client, "Project name cannot be empty.");
            return;
        }
        try {
            ProjectDAO projectDao = new ProjectDAO();
            Project project       = new Project(projectName, owner);
            projectDao.insert(project);
            DBUtil.getConnection().commit();

            try {
				client.sendToClient("PROJECT_ADDED:" + projectName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println("Project added: " + projectName);
        } catch (SQLException e) {
            e.printStackTrace();
            sendError(client, "ADD_PROJECT_ERROR: " + e.getMessage());
        }
    }

    private void handleGetProjects(String owner, ConnectionToClient client) {
        try {
            ProjectDAO projectDao = new ProjectDAO();
            List<Project> projects = projectDao.getByOwner(owner);
            String joined = projects.stream()
                                     .map(Project::getName)
                                     .collect(Collectors.joining(","));

            try {
				client.sendToClient("PROJECTS_LIST:" + joined);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println("Projects sent to client: " + joined);
        } catch (SQLException e) {
            e.printStackTrace();
            sendError(client, "GET_PROJECTS_ERROR: " + e.getMessage());
        }
    }

    private void handleAddTask(String taskData, ConnectionToClient client) {
        // parse: projectName|title|description|dueDate|status|assignee
        String[] parts       = taskData.split("\\|", 6);
        String projectName  = parts[0];
        String title        = parts[1];
        String description  = parts[2];
        String dueDate      = parts[3];
        String status       = parts[4];
        String assignee     = parts[5];

        try {
            TaskDAO taskDao = new TaskDAO();
            Task task       = new Task(projectName, title, description, dueDate, status, assignee);
            taskDao.insert(task);
            DBUtil.getConnection().commit();

            try {
				client.sendToClient("TASK_ADDED:" + title);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println("Task added to '" + projectName + "': " + title);
        } catch (SQLException e) {
            e.printStackTrace();
            sendError(client, "ADD_TASK_ERROR: " + e.getMessage());
        }
    }

    private void handleGetTasks(String projectName, ConnectionToClient client) {
        try {
            TaskDAO taskDao = new TaskDAO();
            List<Task> tasks = taskDao.findByProject(projectName);
            String joined = tasks.stream()
                                 .map(Task::getTitle)
                                 .collect(Collectors.joining(","));

            try {
				client.sendToClient("TASKS_LIST:" + joined);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println("Tasks sent for project '" + projectName + "': " + joined);
        } catch (SQLException e) {
            e.printStackTrace();
            sendError(client, "GET_TASKS_ERROR: " + e.getMessage());
        }
    }

    private void sendError(ConnectionToClient client, String message) {
    	try {
			client.sendToClient("ERROR:" + message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
