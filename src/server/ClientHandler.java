package server;

import java.io.*;
import java.net.Socket;

import Models.GlobalResponse;
import Models.LogInResponse;
import server.BSL.*;
import server.handler.UserHandler;

public class ClientHandler implements Runnable {
    private Socket socket;
    private int id;
    private String userName;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {

            String req;
            UserHandler userObj = new UserHandler();
            while ((req = reader.readLine()) != null && !req.equals("5")) {
                handleRequest(req, reader, writer, objectOutputStream, userObj);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void handleRequest(String req, BufferedReader reader, BufferedWriter writer,
            ObjectOutputStream objectOutputStream, UserHandler userObj) throws IOException {
        switch (req) {
            case "Signup":
                handleSignup(reader, writer, objectOutputStream);
                break;
            case "Login":
                handleLogin(reader, writer, objectOutputStream, userObj);
                break;
            case "Add Book":
                userObj.handleAddBook(reader, writer, objectOutputStream);
                break;
            case "Remove Book":
                userObj.handleRemoveBook(reader, writer, objectOutputStream);
                break;
            case "search":
                userObj.handleSearch(reader, writer, objectOutputStream);
                break;
            case "show books":
                userObj.ShowBooksBasedOn(reader, writer, objectOutputStream);
                break;
            case "review":
                userObj.handleReview(reader, writer, objectOutputStream);
                break;
            case "borrow":
                userObj.handleBorrow(reader, writer, objectOutputStream);
                break;
            case "L_history":
                userObj.handleLHistory(reader, writer, objectOutputStream);
                break;
            case "B_history":
                userObj.handleBHistory(reader, writer, objectOutputStream);
                break;
            case "Manage Req":
                userObj.handleManageRequest(reader, writer, objectOutputStream);
                break;
            case "chat":
                userObj.handleChat(reader, writer, objectOutputStream);
                break;
            case "admin":
                userObj.showLibraryOverallStatistics(reader, writer, objectOutputStream);
                break;
            default:
                Server.users.get(userName);
                break;
        }
    }

    private void handleSignup(BufferedReader reader, BufferedWriter writer,
            ObjectOutputStream objectOutputStream) throws IOException {
        AuthBSl authBSl = new AuthBSl();
        String inputLine = reader.readLine();
        String[] parts = inputLine.split(";");
        String name = parts[0];
        String username = parts[1];
        String password = parts[2];
        GlobalResponse response = authBSl.signup(name, username, password);
        objectOutputStream.writeObject(response);
        writer.flush();
    }

    private void handleLogin(BufferedReader reader, BufferedWriter writer,
            ObjectOutputStream objectOutputStream, UserHandler userObj) throws IOException {
        AuthBSl authBSl = new AuthBSl();
        String inputLine = reader.readLine();
        String[] parts = inputLine.split(";");
        String username = parts[0];
        String password = parts[1];
        LogInResponse response = (LogInResponse) authBSl.login(username, password);
        objectOutputStream.writeObject(response);
        if (response.getStatus() == 200) {
            Server.users.put(username, socket);
            id = response.getUser().getId();
            userObj.setId(id);
            userName = username;
        }
        writer.flush();
    }

}
