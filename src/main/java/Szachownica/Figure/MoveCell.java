package Szachownica.Figure;

public class MoveCell {
    public Bishop.canMove movement;
    int x,y;
    public MoveCell(Bishop.canMove movement, int x , int y) {
        this.movement = movement;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoveCell)) return false;
        MoveCell other = (MoveCell) o;
        return this.movement == other.movement; // same enum value => equal
    }

    @Override
    public int hashCode() {
        // For instance, just hash the enum's name or its ordinal
        return movement.hashCode();
    }
}
