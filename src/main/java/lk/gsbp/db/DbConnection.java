package lk.gsbp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection dbConnection;
    private Connection connection;

    private DbConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/GsBookPlaza",
                "root",
                "199884"
        );
    }

    public static DbConnection getInstance() {
        if(dbConnection == null) {
            try {
                dbConnection = new DbConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}