package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import Models.GlobalResponse;
import Models.LogInResponse;
import client.validation.InputValidation;
import client.view.AdminView;
import client.view.UserView;

public class Client {
    private String IP;
    private int port;

    public Client(String iP, int port) {
        IP = iP;
        this.port = port;
    }

    public void runClient() {
        try (
                Socket socket = new Socket(IP, port);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Scanner scanner = new Scanner(System.in)) {
            Object receivedObject;
            System.out.println("***********************************************");
            System.out.println("*           Welcome to Online Bookstore        *");
            System.out.println("*                 Project                      *");
            System.out.println("***********************************************");
            boolean con = true;
            while (con) {
                System.out.println("\n");
                System.out.println("1. Signup");
                System.out.println("2. Login");
                System.out.println("Enter any key to Exit.");
                String choice;
                choice = InputValidation.getInput(scanner, "\nEnter your choice: ");
                switch (choice) {
                    case "1":
                        writer.write("Signup");
                        writer.newLine();
                        String name = InputValidation.getInput(scanner, "Enter name: ");
                        String username = InputValidation.getInput(scanner, "Enter username: ");
                        String password = InputValidation.getInput(scanner, "Enter password: ");
                        writer.write(name + ";" + username + ";" + password + ";");
                        writer.newLine();
                        writer.flush();
                        receivedObject = objectInputStream.readObject();
                        System.out.println(receivedObject);
                        break;
                    case "2":
                        writer.write("Login");
                        writer.newLine();
                        String userName = InputValidation.getInput(scanner, "Enter username: ");
                        String Password = InputValidation.getInput(scanner, "Enter password: ");
                        writer.write(userName + ";" + Password);
                        writer.newLine();
                        writer.flush();
                        receivedObject = (GlobalResponse) objectInputStream.readObject();
                        System.out.println(receivedObject);
                        if (((GlobalResponse) receivedObject).getStatus() == 200) {
                            String role = ((LogInResponse) receivedObject).getUser().getRole();
                            if (role.equals("user")) {
                                UserView.displayUserMenu(scanner,reader ,writer, objectInputStream);
                            }else{
                                AdminView.displayAdminMenu(scanner, reader, writer, objectInputStream);
                            }

                        }
                        break;
                    default:
                        con = false;
                        break;
                }

            }

        } catch (Exception e) {
            System.out.println("Error Message: " + e.toString());
        }
    }


}
