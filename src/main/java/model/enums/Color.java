package model.enums;

public enum Color {
    NONE ("none", 'n'),
    GREY("grey",'l'),
    YELLOW("giallo", 'y'),
    RED("rosso", 'r'),
    BLUE("blu", 'b'),
    GREEN("verde", 'g'),
    WHITE("bianco", 'w'),
    PURPLE("viola", 'p');

    public String color;
    public char abbr;

    Color(String name, char abbr){
        this.abbr = abbr;
        this.color = name;
    }
}
