package server.BSL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Models.Book;
import Models.BookResponse;
import Models.GlobalResponse;
import Models.SignInResponse;
import server.DB.DatabaseManager;
import server.Utils.Authorization;

public class Wishlist {
    private Connection connection;
    private Statement statement;

    public GlobalResponse addToWishlist(int userId, int bookId) {
        try {
            connection = DatabaseManager.getConnection();
            GlobalResponse response;
            if (!Authorization.checkAuthorization(userId, "user", connection)) {
                return new GlobalResponse(401, "Unauthorized");
            }
            String query = "INSERT INTO Wishlist (user_id, book_id) VALUES ('" +
                    userId + "', '" + bookId + "')";

            statement = connection.createStatement();
            int rowsInserted = statement.executeUpdate(query);
            if (rowsInserted > 0) {
                response = new GlobalResponse(200, "New Book Adding To WishList successfully.");
            } else {
                response = new GlobalResponse(404, "Failed to ADD Book.");
            }
            statement.close();
            return response;
        } catch (Exception e) {
            return new GlobalResponse(500, e.toString());

        }
    }

    public GlobalResponse deleteFromWishlist(int userId, int bookId) {
        try {
            connection = DatabaseManager.getConnection();
            GlobalResponse response;
            if (!Authorization.checkAuthorization(userId, "user", connection)) {
                return new GlobalResponse(401, "Unauthorized");
            }
            String query = "DELETE FROM Wishlist WHERE user_id = " + userId + " AND book_id = " + bookId;
            statement = connection.createStatement();
            int rowsInserted = statement.executeUpdate(query);
            if (rowsInserted > 0) {
                response = new GlobalResponse(200, "Delete Book from Wishlist successfully.");
            } else {
                response = new GlobalResponse(404, "Not Found Book in your Wishlist.");
            }
            return response;
        } catch (Exception e) {
            return new GlobalResponse(500, e.toString());
        }
    }

    public GlobalResponse getMyWishlist(int userId) {
        List<Book> books = new ArrayList<>();
        try {
            connection = DatabaseManager.getConnection();

            GlobalResponse response;
            if (!Authorization.checkAuthorization(userId, "user", connection)) {
                return new GlobalResponse(401, "Unauthorized");
            }
            String query = "SELECT Book.* " +
                    "FROM Wishlist " +
                    "JOIN Book ON Wishlist.book_id = Book.id " +
                    "WHERE Wishlist.user_id = ?";
            statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                do {
                    // *Retrieve the columns from the Book table
                    int bookId = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    String genre = resultSet.getString("genre");
                    double price = resultSet.getDouble("price");
                    double ratingAvg = resultSet.getDouble("ratingAvg");
                    int quantity = resultSet.getInt("quantity");
                    // *Create a Book object and add it to the list
                    Book book = new Book(title, author, genre, price, quantity);
                    book.setId(bookId);
                    book.setRatingAvg(ratingAvg);
                    books.add(book);
                } while (resultSet.next());
                response = new BookResponse(200, "Success", books);
            } else {
                response = new GlobalResponse(404, "Wishlist is Empty.");
            }

            resultSet.close();
            statement.close();
            return response;
        } catch (Exception e) {
            return new GlobalResponse(500, e.toString());
        }
    }

    // public static void main(String[] args) {
    // DatabaseManager.connect();
    // Wishlist wishlist = new Wishlist();
    // GlobalResponse response=wishlist.addToWishlist(9, 4);
    // GlobalResponse response2 = wishlist.deleteFromWishlist(9, 2);
    // GlobalResponse response3=wishlist.getMyWishlist(9);
    // System.out.println(response2);
    // }
}
