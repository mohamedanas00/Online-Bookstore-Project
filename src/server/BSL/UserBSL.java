package server.BSL;

import java.sql.*;

import Models.GlobalResponse;
import server.DB.DatabaseManager;
import server.Utils.Authorization;

public class UserBSL {
    private Connection connection;
    private Statement statement;

    public GlobalResponse buyBook(int userId, int bookId) {
        try {
            connection = DatabaseManager.getConnection();
            GlobalResponse response;
            if (!Authorization.checkAuthorization(userId, "user", connection)) {
                return new GlobalResponse(401, "Unauthorized");
            }

            // *Check the quantity of the book
            String quantityQuery = "SELECT quantity FROM Book WHERE id = " + bookId;
            statement = connection.createStatement();
            ResultSet quantityResult = statement.executeQuery(quantityQuery);
            if (quantityResult.next()) {
                int quantity = quantityResult.getInt("quantity");
                if (quantity > 0) {
                    // *Decrement the quantity of the book
                    String updateQuery = "UPDATE Book SET quantity = quantity - 1 WHERE id = " + bookId;
                    int rowsUpdated = statement.executeUpdate(updateQuery);
                    if (rowsUpdated > 0) {
                        // *Insert into BookOwnership table
                        String ownershipQuery = "INSERT INTO BookOwnership (user_id, book_id) VALUES ('" +
                                userId + "', '" + bookId + "')";
                        int rowsInserted = statement.executeUpdate(ownershipQuery);
                        if (rowsInserted > 0) {
                            response = new GlobalResponse(200, "Book bought successfully.");
                        } else {
                            response = new GlobalResponse(404, "Failed to insert into BookOwnership.");
                        }
                    } else {
                        response = new GlobalResponse(404, "Failed to update book quantity.");
                    }
                } else {
                    response = new GlobalResponse(404, "Book is out of stock.");
                }
            } else {
                response = new GlobalResponse(404, "Book not found.");
            }
            statement.close();
            return response;
        } catch (Exception e) {
            return new GlobalResponse(500, e.toString());
        }
    }
    
    // public static void main(String[] args) {
    //     DatabaseManager.connect();
    //     UserBSL userBSL =new UserBSL();
    //     userBSL.buyBook(9, 5);

    // }

}
