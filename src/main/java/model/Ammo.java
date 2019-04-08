package model;

import model.enums.Color;

import java.io.Serializable;

public class Ammo implements Serializable {
    private Color color;

    public Ammo(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
