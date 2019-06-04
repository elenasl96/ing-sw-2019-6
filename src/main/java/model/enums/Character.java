package model.enums;

import java.io.Serializable;

public enum Character implements Serializable {
    NOT_ASSIGNED("NOT_ASSIGNED", Color.NONE, "NOT_ASSIGNED"),
    PG1(":D-STRUCT-OR", Color.YELLOW, "PG1"),
    PG2("BANSHEE", Color.BLUE, "PG2"),
    PG3("DOZER", Color.GREY, "PG3"),
    PG4("VIOLET", Color.PURPLE, "PG4"),
    PG5("SPROG", Color.GREEN, "PG5");

    private String def;

    Character(String name, Color color, String def){
        this.def = def;
    }

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
}
