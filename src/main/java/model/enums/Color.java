package model.enums;

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

    public String getName(){
        return name;
    }
}
