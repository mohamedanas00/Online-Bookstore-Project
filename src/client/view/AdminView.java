package client.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class AdminView {
    public static void displayAdminMenu(Scanner scanner, BufferedReader reader, BufferedWriter writer,
            ObjectInputStream objectInputStream) {
        try {
            boolean flag = true;
            Object receivedObject;
            while (flag) {
                System.out.println("\nWelcome to the Admin Interface!");
                System.out.println("1. Library overall statistics:");
                System.out.println("Enter any key to Logout.");
                System.out.print("\nEnter your choice: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        writer.write("admin");
                        writer.newLine();
                        writer.flush();

                        receivedObject = objectInputStream.readObject();
                        System.out.println(receivedObject);
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
}
