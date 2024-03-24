package server.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import Models.Book;
import Models.GlobalResponse;
import Models.Request;
import Models.RequestResponse;
import server.Server;
import server.BSL.BookBSl;
import server.BSL.LibraryBSL;
import server.BSL.RequestBSL;
import server.BSL.ReviewBSL;

public class UserHandler {
    private int id;
    
    public void handleAddBook(BufferedReader reader, BufferedWriter writer,
            ObjectOutputStream objectOutputStream) throws IOException {
        BookBSl bookBSl = new BookBSl();
        String inputLine = reader.readLine();
        String[] parts = inputLine.split(";");
        String title = parts[0];
        String author = parts[1];
        String genre = parts[2];
        double price = Double.parseDouble(parts[3]);
        int quantity = Integer.parseInt(parts[4]);
        Book book = new Book(title, author, genre, price, quantity);
        GlobalResponse response = bookBSl.addBook(id, book);
        objectOutputStream.writeObject(response);
        writer.flush();
    }
   
    public void showLibraryOverallStatistics(BufferedReader reader, BufferedWriter writer,
        ObjectOutputStream objectOutputStream) throws IOException {
        LibraryBSL libraryBSL =new LibraryBSL();
        GlobalResponse response = libraryBSL.getLibraryStatistics(id);
        objectOutputStream.writeObject(response);
        writer.flush();
    }

    public void ShowBooksBasedOn(BufferedReader reader, BufferedWriter writer,
    ObjectOutputStream objectOutputStream)throws IOException{
        BookBSl bookBSl = new BookBSl();
        String text = reader.readLine();
        GlobalResponse response =  bookBSl.ShowBooks(id, text);
        objectOutputStream.writeObject(response);
        writer.flush();
    }
    public void handleRemoveBook(BufferedReader reader, BufferedWriter writer,
            ObjectOutputStream objectOutputStream) throws IOException {
        BookBSl bookBSl = new BookBSl();

        int bookId = Integer.parseInt(reader.readLine());
        GlobalResponse response = bookBSl.deleteBook(id, bookId);
        objectOutputStream.writeObject(response);
        writer.flush();
    }

    public void handleSearch(BufferedReader reader, BufferedWriter writer,
            ObjectOutputStream objectOutputStream) throws IOException {
        BookBSl bookBSl = new BookBSl();
        String text = reader.readLine();
        GlobalResponse response = bookBSl.searchBooks(id, text);
        objectOutputStream.writeObject(response);
        writer.flush();
    }

    public void handleReview(BufferedReader reader, BufferedWriter writer,
            ObjectOutputStream objectOutputStream) throws IOException {
        ReviewBSL reviewBSL = new ReviewBSL();
        String inputLine = reader.readLine();
        String[] parts = inputLine.split(";");
        int bookId = Integer.parseInt(parts[0]);
        String comment = parts[1];
        int rating = Integer.parseInt(parts[2]);
        GlobalResponse response = reviewBSL.addReview(id, bookId, rating, comment);
        objectOutputStream.writeObject(response);
        writer.flush();
    }

    public void handleBorrow(BufferedReader reader, BufferedWriter writer,
            ObjectOutputStream objectOutputStream) throws IOException {

        RequestBSL requestBSL = new RequestBSL();
        int bookId = Integer.parseInt(reader.readLine());
        GlobalResponse response = requestBSL.addRequest(id, bookId);
        objectOutputStream.writeObject(response);
        writer.flush();
    }

    public void handleLHistory(BufferedReader reader, BufferedWriter writer,
            ObjectOutputStream objectOutputStream) throws IOException {

        RequestBSL requestBSL = new RequestBSL();
        GlobalResponse response = requestBSL.showRequestHistory(id);
        objectOutputStream.writeObject(response);
        writer.flush();
    }

    public void handleBHistory(BufferedReader reader, BufferedWriter writer,
            ObjectOutputStream objectOutputStream) throws IOException {

        RequestBSL requestBSL = new RequestBSL();
        GlobalResponse response = requestBSL.showRequestHistoryAsBorrower(id);
        objectOutputStream.writeObject(response);
        writer.flush();
    }

    public void handleManageRequest(BufferedReader reader, BufferedWriter writer,
            ObjectOutputStream objectOutputStream) throws IOException {

        RequestBSL requestBSL = new RequestBSL();
        String inputLine = reader.readLine();
        String[] parts = inputLine.split(";");
        int reqId = Integer.parseInt(parts[0]);
        int status = Integer.parseInt(parts[1]);
        GlobalResponse response;
        System.out.println("ssssssssssssss2");
        if (status == 0) {
            response = requestBSL.manageRequest(id, reqId, false);
        } else {
            System.out.println("ssssssssssssss");
            response = requestBSL.manageRequest(id, reqId, true);
            objectOutputStream.writeObject(response);
            writer.flush();
            if (response.getStatus() == 200) {
                String borrower = "";
                RequestResponse requests = (RequestResponse) response;
                List<Request> requestList = requests.getRequests();
                for (Request request : requestList) {
                    borrower = request.getBorrowerUsername();
                }

                writer.write("chat");
                writer.newLine();
                writer.write(borrower);
                writer.newLine();
                writer.flush();

                BufferedWriter targetWriter = new BufferedWriter(
                        new OutputStreamWriter(Server.users.get(borrower).getOutputStream()));
                startChatSession(reader, targetWriter);
            } else {
                writer.write("no");
                writer.newLine();
                writer.flush();
            }
        }
        objectOutputStream.writeObject(response);
        writer.flush();
    }

    public void handleChat(BufferedReader reader, BufferedWriter writer,
            ObjectOutputStream objectOutputStream) throws IOException {
        String chatWith = reader.readLine();
        if (Server.users.containsKey(chatWith)) {
            BufferedWriter targetWriter = new BufferedWriter(
                    new OutputStreamWriter(Server.users.get(chatWith).getOutputStream()));
            startChatSession(reader, targetWriter);
        } else {
            writer.write("User is Offline");
            writer.newLine();
            writer.flush();
        }
    }

    public void startChatSession(BufferedReader reader, BufferedWriter targetWriter) throws IOException {
        String message;
        while (true) {
            message = reader.readLine();
            targetWriter.write(message);
            targetWriter.newLine();
            targetWriter.flush();
            if (message.equals("end")) {
                break;
            }
        }
    }

    
    public void setId(int id) {
        this.id = id;
    }

}
