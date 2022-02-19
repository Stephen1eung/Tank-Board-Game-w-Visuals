package a3.cmpt213.ui;

import a3.cmpt213.model.Cell;
import a3.cmpt213.model.Tank;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class UserInterface {
    private static final List<Character> letters = new ArrayList<>(List.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'));
    private static final String WELCOME = "Welcome to S&S Fortress Defense\n";
    private static final String HIT = "Hit!\n";
    private static final String MISS = "Miss\n";

    public static int[] getInput() {
        int[] coordinates = new int[2];
        int xPos;
        int yPos;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your move:");
        String userInput = scanner.nextLine().toUpperCase(Locale.ROOT);
        while (!isValidInput(userInput)) {
            System.out.println("Sorry, that's not a valid move. Please enter a coordinate.");
            userInput = scanner.nextLine().toUpperCase(Locale.ROOT);
        }
        xPos = letters.indexOf(userInput.charAt(0));
        yPos = Integer.parseInt(userInput.substring(1));
        coordinates[0] = xPos;
        coordinates[1] = yPos - 1;
        return coordinates;
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

    public static void displayBoard(Cell[][] board, Tank[] tanks, boolean fog) {
        StringBuilder result = new StringBuilder("Game Board:\n\t  ");
        result.append("  ");
        for (int i = 0; i < 10; i++) {
            result.append(letters.get(i));
            result.append("  ");
        }
        result.append("\n");
        for (int i = 0; i < 10; i++) {
            if(i < 9){
                result.append("\t");
                result.append(" ");
                result.append(i + 1);
                result.append("  ");
            }
            else{
                result.append("\t");
                result.append(i + 1);
                result.append("  ");
            }
            for (int j = 0; j < 10; j++) {
                result.append(cellToSymbol(board[i][j], tanks, fog));
                result.append("  ");
            }
            result.append("\n");
        }
        if (!fog) {
            result.append("Lowercase letters indicate where tanks are hit\n");
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

    public static void displayWelcome() {System.out.println(WELCOME);}

    public static void displayHit() {System.out.println(HIT);}

    public static void displayMiss() {System.out.println(MISS);}

    //TODO: remove display
    public static void display(String message) {System.out.println(message);}

    public static void displayHealth(int health) {System.out.println("Fortress Structure Left: " + health);}

    public static void displayLose() {
        System.out.println("We Gotta Retreat Captain! We Lost!\n");
        displayExit();
    }

    public static void displayWin() {
        System.out.println("Congrats Captain, All Tanks are destroyed You Won!\n");
        displayExit();
    }
    private static void displayExit(){
        System.out.println("-----GAME OVER-----");
        System.out.println("------EXITING------\n");
    }
}
