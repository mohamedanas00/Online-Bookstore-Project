package server.Middleware;

import java.sql.*;


public class Authorization {

    static public boolean checkAuthorization(int userId, String role, Connection connection) {
        try {
            String query = "SELECT  role  FROM User WHERE id = '" + userId + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String userRole = resultSet.getString("role");
                return role.equals(userRole);
            }
            resultSet.close();
            statement.close();
            return false;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

}
