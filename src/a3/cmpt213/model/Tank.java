package a3.cmpt213.model;

import java.util.Set;

public class Tank {
    private final Set<Cell> cells;
    private final int[] damageLevels = {0, 1, 2, 5, 20, 20};
    private int health = 5;
    private final char id;

    public Tank(Set<Cell> cells, char id) {
        this.cells = cells;
        this.id = id;
    }

    public void hit(Cell cell) {
        assert cells.contains(cell);
        cell.setState(Cell.States.HIT);
        this.health--;
    }

    public int getDamage() {
        return damageLevels[health];
    }

    public boolean isTank(Cell cell) {
        return cells.contains(cell);
    }

    public char getId() {
        return id;
    }
}
