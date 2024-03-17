package server.BSL;

import java.sql.*;

import server.DB.DatabaseManager;
import server.Models.Response;
import server.Utils.Hashing;

public class AuthBSl {
    private Connection connection;

    public Response signup(String name, String username, String password) {
        try {
            connection = DatabaseManager.getConnection();
            password = Hashing.doHashing(password);
            String query = "INSERT INTO User (name, username, password) VALUES ('" +
                    name + "', '" + username + "', '" + password + "')";

            Statement statement = connection.createStatement();
            int rowsInserted = statement.executeUpdate(query);
            Response response;
            if (rowsInserted > 0) {
                response = new Response(200, "User registered successfully.");
            } else {
                response = new Response(404, "Failed to register user.");
            }
            DatabaseManager.closeConnection();
            return response;
        } catch (Exception e) {
            return new Response(404, e.toString());
        }
    }

    public Response login(String username, String password) {
        try {
            connection = DatabaseManager.getConnection();
            String query = "SELECT password FROM User WHERE username = '" + username + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            Response response;
            if (resultSet.next()) {
                String hashPassword = resultSet.getString("password");
                if (!Hashing.comparePassword(password, hashPassword)) {
                    response = new Response(401, "Unauthorized");
                } else {
                    response = new Response(200, "Welcome Back");
                }
            } else {
                response = new Response(404, "User Not Found");
            }

            return response;
        } catch (Exception e) {
            return new Response(500, e.toString());
        }
    }

}
