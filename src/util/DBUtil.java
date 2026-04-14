package util;

import java.sql.*;

public class DBUtil {
	private static final String URL =
		    "jdbc:mysql://localhost:3306/ctms"
		  + "?useSSL=false"
		  + "&allowPublicKeyRetrieval=true"
		  + "&serverTimezone=UTC";

	private static final String USER = "ctms_user";
    private static final String PWD  = "s3cr3t";

    private static Connection conn;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PWD);
            conn.setAutoCommit(false);
        } catch (Exception e) {
            throw new RuntimeException("DB init failed", e);
        }
    }

    public static Connection getConnection() {
        return conn;
    }
}
