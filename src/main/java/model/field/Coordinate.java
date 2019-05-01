package model.field;

public class Coordinate {
    private final char x;
    private final int y;

    public Coordinate(char x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate coordinateCast = (Coordinate) o;

        return (coordinateCast.x == this.x && coordinateCast.y == this.y);
    }


    @Override
    public String toString() {
        return "["+this.x+" "+this.y+"]";
    }
}
