package server.BSL;

import java.sql.*;

import Models.GlobalResponse;
import Models.LogInResponse;
import Models.User;
import server.DB.DatabaseManager;
import server.Utils.Hashing;

public class AuthBSl {
    private Connection connection;

    public GlobalResponse signup(String name, String username, String password) {
        try {
            connection = DatabaseManager.getConnection();
            password = Hashing.doHashing(password);
            String query = "INSERT INTO User (name, username, password) VALUES ('" +
                    name + "', '" + username + "', '" + password + "')";

            Statement statement = connection.createStatement();
            int rowsInserted = statement.executeUpdate(query);
            GlobalResponse response;
            if (rowsInserted > 0) {
                response = new GlobalResponse(200, "User registered successfully.");
            } else {
                response = new GlobalResponse(404, "Failed to register user.");
            }
            statement.close();
            return response;
        } catch (Exception e) {
            return new GlobalResponse(404, e.toString());
        }
    }

    public GlobalResponse login(String username, String password) {
        try {
            connection = DatabaseManager.getConnection();
            String query = "SELECT *  FROM User WHERE username = '" + username + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            GlobalResponse response;
            if (resultSet.next()) {
                String hashPassword = resultSet.getString("password");
                if (!Hashing.comparePassword(password, hashPassword)) {
                    response = new GlobalResponse(401, "Unauthorized");
                } else {
                    User user = new User(resultSet);
                    response = new LogInResponse(200, "Welcome Back", user);
                }
            } else {
                response = new GlobalResponse(404, "User Not Found");
            }
            resultSet.close();
            statement.close();
            return response;
        } catch (Exception e) {
            return new GlobalResponse(500, e.toString());
        }
    }

}
