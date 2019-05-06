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
