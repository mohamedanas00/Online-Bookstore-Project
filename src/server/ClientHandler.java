package server;

import java.io.*;
import java.net.Socket;

import Models.GlobalResponse;
import Models.LogInResponse;
import server.BSL.*;
import server.handler.IoHandler;

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
            IoHandler userObj = new IoHandler();
            while ((req = reader.readLine()) != null && !req.equals("5")) {
                try {
                    handleRequest(req, reader, writer, objectOutputStream, userObj);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void handleRequest(String req, BufferedReader reader, BufferedWriter writer,
            ObjectOutputStream objectOutputStream, IoHandler userObj) throws IOException {
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
            case "browse books":
                System.out.println("sssssss");
                userObj.handleBrowseBook(reader, writer, objectOutputStream);
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
                userObj.handleChat(reader, writer, "");
                break;
            case "admin":
                userObj.showLibraryOverallStatistics(reader, writer, objectOutputStream);
                break;
            case "Logout":
                Server.users.remove(userName);
                break;
            default:
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
            ObjectOutputStream objectOutputStream, IoHandler userObj) throws IOException {
        AuthBSl authBSl = new AuthBSl();
        String inputLine = reader.readLine();
        String[] parts = inputLine.split(";");
        String username = parts[0];
        String password = parts[1];
        GlobalResponse response = authBSl.login(username, password);
        if (response.getStatus() == 200) {
            LogInResponse res = (LogInResponse) response;
            Server.users.put(username, socket);
            this.id = res.getUser().getId();
            userObj.setId(id);
            this.userName = username;
        }
        objectOutputStream.writeObject(response);

        writer.flush();
    }

}
