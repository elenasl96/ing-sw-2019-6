package model.decks;

import model.Ammo;
import model.Player;
import model.enums.WeaponStatus;
import model.moves.Move;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Weapon implements Serializable {
    private String name;
    private String effectsDescription;
    private WeaponStatus status;
    private ArrayList<Move> basicEffect = new ArrayList<>();
    private ArrayList<Ammo> ammoBasic = new ArrayList<>();
    private ArrayList<Move> optionalEffect = new ArrayList<>();
    private ArrayList<Ammo> ammoOptional = new ArrayList<>();
    private ArrayList<Move> alternateFireEffect = new ArrayList<>();
    private ArrayList<Ammo> ammoAlternateFire = new ArrayList<>();

    public Weapon(String name, String effectsDescription, WeaponStatus status) {
    }

    public void setNameAndDescription(String name, String effectsDescription) {
        this.name = name;
        this.effectsDescription = effectsDescription;
    }

    public void setBasicEffect(ArrayList<Move> basicEffect) {
        this.basicEffect = basicEffect;
    }

    public ArrayList<Move> getBasicEffect() {
        return basicEffect;
    }

    public ArrayList<Ammo> getAmmoBasic() {
        return ammoBasic;
    }

    public ArrayList<Move> getOptionalEffect() {
        return optionalEffect;
    }

    public ArrayList<Ammo> getAmmoOptional() {
        return ammoOptional;
    }

    public ArrayList<Move> getAlternateFireEffect() {
        return alternateFireEffect;
    }

    public ArrayList<Ammo> getAmmoAlternateFire() {
        return ammoAlternateFire;
    }

    public void setAmmoBasic(ArrayList<Ammo> ammoBasic) {
        this.ammoBasic = ammoBasic;
    }

    public void setOptionalEffect(ArrayList<Move> optionalEffect) {
        this.optionalEffect = optionalEffect;
    }

    public void setAmmoOptional(ArrayList<Ammo> ammoOptional) {
        this.ammoOptional = ammoOptional;
    }

    public void setAlternateFireEffect(ArrayList<Move> alternateFireEffect) {
        this.alternateFireEffect = alternateFireEffect;
    }

    public void setAmmoAlternateFire(ArrayList<Ammo> ammoAlternateFire) {
        this.ammoAlternateFire = ammoAlternateFire;
    }
}