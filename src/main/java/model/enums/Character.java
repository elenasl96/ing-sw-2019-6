package model.enums;

public enum Character {
    NOT_ASSIGNED("NOT_ASSIGNED", Color.NONE),
    PG1(":D-STRUCT-OR", Color.YELLOW),
    PG2("BANSHEE", Color.BLUE),
    PG3("DOZER", Color.GREY),
    PG4("VIOLET", Color.PURPLE),
    PG5("SPROG", Color.GREEN);

    private String name;
    private Color color;

    Character(String name, Color color){
        this.name = name;
        this.color = color;
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
}
