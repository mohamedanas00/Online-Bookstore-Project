package server.BSL;

import java.sql.*;

import server.DB.DatabaseManager;
import server.Models.GlobalResponse;
import server.Models.SignInResponse;
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
            return response;
        } catch (Exception e) {
            return new GlobalResponse(404, e.toString());
        }
    }

    public GlobalResponse login(String username, String password) {
        try {
            connection = DatabaseManager.getConnection();
            String query = "SELECT password , role  FROM User WHERE username = '" + username + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            GlobalResponse response;
            if (resultSet.next()) {
                String hashPassword = resultSet.getString("password");
                String role = resultSet.getString("role");
                if (!Hashing.comparePassword(password, hashPassword)) {
                    response = new GlobalResponse(401, "Unauthorized");
                } else {
                    response = new SignInResponse(200, "Welcome Back", role);
                }
            } else {
                response = new GlobalResponse(404, "User Not Found");
            }

            return response;
        } catch (Exception e) {
            return new GlobalResponse(500, e.toString());
        }
    }

    // public static void main(String[] args) {
    //     DatabaseManager.connect();
    //     AuthBSl authBSl = new AuthBSl();
    //     // authBSl.signup("anas", "anas1001", "anos2002");
    //     GlobalResponse res = authBSl.login("anas1001", "anos2002");
    //     // Check if the response is an instance of SignInResponse
    //     if (res instanceof SignInResponse) {
    //         SignInResponse signInResponse = (SignInResponse) res;
    //         System.out.println(res.getStatus() + " " + res.getMessage() + " " + signInResponse.getRole());
    //     } else {
    //         System.out.println(res.getStatus() + " " + res.getMessage());
    //     }
    // }

}
