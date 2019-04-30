package model.field;

public class Coordinate {
    private final char x;
    private final int y;

    public Coordinate(char x, int y){
        this.x = x;
        this.y = y;
    }



    @Override
    public String toString() {
        return "["+this.x+" "+this.y+"]";
    }
}
