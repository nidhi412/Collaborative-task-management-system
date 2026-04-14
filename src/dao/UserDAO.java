package dao;

import models.User;
import util.DBUtil;

import java.sql.*;
import java.util.*;

public class UserDAO {
    public User findByUsername(String username) throws SQLException {
        Connection conn = DBUtil.getConnection();
        String sql = "SELECT id, username, password, full_name FROM Users WHERE username=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new User(
                  rs.getInt("id"),
                  rs.getString("username"),
                  rs.getString("password"),
                  rs.getString("full_name")
                );
            }
        }
    }

    public List<User> getAll() throws SQLException {
        Connection conn = DBUtil.getConnection();
        List<User> out = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT id,username,password,full_name FROM Users")) {
            while (rs.next()) {
                out.add(new User(
                  rs.getInt("id"),
                  rs.getString("username"),
                  rs.getString("password"),
                  rs.getString("full_name")
                ));
            }
        }
        return out;
    }

    public void insert(User u) throws SQLException {
        Connection conn = DBUtil.getConnection();
        String sql = "INSERT INTO Users(username,password,full_name) VALUES(?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFullName());
            ps.executeUpdate();
        }
    }
}
