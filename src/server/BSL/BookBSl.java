package server.BSL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Models.Book;
import Models.BookResponse;
import Models.GlobalResponse;
import server.DB.DatabaseManager;
import server.Utils.Authorization;

public class BookBSl {
    private Connection connection;
    private Statement statement;

    public GlobalResponse addBook(int userId, Book book) {
        try {
            connection = DatabaseManager.getConnection();
            GlobalResponse response;
            if (!Authorization.checkAuthorization(userId, "admin", connection)) {
                return new GlobalResponse(401, "Unauthorized");
            }
            String query = "INSERT INTO Book (title, author, genre, price, quantity) VALUES ('" +
                    book.getTitle() + "', '" + book.getAuthor() + "', '" + book.getGenre() + "', '"
                    + book.getPrice() + "', '" + book.getQuantity() + "')";

            statement = connection.createStatement();
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

    public GlobalResponse searchBooks(int userId,String searchQuery) {
        List<Book> books = new ArrayList<>();
        try {
            connection = DatabaseManager.getConnection();
            GlobalResponse response;
            if (!Authorization.checkAuthorization(userId, "user", connection)) {
                return new GlobalResponse(401, "Unauthorized");
            }
            String query = "SELECT * " +
            "FROM Book " +
            "WHERE " +
            "    title LIKE '%" + searchQuery + "%' " +
            "    OR author LIKE '%" + searchQuery + "%' " +
            "    OR genre LIKE '%" + searchQuery + "%' " ;
           

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Book book = new Book(resultSet);
                books.add(book);
            }
            response = new BookResponse(200, "Success", books);
            statement.close();
            resultSet.close();
            return response;
        } catch (Exception e) {
            return new GlobalResponse(500, e.toString());
        }
    }
    public static void main(String[] args) {
    DatabaseManager.connect();
    // Book book =new Book("Future", "Mark", "Drama", 99.4, 10);
    BookBSl bookBSl =new BookBSl();
    GlobalResponse RES =bookBSl.searchBooks(9, "future");
    // GlobalResponse res =bookBSl.addBook(10, book);
    System.out.println(RES);
    }

}
