package model;

import model.enums.Color;

public class Ammo {
    private Color color;

    public Ammo(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
