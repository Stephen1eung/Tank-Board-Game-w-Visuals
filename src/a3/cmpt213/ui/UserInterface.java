package a3.cmpt213.ui;

import a3.cmpt213.model.Cell;
import a3.cmpt213.model.Tank;

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

    public static void displayBoard(Cell[][] board, Tank[] tanks, boolean fog) {
        StringBuilder result = new StringBuilder("Game Board:\n\t  ");
        for (int i = 0; i < 10; i++) {
            result.append(letters.get(i));
            result.append(" ");
        }
        result.append("\n");
        for (int i = 0; i < 10; i++) {
            result.append("\t");
            result.append(i + 1);
            result.append(" ");
            for (int j = 0; j < 10; j++) {
                result.append(cellToSymbol(board[i][j], tanks, fog));
                result.append(" ");
            }
            result.append("\n");
        }
        System.out.println(result);
    }

    private static char cellToSymbol(Cell cell, Tank[] tanks, boolean fog) {
        boolean isTank = false;
        char tankId = '*'; // IDE complains if you don't initialize
        char result = '*'; // if you see a '*' on the board there's a bug
        for (Tank tank : tanks) {
            if (tank.isTank(cell)) {
                isTank = true;
                tankId = tank.getId();
            }
        }
        switch (cell.getState()) {
            case UNKNOWN -> {
                if (isTank) {
                    result = (fog) ? '~' : tankId;
                } else {
                    result = (fog) ? '~' : '.';
                }
            }
            case MISS -> {
                result = ' ';
            }
            case HIT -> {
                result = Character.toLowerCase(tankId);
            }
        }
        return result;
    }
}
