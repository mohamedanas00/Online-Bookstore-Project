package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import Models.GlobalResponse;

public class Client {
    private String IP;
    private int port;

    public Client(String iP, int port) {
        IP = iP;
        this.port = port;
    }

    public void runClient() {
        try {
            Socket socket = new Socket(IP, port);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Your Choice :");
            String input;
            do {
                input = scanner.nextLine();
                writer.write(input);
                writer.newLine();
                writer.flush();
                switch (input) {
                    case "1":
                        System.out.println("username :");
                        String username = scanner.nextLine();
                        writer.write(username);
                        writer.newLine();
                        System.out.println("password :");
                        String password = scanner.nextLine();
                        writer.write(password);
                        writer.newLine();
                        writer.flush();

                        Object receivedObject = objectInputStream.readObject();

                        if(receivedObject instanceof GlobalResponse){
                            GlobalResponse res =(GlobalResponse) receivedObject;
                            System.out.println(res);
                        }
                        
                        break;

                    default:
                        break;
                }
            } while (!input.equals("5"));

            objectInputStream.close();
            reader.close();
            writer.close();
            socket.close();
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
