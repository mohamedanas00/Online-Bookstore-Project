package server.BSL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Models.Book;
import Models.BookResponse;
import Models.GlobalResponse;
import Models.User;
import server.DB.DatabaseManager;
import server.Middleware.Authorization;

public class BookBSl {
    private Connection connection;
    private Statement statement;

    public GlobalResponse addBook(int userId, Book book) {
        try {
            connection = DatabaseManager.getConnection();
            GlobalResponse response;
            if (!Authorization.checkAuthorization(userId, "user", connection)) {
                return new GlobalResponse(401, "Unauthorized");
            }
            String query = "INSERT INTO Book (title, author, genre, price, quantity,user_id) VALUES ('" +
                    book.getTitle() + "', '" + book.getAuthor() + "', '" + book.getGenre() + "', '"
                    + book.getPrice() + "', '" + book.getQuantity() + "', '" + userId + "')";

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

    public GlobalResponse deleteBook(int userId, int bookId) {
        try {
            connection = DatabaseManager.getConnection();
            GlobalResponse response;
            if (!Authorization.checkAuthorization(userId, "user", connection)) {
                return new GlobalResponse(401, "Unauthorized");
            }
            String query = "DELETE FROM Book WHERE user_id = " + userId + " AND id = " + bookId;

            statement = connection.createStatement();
            int rowsDeleted = statement.executeUpdate(query);
            if (rowsDeleted > 0) {
                response = new GlobalResponse(200, "Book deleted successfully.");
            } else {
                response = new GlobalResponse(404, "Book not found or you are not Owner of a book.");
            }
            statement.close();
            return response;
        } catch (Exception e) {
            return new GlobalResponse(500, e.toString());
        }
    }
    public GlobalResponse browseBooks(){
        List<Book> books = new ArrayList<>();
        UserBSL userBsl = new UserBSL();
        try {
            System.out.println("yyyyyyyyy");

            connection = DatabaseManager.getConnection();
            GlobalResponse response;
            String query ="SELECT * FROM Book";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("sssssssssssssssssss");
                Book book = new Book(resultSet);
                User user = userBsl.getUserDetails(book.getUserId());
                System.out.println(user);
                book.setUser(user);
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
    public GlobalResponse searchBooks(int userId, String searchQuery) {
        List<Book> books = new ArrayList<>();
        try {
            connection = DatabaseManager.getConnection();
            GlobalResponse response;
            if (!Authorization.checkAuthorization(userId, "user", connection)) {
                return new GlobalResponse(401, "Unauthorized");
            }

            UserBSL userBsl = new UserBSL();
            String query = "SELECT * " +
                    "FROM Book " +
                    "WHERE " +
                    "    title LIKE '%" + searchQuery + "%' " +
                    "    OR author LIKE '%" + searchQuery + "%' " +
                    "    OR genre LIKE '%" + searchQuery + "%' ";

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Book book = new Book(resultSet);
                User user = userBsl.getUserDetails(book.getUserId());
                book.setUser(user);
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

    public GlobalResponse ShowBooks(int userId, String choice) {
        List<Book> books = new ArrayList<>();
        try {
            connection = DatabaseManager.getConnection();
            GlobalResponse response;
            ResultSet resultSet;
            UserBSL userBsl = new UserBSL();
            if (!Authorization.checkAuthorization(userId, "user", connection)) {
                return new GlobalResponse(401, "Unauthorized");
            }
            if (choice.equals("2")) {
                resultSet = getBasedOnReviews();
            } else {
                resultSet = getBasedOnUserPreferences(userId);
            }
            while (resultSet.next()) {
                Book book = new Book(resultSet);
                User user = userBsl.getUserDetails(book.getUserId());
                book.setUser(user);
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

    public Book getBookById(int id) throws SQLException {
        connection = DatabaseManager.getConnection();
        statement = connection.createStatement();

        String query1 = "SELECT * FROM Book WHERE id = " + id;
        ResultSet resultSet = statement.executeQuery(query1);
        if (!resultSet.next()) {
            return null;
        }
        Book book = new Book(resultSet);
        resultSet.close();
        statement.close();
        return book;
    }

    public boolean updateBookQuantity(int id) throws SQLException {
        connection = DatabaseManager.getConnection();
        statement = connection.createStatement();
        String query = "UPDATE Book SET quantity = quantity - 1 WHERE id =" + id;
        int rowsInserted = statement.executeUpdate(query);
        if (rowsInserted < 0) {
            return false;
        }
        return true;
    }

    public boolean calculateBookRating(int id) throws SQLException {
        connection = DatabaseManager.getConnection();
        statement = connection.createStatement();
        String query = "SELECT book_id, AVG(rating) AS avg_rating FROM Review GROUP BY book_id";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            int bookId = resultSet.getInt("book_id");
            double avgRating = resultSet.getDouble("avg_rating");
            updateRatingAvg(connection, bookId, avgRating);
        }
        return false;
    }

    private static void updateRatingAvg(Connection connection, int bookId, double avgRating) throws SQLException {
        String updateQuery = "UPDATE Book SET ratingAvg = ? WHERE id = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setDouble(1, avgRating);
            updateStatement.setInt(2, bookId);
            updateStatement.executeUpdate();
        }
    }

    private ResultSet getBasedOnUserPreferences(int userId) throws SQLException {
        // Query to retrieve book recommendations based on user preferences
        String genreQuery = "SELECT DISTINCT genre FROM Book " +
                "WHERE user_id = " + userId;
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(genreQuery);

        // Collecting user's preferred genres
        List<String> preferredGenres = new ArrayList<>();
        while (resultSet.next()) {
            preferredGenres.add(resultSet.getString("genre"));
        }

        // Generating recommendations based on preferred genres
        for (String genre : preferredGenres) {
            String recommendationQuery = "SELECT * FROM Book " +
                    "WHERE genre = '" + genre + "' " +
                    "AND user_id != " + userId + " " +
                    "ORDER BY ratingAvg DESC " ;
            resultSet = statement.executeQuery(recommendationQuery);
        }
        return resultSet;
    }

    private ResultSet getBasedOnReviews() throws SQLException {
        // Query to retrieve book recommendations based on user preferences
        String sqlQuery = "SELECT b.* " +
                "FROM Book b " +
                "ORDER BY b.genre, b.ratingAvg DESC";
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        return resultSet;
    }


}
