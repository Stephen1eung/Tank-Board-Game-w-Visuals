package a3.cmpt213.model;

import java.util.*;

public class GameBoard {
    private Cell[][] map;
    private Set<Cell> emptyCells = new HashSet<>();
    private int health = 2500;
    private Tank[] tanks;
    private boolean cheats;

    public GameBoard(int numTanks, boolean cheats) {
        this.map = new Cell[10][10];
        this.tanks = new Tank[numTanks];
        this.cheats = cheats;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                map[i][j] = new Cell();
                emptyCells.add(map[i][j]);
            }
        }
        for (int i = 0; i < numTanks; i++) {
            Set<Cell> tankCells = generateTankShape();
            if (tankCells == null) {
                System.exit(-1);
            }
            tanks[i] = new Tank(tankCells);
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
        } while (this.emptyCells.contains(map[xPos][yPos]));
        shape.add(map[xPos][yPos]);
        emptyCells.remove(map[xPos][yPos]);
        List<Integer[]> possibleCells = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int newX = xPos + directions[j][0];
                int newY = yPos + directions[j][1];
                if (newX >= 0 && newX < 10 && newY >= 0 && newY< 10 && emptyCells.contains(map[newX][newY])) {
                    possibleCells.add(new Integer[]{newX, newY});
                }
            }
            if (possibleCells.size() == 0) {
                return null;
            }
            int indexToAdd = (int) (Math.random() * possibleCells.size());
            xPos = possibleCells.get(indexToAdd)[0];
            yPos = possibleCells.get(indexToAdd)[1];
            shape.add(map[xPos][yPos]);
            emptyCells.remove(map[xPos][yPos]);
            possibleCells.remove(indexToAdd);
        }
        return shape;
    }

    private void tankFire() {
        for (Tank tank : this.tanks) {
            this.health -= tank.getDamage();
        }
    }

    private void userFire(int x, int y) {
        for (Tank tank : this.tanks) {
            if (tank.isTank(map[x][y])) {
                tank.hit(map[x][y]);
                break;
            }
        }
    }

    public int getHealth() {
        return health;
    }

    // TODO: getBoard() should return enum instead of string - more interface agnostic
}
