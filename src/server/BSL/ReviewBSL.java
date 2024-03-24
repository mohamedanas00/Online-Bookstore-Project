package server.BSL;

import java.sql.*;

import Models.GlobalResponse;
import server.DB.DatabaseManager;

public class ReviewBSL {
    private Connection connection;
    private Statement statement;
    private BookBSl bookBSl;

    public GlobalResponse addReview(int userId, int bookId, int rating, String comment) {
        GlobalResponse response;
        bookBSl = new BookBSl();
        try {
            connection = DatabaseManager.getConnection();
            statement = connection.createStatement();

            String query = "INSERT INTO Review (user_id, book_id, rating, comment) VALUES ('" +
                    userId + "', '" + bookId + "', '" + rating + "', '"
                    + comment + "')";

            int rowsInserted = statement.executeUpdate(query);
            if (rowsInserted > 0) {
                response = new GlobalResponse(200, "Review Adding successfully.");
                if (bookBSl.calculateBookRating(bookId)) {
                    return new GlobalResponse(404, "Failed to update Rating.");
                }

            } else {
                response = new GlobalResponse(404, "Failed to ADD Review.");
            }
            statement.close();
            return response;
        } catch (Exception e) {
            return new GlobalResponse(500, e.toString());
        }
    }

}
