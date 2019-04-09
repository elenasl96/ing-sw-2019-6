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

    /**
     * Check if the given letter is in the collection
     * @param letter
     * @return
     */
    /*
    public static Color found(char letter) {
        for (Color c : Color.values()) {
            if (c.abbr == letter) {
                return c;
            }
        }
        return null;
    }
    */

}
