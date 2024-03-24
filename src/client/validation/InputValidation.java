package client.validation;

import java.util.Scanner;

public class InputValidation {
    public static String getInput(Scanner scanner, String message) {
        String input;
        do {
            System.out.print(message);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }

    public static double getDoubleInput(Scanner scanner, String message) {
        double input = 0.0;
        boolean isValid = false;
        do {
            System.out.print(message);
            try {
                input = Double.parseDouble(scanner.nextLine().trim());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        } while (!isValid);
        return input;
    }
    public static int getIntInput(Scanner scanner, String message) {
        int input = 0;
        boolean isValid = false;
        do {
            System.out.print(message);
            try {
                input = Integer.parseInt(scanner.nextLine().trim());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        } while (!isValid);
        return input;
    }

    public static int getIntInputInRange(Scanner scanner, String message, int min, int max) {
        int input = 0;
        boolean isValid = false;
        do {
            System.out.print(message);
            try {
                input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) {
                    isValid = true;
                } else {
                    System.out.println("Input out of range. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        } while (!isValid);
        return input;
    }
    
    
}
