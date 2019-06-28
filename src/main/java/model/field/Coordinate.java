package model.field;

import java.io.Serializable;

public class Coordinate implements Serializable {
    /**
     * Column in letters, from A to D
     */
    private char x;
    /**
     * Row in numbers from 0 to 2
     */
    private int y;

    public Coordinate(){
    }

    public Coordinate(char x, int y){
        this.x = x;
        this.y = y;
    }

    public char getX() {
        return x;
    }

    public int getY() {
        return y;
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

    /**
     * @param input the coordinate in String format
     * @return  the corresponding coordinate
     */
    public static Coordinate fillCoordinate(String input) {
        Coordinate coordinate = new Coordinate();
        char letter;
        int number;
        String[] inputSplit = input.split(" ");
        if(inputSplit.length !=2 || inputSplit[0].length()!=1 || inputSplit[1].length()!=1){
            throw new NumberFormatException();
        } else {
            letter = inputSplit[0].toUpperCase().charAt(0);
            if (!java.lang.Character.isLetter(letter)) {
                throw new NumberFormatException();
            }
            number = Integer.parseInt(inputSplit[1]);
            coordinate.x = letter;
            coordinate.y = number;
        }
        return coordinate;
    }
}
