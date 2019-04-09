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

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Ammo)) {
            return false;
        }
        Ammo ammo = (Ammo) obj;
        return ammo.color.equals(this.color);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Ammo clone() {
        return new Ammo(color);
    }
}
