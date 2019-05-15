package model.moves;

import model.Ammo;
import model.enums.EffectType;

import java.util.ArrayList;

public abstract class Effect {
    protected EffectType type;
    protected Target target;
    protected ArrayList<Ammo> cost = new ArrayList<>();

    public Effect(){

    }

    public Effect(EffectType type, Target target){
        this.type = type;
        this.target = target;
    }

    public ArrayList<Ammo> getCost() {
        return cost;
    }

    public void addCost(Ammo ammo) {
        this.cost.add(ammo);
    }
}
