package model.decks;

import model.enums.WeaponStatus;
import model.moves.Effect;

import java.util.ArrayList;
import java.util.List;

public class Weapon {
    private String name;
    private String effectsDescription;
    private WeaponStatus status;
    private List<Effect> effects = new ArrayList<>();

    public Weapon(String name, String effectsDescription, WeaponStatus status) {
        this.name = name;
        this.effectsDescription = effectsDescription;
        this. status = status;
    }

    public void setNameAndDescription(String name, String effectsDescription) {
        this.name = name;
        this.effectsDescription = effectsDescription;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }

    public List<Effect> getEffects() {
        return effects;
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
}