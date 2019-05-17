package model.moves;

import model.Ammo;
import model.enums.EffectType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Effect implements Serializable {
    protected EffectType type;
    protected Boolean optionality;
    protected Boolean different;
    protected List<Target> targets = new ArrayList<>();
    protected List<Ammo> cost = new ArrayList<>();

    public Effect(){
    }

    public Effect(EffectType type, List<Target> targets, Boolean optionality, Boolean notChosen){
        this.type = type;
        this.targets = targets;
        this.optionality = optionality;
        this.different = notChosen;
    }

    public EffectType getType(){
        return this.type;
    }

    public Boolean getOptionality(){
        return this.optionality;
    }

    public List<Target> getTarget(){
        return this.targets;
    }

    public List<Ammo> getCost(){
        return this.cost;
    }

    public void setCost(List<Ammo> cost) {
        this.cost = cost;
    }

    public void addCost(Ammo ammo) {
        this.cost.add(ammo);
    }
}
