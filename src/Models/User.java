package Models;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User implements Serializable{
    private int id;
    private String name;
    private String username;
    private String password;
    private String role;

    public User(ResultSet resultSet) throws SQLException{
        id = resultSet.getInt("id");
        name = resultSet.getString("name");
        username=resultSet.getString("username");
        password=resultSet.getString("password");
        role=resultSet.getString("role");

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User Details:\n");
        sb.append("  ID: ").append(id).append("\n");
        sb.append("  Name: ").append(name).append("\n");
        sb.append("  Username: ").append(username).append("\n");
        return sb.toString();
    }
    
    
}
