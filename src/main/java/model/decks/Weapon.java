package model.decks;

import model.Ammo;
import model.enums.EffectType;
import model.enums.WeaponStatus;
import model.moves.Effect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Weapon implements Serializable {
    private String name;
    private String effectsDescription;
    private WeaponStatus status;
    private transient List<CardEffect> cardEffectList = new ArrayList<>();

    public Weapon(String name, String effectsDescription, WeaponStatus status) {
        this.name = name;
        this.effectsDescription = effectsDescription;
        this. status = status;
    }

    public void setNameAndDescription(String name, String effectsDescription) {
        this.name = name;
        this.effectsDescription = effectsDescription;
    }

    public void setEffectsCard(List<CardEffect> cardEffectCard) {
        this.cardEffectList = cardEffectCard;
    }

    public List<CardEffect> getEffectsList() {
        return cardEffectList;
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

    public void setStatus(WeaponStatus status){
        this.status = status;
    }

    @Override
    public String toString(){
        int cost = 0;
        StringBuilder string = new StringBuilder(
                "\nName: " + name
        );
        for(CardEffect e: cardEffectList){
            string.append("\nCost effect ").append(cost).append(": ");
            if(!e.getCost().isEmpty()){
                for(Ammo a: e.getCost()){
                    string.append(a);
                }
            }
            cost ++;
        }
        string.append("\nAmmo status: ")
                .append(status.toString())
                .append("\n=========================");
        return  string.toString();
    }
}

