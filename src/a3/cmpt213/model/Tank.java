package a3.cmpt213.model;

import java.util.Set;

public class Tank {
    private final Set<Cell> cells;
    private final int[] damageLevels = {0, 1, 2, 5, 20, 20};
    private int health = 5;

    public Tank(Set<Cell> cells) {
        this.cells = cells;
    }

    public void hit(Cell cell) {
        assert cells.contains(cell);
        if (cell.isUnknown()) {
            cell.setUnknown(false);
        }
        cell.setHit(true);
        this.health--;
    }

    public int getDamage() {
        return damageLevels[health];
    }

    public boolean isTank(Cell cell) {
        return cells.contains(cell);
    }
}
