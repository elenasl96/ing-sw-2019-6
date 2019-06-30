package model.decks;


import model.Ammo;
import model.enums.EffectType;
import model.moves.Effect;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The effect of Weapons and Powerups
 */
public class CardEffect {
    private EffectType effectType;
    private List<Effect> effects = new ArrayList<>();
    private List<Ammo> cost = new ArrayList<>();
    private String description;

    CardEffect(EffectType effectType, Stream<Ammo> ammos, String description){
        if(ammos!=null)
            this.setCost(ammos);
        this.effectType = effectType;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    private void setCost(Stream<Ammo> ammos) {
        this.cost = ammos.collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Ammo> getCost() {
        return cost;
    }

    /**
     * @return true if any of the effects of this card does Damage
     */
    public boolean doesDamage() {
        for(Effect e: getEffects()){
            if(e.doesDamage())
                return true;
        }
        return false;
    }
}
