package model.enums;

import network.commands.response.MoveUpdateResponse;

import java.io.Serializable;

public enum Character implements Serializable {
    NOT_ASSIGNED("NOT_ASSIGNED", Color.NONE, "NOT_ASSIGNED",0),
    PG1(":D-STRUCT-OR", Color.YELLOW, "PG1",1),
    PG2("BANSHEE", Color.BLUE, "PG2",2),
    PG3("DOZER", Color.GREY, "PG3",3),
    PG4("VIOLET", Color.PURPLE, "PG4",4),
    PG5("SPROG", Color.GREEN, "PG5",5);

    private String def;
    private int num;
    private Color color;

    Character(String name, Color color, String def, int num){
        this.def = def;
        this.num = num;
        this.color = color;
    }

    /**
     * Returns the character corresponding to the integer.
     * It's used client side to pass the a number instead of a character type object, for serialization.
     * @see controller.ClientController#handle(MoveUpdateResponse)
     * @param x   the integer
     * @return    the character
     */
    public static Character fromInteger(int x) {
        switch(x) {
            case 1:
                return PG1;
            case 2:
                return PG2;
            case 3:
                return PG3;
            case 4:
                return PG4;
            case 5:
                return PG5;
            default:
                return NOT_ASSIGNED;
        }
    }

    @Override
    public String toString(){
        return this.def;
    }

    public int getNum() {
        return num;
    }

    public char getColor() {
        return color.getAbbr();
    }
}
