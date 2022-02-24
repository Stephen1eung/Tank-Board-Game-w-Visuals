package a3.cmpt213.model;

/**
 * Cell class allows states to be mutated and accessed
 */

public class Cell {
    public enum States {
        UNKNOWN,
        MISS,
        HIT
    }
    private States state;

    public Cell(States state) {
        this.state = state;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }
}
