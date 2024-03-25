package server.BSL;
import java.sql.*;

import server.DB.DatabaseManager;
import Models.User;

public class UserBSL {
    private Connection connection;
    private Statement statement;

    public User getUserDetails(int userId) throws SQLException{
        connection =DatabaseManager.getConnection();
        String query = "SELECT * FROM User WHERE id = '" + userId + "'";
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        User user;
        if(resultSet.next()){
            user= new User(resultSet);
            user= new User(resultSet);
        }else{
            return null;
        }

        return user;   
    }
}
