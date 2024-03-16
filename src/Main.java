import java.sql.Connection;

import DB.DatabaseManager;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.connect();
        //  Connection connection = DatabaseManager.getConnection();
    }
}
