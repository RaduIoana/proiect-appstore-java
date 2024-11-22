package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {
    private static final String ConnectionStr = "jdbc:mysql://localhost:3306/paoDB";
    private static final String User = "root";
    private static final String Password = "paopa55";

    private static Connection dbConnection;

    private DBConfig() { }

    public static Connection getDatabaseConnection() {
        try {
            if (dbConnection == null || dbConnection.isClosed()) {
                dbConnection = DriverManager.getConnection(ConnectionStr, User, Password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbConnection;
    }

    public static void closeDatabaseConnection() {
        try {
            if (dbConnection != null && !dbConnection.isClosed()) {
                dbConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
