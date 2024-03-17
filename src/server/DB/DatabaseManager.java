package server.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String host = "blmmiditxmpsnnbkawh7-mysql.services.clever-cloud.com";
    private static final String dbName = "blmmiditxmpsnnbkawh7";
    private static final String user = "ubweqlezlg40fcgv";
    private static final String password = "txdguhFypxpNuwZzukPh";
    private static final int port = 3306;
    private static Connection connection;

    // *Method to make database connection
    public static void connect() {
        try {
            String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the Database Successfully.");
        } catch (SQLException e) {
            System.out.println("Connection failed. Error: " + e.getMessage());
        }
    }

    // *Get the database connection
    public static Connection getConnection() {
        return connection;
    }

    // *Close the database connection
    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close connection. Error: " + e.getMessage());
        }
    }
}
