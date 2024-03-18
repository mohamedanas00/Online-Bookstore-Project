package server.BSL;

import java.sql.*;

import Models.Book;
import Models.GlobalResponse;
import server.DB.DatabaseManager;
import server.Utils.Authorization;

public class BookBSl {
    private Connection connection;

    public GlobalResponse addBook(int userId, Book book) {
        try {
            connection = DatabaseManager.getConnection();
            GlobalResponse response;
            if (!Authorization.checkAuthorization(userId,"admin" ,connection)) {
                return new GlobalResponse(401, "Unauthorized");
            }
            String query = "INSERT INTO Book (title, author, genre, price, quantity) VALUES ('" +
                    book.getTitle() + "', '" + book.getAuthor() + "', '" + book.getGenre() + "', '"
                    + book.getPrice() + "', '" + book.getQuantity() + "')";

            Statement statement = connection.createStatement();
            int rowsInserted = statement.executeUpdate(query);
            if (rowsInserted > 0) {
                response = new GlobalResponse(200, "New Book Adding successfully.");
            } else {
                response = new GlobalResponse(404, "Failed to ADD Book.");
            }
            statement.close();
            return response;
        } catch (Exception e) {
            return new GlobalResponse(500, e.toString());
        }
    }

    // public static void main(String[] args) {
    // DatabaseManager.connect();
    // Book book =new Book("Future", "Mark", "Drama", 99.4, 10);
    // BookBSl bookBSl =new BookBSl();
    // GlobalResponse res =bookBSl.addBook(10, book);
    // System.out.println(res);
    // }

}
