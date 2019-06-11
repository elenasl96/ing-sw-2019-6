package model.field;

import java.io.Serializable;

//TODO javadoc
public class Coordinate implements Serializable {
    private char x;
    private int y;

    public Coordinate(){
    }

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
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return this.x+" "+this.y;
    }

    public static Coordinate fillCoordinate(String input) {
        Coordinate coordinate = new Coordinate();
        char letter;
        int number;
        String[] inputSplitted = input.split(" ");
        if(inputSplitted.length !=2 || inputSplitted[0].length()!=1 || inputSplitted[1].length()!=1){
            throw new NumberFormatException();
        } else {
            letter = inputSplitted[0].toUpperCase().charAt(0);
            if (!java.lang.Character.isLetter(letter)) {
                throw new NumberFormatException();
            }
            number = Integer.parseInt(inputSplitted[1]);
            coordinate.x = letter;
            coordinate.y = number;
        }
        return coordinate;
    }
}
