package dao;

import models.Project;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    /**
     * Inserts a new project into the database.
     * Looks up the owner’s user_id by username.
     */
    public void insert(Project project) throws SQLException {
        Connection conn = DBUtil.getConnection();

        // 1) Find the owner’s user ID
        String findUserSql = "SELECT id FROM Users WHERE username = ?";
        int ownerId;
        try (PreparedStatement ps = conn.prepareStatement(findUserSql)) {
            ps.setString(1, project.getOwner());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("User not found: " + project.getOwner());
                }
                ownerId = rs.getInt("id");
            }
        }

        // 2) Insert the project
        String insertSql = "INSERT INTO Projects(name, owner_id) VALUES(?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
            ps.setString(1, project.getName());
            ps.setInt(2, ownerId);
            ps.executeUpdate();
        }
    }

    /**
     * Retrieves all projects owned by the given username.
     */
    public List<Project> getByOwner(String ownerUsername) throws SQLException {
        Connection conn = DBUtil.getConnection();
        String sql =
            "SELECT p.id, p.name, u.username " +
            "FROM Projects p " +
            "JOIN Users u ON p.owner_id = u.id " +
            "WHERE u.username = ?";
        List<Project> projects = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ownerUsername);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    projects.add(new Project(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username")
                    ));
                }
            }
        }
        return projects;
    }

    // (Optional) add update(Project), delete(int projectId), findById(int) as needed
}
