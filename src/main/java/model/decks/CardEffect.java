package model.decks;

import model.Ammo;
import model.enums.EffectType;
import model.moves.Effect;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CardEffect {
    private EffectType effectType;
    private List<Effect> effects = new ArrayList<>();
    private ArrayList<Ammo> cost;

    CardEffect(EffectType effecType, Stream<Ammo> ammos){
        if(ammos!=null)
            this.setCost(ammos);
        this.effectType = effecType;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    public void setEffectType(EffectType effectType) {
        this.effectType = effectType;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }

    public void setCost(ArrayList<Ammo> cost) {
        this.cost = cost;
    }

    void setCost(Stream<Ammo> ammos) {
        this.cost = ammos.collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Ammo> getCost() {
        return cost;
    }
}
