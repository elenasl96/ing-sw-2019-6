package model.moves;

import model.Ammo;
import model.enums.EffectType;

import java.util.ArrayList;
import java.util.List;

public abstract class Effect {
    protected EffectType type;
    protected Target target;
    protected List<Ammo> cost = new ArrayList<>();

    public Effect(){
    }

    public Effect(EffectType type, Target target){
        this.type = type;
        this.target = target;
    }

    public void setCost(List<Ammo> cost) {
        this.cost = cost;
    }

    public void addCost(Ammo ammo) {
        this.cost.add(ammo);
    }
}
