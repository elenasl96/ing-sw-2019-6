package model.decks;

import model.Ammo;
import model.enums.EffectType;
import model.moves.Effect;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CardEffect {
    private boolean done;
    private EffectType effectType;
    private List<Effect> effects = new ArrayList<>();
    private ArrayList<Ammo> cost;

    CardEffect(EffectType effecType){
        done = false;
        this.effectType = effecType;
    }

    List<Effect> getEffects() {
        return effects;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }

    void setCost(Stream<Ammo> ammos) {
        this.cost = ammos.collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Ammo> getCost() {
        return cost;
    }
}
