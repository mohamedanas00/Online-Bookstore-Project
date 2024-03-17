package server.BSL;

import java.sql.*;

import server.DB.DatabaseManager;
import server.Models.Response;

public class AuthBSl {
    private Connection connection;

    public Response signup(String name, String username, String password) {
        try {
            this.connection = DatabaseManager.getConnection();
            String query = "INSERT INTO User (name, username, password) VALUES ('" +
                    name + "', '" + username + "', '" + password + "')";

            // Create and execute statement
            Statement statement = connection.createStatement();
            int rowsInserted = statement.executeUpdate(query);
            Response response;
            if (rowsInserted > 0) {
                response = new Response(200, "User registered successfully.");
                // System.out.println("User registered successfully.");
            } else {
                response = new Response(404, "Failed to register user.");
            }
            DatabaseManager.closeConnection();
            return response;
        } catch (Exception e) {
            return new Response(404, e.toString());
        }
    }

    public static void main(String[] args) {
        DatabaseManager.connect();
        AuthBSl authBSl = new AuthBSl();
        Response response;
        response = authBSl.signup("metwally", "msss101", "55555");
        System.out.println(response.getStatus() + " " + response.getMessage());
    }
}
