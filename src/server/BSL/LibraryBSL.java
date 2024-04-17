package server.BSL;

import java.sql.*;

import Models.GlobalResponse;
import Models.LibraryStatistics;
import Models.libraryResponse;
import server.DB.DatabaseManager;
import server.Middleware.Authorization;

public class LibraryBSL {
    private Connection connection;
    private LibraryStatistics library;

    public GlobalResponse getLibraryStatistics(int userId) {

        try {
            connection = DatabaseManager.getConnection();
            if (!Authorization.checkAuthorization(userId, "admin", connection)) {
                return new GlobalResponse(401, "Unauthorized");
            }

            library = new LibraryStatistics(countBorrowedBooks(), countAvailableBooks(),
                    countRequestsByStatus("Accepted"), countRequestsByStatus("Rejected"),
                    countRequestsByStatus("Pending"));
            return new libraryResponse(200, "success", library);
        } catch (SQLException e) {
            return new GlobalResponse(404, e.toString());
        }
    }

    private int countBorrowedBooks() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT COUNT(*) AS borrowed_books FROM Request WHERE status = 'Accepted'");
        ResultSet result = statement.executeQuery();
        result.next();
        int count = result.getInt("borrowed_books");
        statement.close();
        return count;
    }

    private int countAvailableBooks() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT COUNT(*) AS available_books FROM Book WHERE quantity > 0");
        ResultSet result = statement.executeQuery();
        result.next();
        int count = result.getInt("available_books");
        statement.close();
        return count;
    }

    private int countRequestsByStatus(String status) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT COUNT(*) AS requests FROM Request WHERE status = ?");
        statement.setString(1, status);
        ResultSet result = statement.executeQuery();
        result.next();
        int count = result.getInt("requests");
        statement.close();
        return count;
    }

}
