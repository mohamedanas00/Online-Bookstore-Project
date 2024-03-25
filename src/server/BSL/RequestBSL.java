package server.BSL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Models.Book;
import Models.GlobalResponse;
import Models.Request;
import Models.RequestResponse;
import server.DB.DatabaseManager;

public class RequestBSL {
    private Connection connection;
    private Statement statement;
    private BookBSl bookBSl;

    public GlobalResponse addRequest(int borrowId, int bookId) {
        GlobalResponse response;
        bookBSl = new BookBSl();
        try {
            connection = DatabaseManager.getConnection();
            statement = connection.createStatement();
            // *Get Book details and Check Exist */
            Book book = bookBSl.getBookById(bookId);
            if (book == null) {
                return new GlobalResponse(404, "Book Not Exist.");
            }
            if (book.getUserId() == borrowId) {
                return new GlobalResponse(404, "You Already Owned this book.");
            }
            if (book.getQuantity() <= 0) {
                return new GlobalResponse(200, "BooK is Already Borrowed.");
            }

            // *Decrease Quantity */
            if (!bookBSl.updateBookQuantity(bookId)) {
                return new GlobalResponse(200, "Something want wrong please try again.");

            }
            // *ADD Request */
            String query2 = "INSERT INTO Request (borrower_id , lender_id,book_id ) VALUES ('" +
                    borrowId + "', '" + book.getUserId() + "', '" + book.getId()
                    + "')";

            statement = connection.createStatement();
            int rowsInserted = statement.executeUpdate(query2);

            if (rowsInserted > 0) {
                response = new GlobalResponse(200, "New Request is Send.");
            } else {
                response = new GlobalResponse(404, "Failed to Send Request.");

            }
            statement.close();
            return response;
        } catch (Exception e) {
            return new GlobalResponse(500, e.toString());
        }
    }

    public GlobalResponse manageRequest(int userId, int requestId, boolean status) {
        GlobalResponse response;
        Request request;
        List<Request> requests = new ArrayList<>();
        try {
            connection = DatabaseManager.getConnection();
            statement = connection.createStatement();
            String query = "SELECT r.*, " +
                    "bu.username AS borrower_username, lu.username AS lender_username " +
                    "FROM Request r " +
                    "JOIN User bu ON r.borrower_id = bu.id " +
                    "JOIN User lu ON r.lender_id = lu.id " +
                    "WHERE r.id = " + requestId;
            ResultSet resultSet = statement.executeQuery(query);
            String stat = " ";
            if (resultSet.next()) {
                stat = resultSet.getString("status");
                request = new Request(resultSet);
            } else {
                return new GlobalResponse(404, "Request Not Exist.");
            }

            if (!stat.equals("Pending")) {
                return new GlobalResponse(201, "Request Already Managed.");
            }
            int lenderId = resultSet.getInt("lender_id");
            if (lenderId != userId) {
                return new GlobalResponse(401, "Unauthorized");
            }
            String query1;
            if (status) {
                stat="Accepted";
                query1 = "UPDATE Request SET status = 'Accepted' WHERE id =" + requestId;

            } else {
                stat="Rejected";
                query1 = "UPDATE Request SET status = 'Rejected' WHERE id =" + requestId;
            }

            int rowsInserted = statement.executeUpdate(query1);
            if (rowsInserted > 0) {
                request.setStatus(stat);
                requests.add(request);
                response = new RequestResponse(200, "Success.", requests);

            } else {
                response = new GlobalResponse(404, "Failed.");

            }
            resultSet.close();
            statement.close();
            return response;
        } catch (Exception e) {
            return new GlobalResponse(500, e.toString());
        }
    }

    public GlobalResponse showRequestHistory(int userId) {
        List<Request> requests = new ArrayList<>();
        GlobalResponse response;
        ResultSet resultSet;

        try {
            connection = DatabaseManager.getConnection();
            statement = connection.createStatement();

            String query = "SELECT R.*, L.username AS lender_username, B.username AS borrower_username " +
                    "FROM Request R " +
                    "JOIN User L ON R.lender_id = L.id " +
                    "JOIN User B ON R.borrower_id = B.id " +
                    "WHERE R.lender_id = " + userId;

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Request request = new Request(resultSet);
                requests.add(request);
            }

            if (requests.isEmpty()) {
                return new GlobalResponse(200, "Request history is empty");
            }

            response = new RequestResponse(200, "Success", requests);
            resultSet.close();
            statement.close();
            return response;
        } catch (Exception e) {
            return new GlobalResponse(500, e.toString());
        }
    }

    public GlobalResponse showRequestHistoryAsBorrower(int userId) {
        List<Request> requests = new ArrayList<>();
        GlobalResponse response;
        ResultSet resultSet;

        try {
            connection = DatabaseManager.getConnection();
            statement = connection.createStatement();

            String query = "SELECT R.*, L.username AS lender_username, B.username AS borrower_username " +
                    "FROM Request R " +
                    "JOIN User L ON R.lender_id = L.id " +
                    "JOIN User B ON R.borrower_id = B.id " +
                    "WHERE R.borrower_id = " + userId;

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Request request = new Request(resultSet);
                requests.add(request);
            }

            if (requests.isEmpty()) {
                return new GlobalResponse(200, "Request history is empty");
            }

            response = new RequestResponse(200, "Success", requests);
            resultSet.close();
            statement.close();
            return response;
        } catch (Exception e) {
            return new GlobalResponse(500, e.toString());
        }
    }

}
