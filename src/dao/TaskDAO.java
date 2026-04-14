package dao;

import models.Task;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    /**
     * Inserts a new task into the database.
     * Looks up project_id by project name, and assignee_id by username.
     */
    public void insert(Task task) throws SQLException {
        Connection conn = DBUtil.getConnection();

        // 1) Find project ID
        String findProjSql = "SELECT id FROM Projects WHERE name = ?";
        int projectId;
        try (PreparedStatement ps = conn.prepareStatement(findProjSql)) {
            ps.setString(1, task.getProjectName());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Project not found: " + task.getProjectName());
                }
                projectId = rs.getInt("id");
            }
        }

        // 2) Find assignee ID (if provided)
        Integer assigneeId = null;
        if (task.getAssignee() != null && !task.getAssignee().isBlank()) {
            String findUserSql = "SELECT id FROM Users WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(findUserSql)) {
                ps.setString(1, task.getAssignee());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        assigneeId = rs.getInt("id");
                    } else {
                        throw new SQLException("Assignee not found: " + task.getAssignee());
                    }
                }
            }
        }

        // 3) Insert the task
        String insertSql =
            "INSERT INTO Tasks(project_id, title, description, due_date, status, assignee_id) " +
            "VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
            ps.setInt(1, projectId);
            ps.setString(2, task.getTitle());
            ps.setString(3, task.getDescription());
            // dueDate stored as DATE; convert String to java.sql.Date
            if (task.getDueDate() != null && !task.getDueDate().isBlank()) {
                ps.setDate(4, Date.valueOf(task.getDueDate()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setString(5, task.getStatus());
            if (assigneeId != null) {
                ps.setInt(6, assigneeId);
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            ps.executeUpdate();
        }
    }

    /**
     * Returns all tasks for the given project name.
     */
    public List<Task> findByProject(String projectName) throws SQLException {
        Connection conn = DBUtil.getConnection();
        String sql =
            "SELECT t.id, p.name AS project_name, t.title, t.description, " +
            "       t.due_date, t.status, u.username AS assignee " +
            "FROM Tasks t " +
            "JOIN Projects p ON t.project_id = p.id " +
            "LEFT JOIN Users u ON t.assignee_id = u.id " +
            "WHERE p.name = ?";

        List<Task> tasks = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, projectName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getString("project_name"),
                        rs.getString("title"),
                        rs.getString("description"),
                        // convert DATE to String (ISO format)
                        rs.getDate("due_date") != null
                           ? rs.getDate("due_date").toString()
                           : null,
                        rs.getString("status"),
                        rs.getString("assignee")
                    ));
                }
            }
        }
        return tasks;
    }

    // (Optional) add update(Task), delete(int taskId), findById(int) as needed
}
