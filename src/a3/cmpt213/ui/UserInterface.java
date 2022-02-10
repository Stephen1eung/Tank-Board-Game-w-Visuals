package a3.cmpt213.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class UserInterface {
    private static final List<Character> letters = new ArrayList<>(List.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'));

    public static int[] getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your move:");
        String userInput = scanner.nextLine().toUpperCase(Locale.ROOT);
        while (!isValidInput(userInput)) {
            System.out.println("Sorry, that's not a valid move. Please enter a coordinate.");
            userInput = scanner.nextLine().toUpperCase(Locale.ROOT);
        }
        return parseInput(userInput);
    }

    private static boolean isValidInput(String input) {
        if (input.length() < 2 || input.length() > 3) {
            return false;
        } else if (!letters.contains(input.charAt(0))) {
            return false;
        } else {
            int number;
            try {
                number = Integer.parseInt(input.substring(1));
            } catch (NumberFormatException e) {
                return false;
            }
            return (number > 0 && number <= 10);
        }
    }

    private static int[] parseInput(String input) {
        return new int[]{letters.indexOf(input.charAt(0)), Integer.parseInt(input.substring(1))};
    }
}
