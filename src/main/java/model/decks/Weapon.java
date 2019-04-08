package model.decks;

import model.enums.WeaponStatus;

import java.io.Serializable;
import java.util.List;

public class Weapon implements Serializable {
    private String name;
    private List<AmmoTile> ammos;
    private String effectsDescription;
    private WeaponStatus status;

    public Weapon(String name, List<AmmoTile> ammos, String effectsDescription, WeaponStatus status) {
        this.name = name;
        this.ammos = ammos;
        this.effectsDescription = effectsDescription;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AmmoTile> getAmmos() {
        return ammos;
    }

    public void setAmmos(List<AmmoTile> ammos) {
        this.ammos = ammos;
    }

    public String getEffectsDescription() {
        return effectsDescription;
    }

    public void setEffectsDescription(String effectsDescription) {
        this.effectsDescription = effectsDescription;
    }

    public WeaponStatus getStatus() {
        return status;
    }

    public void setStatus(WeaponStatus status) {
        this.status = status;
    }
}
