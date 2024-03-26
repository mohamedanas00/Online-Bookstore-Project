package client.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;
import client.validation.InputValidation;

public class UserView {
    private static String state;

    public static void displayUserMenu(Scanner scanner, BufferedReader reader, BufferedWriter writer,
            ObjectInputStream objectInputStream) {
        try {
            boolean flag = true;
            int id;
            Object receivedObject;
            while (flag) {
                System.out.println("\nWelcome to the User Interface!");
                System.out.println("1. Add Book.");
                System.out.println("2. Remove Book.");
                System.out.println("3. Browse Books.");
                System.out.println("4. Search for Books by Title, Author, or Genre.");
                System.out.println("5. Recommendation Books Based on  personal genre preferences.");
                System.out.println("6. Recommendation Books Based on current reviews calculation.");
                System.out.println("7. Add a Review Book.");
                System.out.println("8. Request For Borrow Book.");
                System.out.println("9. Show My Request History as lender.");
                System.out.println("10. Show MY Request History as borrower.");
                System.out.println("11. Manage Incoming Request.");
                System.out.println("12. Chatting.");
                System.out.println("Enter any key to Logout.");
                System.out.print("\nEnter your choice: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        writer.write("Add Book");
                        writer.newLine();

                        String title = InputValidation.getInput(scanner, "Enter title: ");
                        String author = InputValidation.getInput(scanner, "Enter author: ");
                        String genre = InputValidation.getInput(scanner, "Enter genre: ");
                        double price = InputValidation.getDoubleInput(scanner, "Enter price: ");
                        int quantity = InputValidation.getIntInput(scanner, "Enter quantity: ");

                        writer.write(title + ";" + author + ";" + genre + ";" + price + ";" + quantity + ";");
                        writer.newLine();
                        writer.flush();

                        receivedObject = objectInputStream.readObject();
                        System.out.println(receivedObject);
                        break;

                    case "2":
                        writer.write("Remove Book");
                        writer.newLine();

                        id = InputValidation.getIntInput(scanner, "Enter Book ID: ");
                        String idStr = String.valueOf(id);
                        writer.write(idStr);
                        writer.newLine();
                        writer.flush();

                        receivedObject = objectInputStream.readObject();
                        System.out.println(receivedObject);
                        break;
                    case "3":
                        writer.write("browse books");
                        writer.newLine();
                        writer.flush();


                        receivedObject = objectInputStream.readObject();
                        System.out.println(receivedObject);
                        break;
                    case "4":
                        writer.write("search");
                        writer.newLine();

                        String text = InputValidation.getInput(scanner, "Enter  Title, Author, or Genre : ");
                        writer.write(text);
                        writer.newLine();
                        writer.flush();

                        receivedObject = objectInputStream.readObject();
                        System.out.println(receivedObject);
                        break;
                    case "5":
                        writer.write("show books");
                        writer.newLine();

                        writer.write("1");
                        writer.newLine();
                        writer.flush();
                        receivedObject = objectInputStream.readObject();
                        System.out.println(receivedObject);
                        break;
                    case "6":
                        writer.write("show books");
                        writer.newLine();

                        writer.write("2");
                        writer.newLine();
                        writer.flush();
                        receivedObject = objectInputStream.readObject();
                        System.out.println(receivedObject);
                        break;
                    case "7":
                        writer.write("review");
                        writer.newLine();
                        int bookId = InputValidation.getIntInput(scanner, "Enter Book ID: ");
                        String comment = InputValidation.getInput(scanner, "Enter Your Comment: ");
                        int Rating = InputValidation.getIntInputInRange(scanner, "Enter a number between 0 and 5:", 0,
                                5);

                        writer.write(bookId + ";" + comment + ";" + Rating + ";");
                        writer.newLine();
                        writer.flush();

                        receivedObject = objectInputStream.readObject();
                        System.out.println(receivedObject);
                        break;

                    case "8":
                        writer.write("borrow");
                        writer.newLine();
                        writer.flush();

                        id = InputValidation.getIntInput(scanner, "Enter Book ID: ");
                        String idStr1 = String.valueOf(id);
                        writer.write(idStr1);
                        writer.newLine();
                        writer.flush();

                        receivedObject = objectInputStream.readObject();
                        System.out.println(receivedObject);
                        break;
                    case "9":
                        writer.write("L_history");
                        writer.newLine();
                        writer.flush();

                        receivedObject = objectInputStream.readObject();
                        System.out.println(receivedObject);

                        break;
                    case "10":
                        writer.write("B_history");
                        writer.newLine();
                        writer.flush();

                        receivedObject = objectInputStream.readObject();
                        System.out.println(receivedObject);
                        break;
                    case "11":
                        writer.write("Manage Req");
                        writer.newLine();

                        int reqId = InputValidation.getIntInput(scanner, "Enter Request ID: ");
                        int status = InputValidation.getIntInputInRange(scanner, "Enter 0.Refused 1.Accept ", 0, 1);
                        writer.write(reqId + ";" + status + ";");
                        writer.newLine();
                        writer.flush();

                        receivedObject = objectInputStream.readObject();

                        if (reader.readLine().equals("chat")) {
                            String ChatWith = reader.readLine();
                            startChatSession(reader, writer, scanner, ChatWith);
                        }
                        System.out.println(receivedObject);
                        break;
                    case "12":
                        System.out.println("Enter username you want to chat with:");
                        String chatUsername = scanner.nextLine();
                        writer.write("chat");
                        writer.newLine();

                        writer.write(chatUsername);
                        writer.newLine();
                        writer.flush();
                        // Start chat session
                        startChatSession(reader, writer, scanner, chatUsername);
                        break;
                    default:
                        writer.write("Logout");
                        writer.newLine();
                        writer.flush();
                        flag = false;

                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    private static void startChatSession(BufferedReader reader, BufferedWriter writer, Scanner scanner,
            String chatUsername)
            throws IOException {
        // Create a thread for reading messages
        state = "run";
        System.out.println("\nStart Chatting with " + chatUsername + " :)");
        Thread readerThread = new Thread(() -> {
            try {
                String receivedMessage;
                while ((receivedMessage = reader.readLine()) != null) {
                    if (receivedMessage.equals("User is Offline")) {
                        System.out.println(chatUsername + " is Offline ; Type 'end to close session.");
                        break;
                    }
                    if (receivedMessage.equals("end") || state.equals("stop")) {
                        state = "stop";
                        System.out.println("Chat session ended.");
                        break;
                    }
                    System.out.println(chatUsername + " : " + receivedMessage);
                }
            } catch (IOException e) {
                System.out.println("Error while reading message: " + e.getMessage());
            }
        });

        // Create a thread for writing messages
        Thread writerThread = new Thread(() -> {
            try {
                String message;
                while (true) {
                    System.out.println("Enter message (type 'end' to end chat session):");
                    message = scanner.nextLine();
                    writer.write(message);
                    writer.newLine();
                    writer.flush();
                    if (message.equals("end") || state.equals("stop")) {
                        state = "stop";
                        System.out.println("Chat session ended ,type 'end' .");
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error while writing message: " + e.getMessage());
            }
        });

        // Start both threads
        writerThread.start();
        readerThread.start();

        try {
            // Wait for both threads to finish
            readerThread.join();
            writerThread.join();
        } catch (InterruptedException e) {
            System.out.println("Error while waiting for threads: " + e.getMessage());
        }
    }

}
