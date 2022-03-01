package a3.cmpt213.model;

import a3.cmpt213.ui.UserInterface;
import java.util.*;

/**
 * GameBoard class is used for generating board/tank shapes,
 * random placement of tanks, as well as handling User/Tank damage
 */

public class GameBoard {
    private Cell[][] board;
    private Set<Cell> emptyCells = new HashSet<>();
    private int health = 2500;
    private Tank[] tanks;

    public GameBoard(int numTanks) {
        this.board = new Cell[10][10];
        this.tanks = new Tank[numTanks];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = new Cell(Cell.States.UNKNOWN);
                emptyCells.add(board[i][j]);
            }
        }
        for (int i = 0; i < numTanks; i++) {
            Set<Cell> tankCells = generateTankShape();
            if (tankCells == null) {
                System.exit(-1);
            }
            tanks[i] = new Tank(tankCells, (char) ('A' + i));
        }
    }

    private Set<Cell> generateTankShape() {
        if (this.emptyCells.size() < 5) {
            return null;
        }
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        Set<Cell> shape = new HashSet<>();
        int xPos;
        int yPos;
        do {
            xPos = (int) (Math.random() * 9);
            yPos = (int) (Math.random() * 9);
        } while (!this.emptyCells.contains(board[xPos][yPos]));
        shape.add(board[xPos][yPos]);
        emptyCells.remove(board[xPos][yPos]);
        List<Integer[]> possibleCells = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int newX = xPos + directions[j][0];
                int newY = yPos + directions[j][1];
                if (newX >= 0 && newX < 10 && newY >= 0 && newY< 10 && emptyCells.contains(board[newX][newY])) {
                    possibleCells.add(new Integer[]{newX, newY});
                }
            }
            if (possibleCells.size() == 0) {
                return null;
            }
            int indexToAdd = (int) (Math.random() * possibleCells.size());
            xPos = possibleCells.get(indexToAdd)[0];
            yPos = possibleCells.get(indexToAdd)[1];
            shape.add(board[xPos][yPos]);
            emptyCells.remove(board[xPos][yPos]);
            possibleCells.remove(indexToAdd);
        }
        return shape;
    }

    public int tankFire() {
        int totalDmg = 0;
        for (Tank tank : this.tanks) {
            totalDmg += tank.getDamage();
        }
        UserInterface.displayTankArray(this.tanks);
        this.health -= totalDmg;
        return totalDmg;
    }

    public void userFire(int[] coordinates) {
        for (Tank tank : this.tanks) {
            if (tank.isTank(board[coordinates[0]][coordinates[1]])) {
                tank.hit(board[coordinates[0]][coordinates[1]]);
                UserInterface.displayHit();
                return;
            }
        }
        UserInterface.displayMiss();
        board[coordinates[0]][coordinates[1]].setState(Cell.States.MISS);
    }

    public int getHealth() {
        return health;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public Tank[] getTanks() {
        return tanks;
    }

/*
    public Tank[] getTanks(Set<Tank> tanks) {
        Iterator<Tank> itr = tanks.iterator();
        return tanks;
    }
 */
}
