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
    public String toString() {
        return "x = " + x + " y = " + y + " movement = " + movement;
    }
}
