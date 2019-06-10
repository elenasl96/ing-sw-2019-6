package model;

import model.enums.Color;

import java.io.Serializable;

/**
 * Class for the ammos of the game
 */
public class Ammo implements Serializable {
    /**
     * The color of the ammo
     */
    private Color color;

    /**
     * Constructor
     * @param color the color of the ammo
     */
    public Ammo(Color color){
        this.color = color;
    }


    public Color getColor() {
        return color;
    }

    @Override
    public String toString(){
        return  this.getColor().getName() + " ";
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

    /**
     * Method that returns a new Ammo of the same color of this ammo object.
     * @return  a new Ammo, same color of this one
     */
    Ammo cloneAmmo() {
        return new Ammo(color);
    }
}
