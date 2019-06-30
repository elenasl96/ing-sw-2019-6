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
    private boolean expired;
    private EffectType effectType;
    private List<Effect> effects = new ArrayList<>();
    private List<Ammo> cost = new ArrayList<>();
    private String description;

    CardEffect(EffectType effectType, Stream<Ammo> ammos, String description){
        if(ammos!=null)
            this.setCost(ammos);
        this.effectType = effectType;
        this.description = description;
        this.expired = false;
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

    public boolean isExpired(){
        return this.expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
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
