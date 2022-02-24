package a3.cmpt213.game;

import a3.cmpt213.model.GameBoard;
import a3.cmpt213.model.Tank;
import a3.cmpt213.ui.UserInterface;

import java.util.Arrays;

public class Game {
    private static final int DEFAULT_NUM_TANKS = 5;
    private static final int FAILURE = -1;
    public static boolean cheats = false;
    public static boolean isCheats() {return cheats;}
    public static void setCheats(boolean cheats) {Game.cheats = cheats;}

    public static void main(String[] args) {
        int numTanks = DEFAULT_NUM_TANKS;

        switch (args.length) {
            case (0):
                break;
            case (1):
                try {
                    numTanks = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    System.out.println("Sorry, I didn't understand the number of tanks you wanted to use\n");
                    System.exit(FAILURE);
                }
                break;
            case (2):
                try {
                    numTanks = Integer.parseInt(args[0]);
                    if (args[1].equalsIgnoreCase("--cheats")) {
                        setCheats(true);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Sorry, I didn't understand the number of tanks you wanted to use\n");
                    System.exit(FAILURE);
                }
                break;
        }
        if (numTanks < 1) {
            System.out.println("Sorry, having less than one tank doesn't work");
            System.exit(FAILURE);
        }

        GameBoard gameBoard = new GameBoard(numTanks);
        UserInterface.displayWelcome();
        if (cheats) {
            UserInterface.displayBoard(gameBoard.getBoard(), gameBoard.getTanks(), !cheats);
            UserInterface.displayHealth(gameBoard.getHealth());
        }
        boolean running = true;
        while (running) {
            UserInterface.displayBoard(gameBoard.getBoard(), gameBoard.getTanks(), true);
            UserInterface.displayHealth(gameBoard.getHealth());
            gameBoard.userFire(UserInterface.getInput());
            if(numTanks * 5 == Tank.getCellsHitSize()){
                UserInterface.displayBoard(gameBoard.getBoard(), gameBoard.getTanks(), false);
                UserInterface.displayWin();
                System.exit(FAILURE);
            }
            gameBoard.tankFire();
            if(gameBoard.getHealth() <= 0){
                UserInterface.displayBoard(gameBoard.getBoard(), gameBoard.getTanks(), false);
                UserInterface.displayLose();
                System.exit(FAILURE);
            }
        }
    }
}
