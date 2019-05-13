package model.decks;

import model.Ammo;
import model.enums.WeaponStatus;
import model.moves.Move;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Weapon implements Serializable {
    private String name;
    private String effectsDescription;
    private WeaponStatus status;
    private List<Move> basicEffect = new ArrayList<>();
    private List<Ammo> ammoBasic = new ArrayList<>();
    private List<Move> optionalEffect = new ArrayList<>();
    private List<Ammo> ammoOptional = new ArrayList<>();
    private List<Move> alternateFireEffect = new ArrayList<>();
    private List<Ammo> ammoAlternateFire = new ArrayList<>();

    public Weapon(String name, String effectsDescription, WeaponStatus status) {
        this.name = name;
        this.effectsDescription = effectsDescription;
        this. status = status;
    }

    public void setNameAndDescription(String name, String effectsDescription) {
        this.name = name;
        this.effectsDescription = effectsDescription;
    }

    public void setBasicEffect(List<Move> basicEffect) {
        this.basicEffect = basicEffect;
    }

    public List<Move> getBasicEffect() {
        return basicEffect;
    }

    public List<Ammo> getAmmoBasic() {
        return ammoBasic;
    }

    public List<Move> getOptionalEffect() {
        return optionalEffect;
    }

    public List<Ammo> getAmmoOptional() {
        return ammoOptional;
    }

    public List<Move> getAlternateFireEffect() {
        return alternateFireEffect;
    }

    public List<Ammo> getAmmoAlternateFire() {
        return ammoAlternateFire;
    }

    public String getName() {
        return name;
    }

    public String getEffectsDescription() {
        return effectsDescription;
    }

    public WeaponStatus getStatus() {
        return status;
    }

    public void setAmmoBasic(List<Ammo> ammoBasic) {
        this.ammoBasic = ammoBasic;
    }

    public void setOptionalEffect(List<Move> optionalEffect) {
        this.optionalEffect = optionalEffect;
    }

    public void setAmmoOptional(List<Ammo> ammoOptional) {
        this.ammoOptional = ammoOptional;
    }

    public void setAlternateFireEffect(List<Move> alternateFireEffect) {
        this.alternateFireEffect = alternateFireEffect;
    }

    public void setAmmoAlternateFire(List<Ammo> ammoAlternateFire) {
        this.ammoAlternateFire = ammoAlternateFire;
    }
}