package model.enums;

/**
 * One color for each possible character or room color
 */
public enum Color {
    NONE ("none", 'n'),
    GREY("grey",'l'),
    YELLOW("yellow", 'y'),
    RED("red", 'r'),
    BLUE("blue", 'b'),
    GREEN("green", 'g'),
    WHITE("white", 'w'),
    PURPLE("purple", 'p');

    private String name;
    private char abbr;

    Color(String name, char abbr){
        this.name = name;
        this.abbr = abbr;
    }

    public static Color fromName(String name) {
        switch(name) {
            case "green":
                return GREEN;
            case "blue":
                return BLUE;
            case "red":
                return RED;
            case "yellow":
                return YELLOW;
            case "white":
                return WHITE;
            default:
                return NONE;
        }
    }

    public String getName(){
        return name;
    }

    public char getAbbr() {
        return abbr;
    }

    public boolean equalsTo(Color color){
        return this.name.equals(color.name);
    }
}
