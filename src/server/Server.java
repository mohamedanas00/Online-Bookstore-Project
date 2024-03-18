package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import server.DB.DatabaseManager;

public class Server {
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        int port =6666;
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server is Running in port :"+port);
            while(true){
                DatabaseManager.connect();
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket);
                clients.add(client);
                Thread t = new Thread(client);
                t.start();
                System.out.println("Thread #" + t.getId());
            }
        }catch (Exception  e){
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
