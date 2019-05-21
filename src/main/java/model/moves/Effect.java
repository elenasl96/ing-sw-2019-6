package model.moves;

import model.Ammo;
import model.enums.EffectType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Effect implements Serializable {
    private EffectType type;
    private Boolean optionality;
    private Boolean different;
    List<Target> targets = new ArrayList<>();
    private List<Ammo> cost = new ArrayList<>();

    public Effect(){
    }

    public Effect(EffectType type, List<Target> targets, Boolean optionality, Boolean notChosen){
        this.type = type;
        this.targets = targets;
        this.optionality = optionality;
        this.different = notChosen;
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
}

