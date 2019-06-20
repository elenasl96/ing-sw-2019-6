package controller;

import model.Ammo;
import model.GameContext;
import model.Player;
import model.decks.CardEffect;
import model.decks.Powerup;
import model.decks.Weapon;
import model.enums.EffectType;
import model.enums.TargetType;
import model.enums.WeaponStatus;
import model.exception.InvalidMoveException;
import model.field.Field;
import model.field.Square;
import model.moves.Effect;
import model.moves.Pay;
import model.moves.Target;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static model.enums.EffectType.*;
import static model.enums.EffectType.BASIC;

public class ShootController {
    private static ShootController instance;

    public static synchronized ShootController get() {
        if (instance == null) {
            instance = new ShootController();
        }
        return instance;
    }

    //-------------------------------------USE WEAPON-------------------------------------//

    synchronized String prepareWeapon(Player player, String weaponEffects, int groupID) {
        String[] weaponEffectsSplitted = weaponEffects.split(" ");
        Weapon weapon = player.getWeapons().get(Integer.parseInt(weaponEffectsSplitted[0]) - 3);
        if (!checkWeaponEffects(weapon, weaponEffectsSplitted, groupID))
            throw new InvalidMoveException("Not valid sequence");
        //Add effects to player
        player.addEffectsToPlay(weaponEffectsSplitted);
        player.setWeaponInUse(weapon.getId());
        //Ask player to fill effects
        return getEffectsToFill(player);
    }

    private synchronized boolean checkWeaponEffects(Weapon weapon, String[] weaponEffectsSplitted, int groupID) {
        //Check if player has the weapon
        int sequenceSize = weaponEffectsSplitted.length - 1;
        EffectType[] sequence = new EffectType[sequenceSize];
        ArrayList<Ammo> ammosToPay = new ArrayList<>();
        if(!weapon.isLoaded())
            throw new InvalidMoveException("Weapon is not loaded");
        //check if the player has enough ammos to pay for effects
        for(int i=1; i<weaponEffectsSplitted.length; i++){
            int index = 0;
            CardEffect effect = weapon.getEffectsList().get(Integer.parseInt(weaponEffectsSplitted[i]));
            if(!effect.getCost().isEmpty() && !effect.getEffectType().equals(BASIC))
                ammosToPay.addAll(effect.getCost().subList(index, effect.getCost().size()-1));
            sequence[i-1] = effect.getEffectType();
        }
        Pay payEffects = new Pay(ammosToPay);
        payEffects.execute(GameContext.get().getGame(groupID).getCurrentPlayer(), groupID);
        System.out.println(Arrays.toString(sequence));
        //Compare the sequence given with the correct sequences
        if(sequenceSize == 1){
            return Arrays.equals(sequence, new EffectType[]{BASIC}) ||
                    Arrays.equals(sequence, new EffectType[]{ALTERNATIVE});
        } else if(sequenceSize == 2){
            return Arrays.equals(sequence, new EffectType[]{BASIC, OPTIONAL}) ||
                    Arrays.equals(sequence, new EffectType[]{BASIC, OPTIONAL1}) ||
                    Arrays.equals(sequence, new EffectType[]{BASIC, BEFORE_AFTER_BASIC}) ||
                    Arrays.equals(sequence, new EffectType[]{BASIC, EVERY_TIME}) ||
                    Arrays.equals(sequence, new EffectType[]{BASIC, OPTIONAL_VORTEX}) ||
                    Arrays.equals(sequence, new EffectType[]{BEFORE_BASIC, BASIC});
        } else if(sequenceSize == 3){
            return Arrays.equals(sequence, new EffectType[]{BASIC, OPTIONAL, OPTIONAL})||
                    Arrays.equals(sequence, new EffectType[]{BASIC, OPTIONAL1, OPTIONAL2})||
                    Arrays.equals(sequence, new EffectType[]{BASIC, OPTIONAL, EVERY_TIME})||
                    Arrays.equals(sequence, new EffectType[]{BASIC, EVERY_TIME, OPTIONAL})||
                    Arrays.equals(sequence, new EffectType[]{EVERY_TIME, BASIC, OPTIONAL})||
                    Arrays.equals(sequence, new EffectType[]{EVERY_TIME, OPTIONAL, BASIC})||
                    Arrays.equals(sequence, new EffectType[]{BASIC, BEFORE_AFTER_BASIC, EVERY_TIME})||
                    Arrays.equals(sequence, new EffectType[]{BEFORE_AFTER_BASIC, BASIC, EVERY_TIME })||
                    Arrays.equals(sequence, new EffectType[]{EVERY_TIME, BEFORE_AFTER_BASIC, BASIC})||
                    Arrays.equals(sequence, new EffectType[]{EVERY_TIME, BASIC, BEFORE_AFTER_BASIC})||
                    Arrays.equals(sequence, new EffectType[]{BEFORE_BASIC, BASIC, OPTIONAL})||
                    Arrays.equals(sequence, new EffectType[]{OPTIONAL, BEFORE_BASIC, BASIC});
        } else return false;
    }

    private String getEffectsToFill(Player player) {
        StringBuilder string = new StringBuilder();
        int numEffect = 0;
        for(CardEffect c: player.getCurrentCardEffects()){
            for(Effect e: c.getEffects()){
                string.append(numEffect).append(" | ").append(e.getFieldsToFill()).append("\n");
                numEffect++;
            }
        }

        return string.toString();
    }

    void playWeapon(Player player, String input, int groupID) {
        //Convert input to matrix
        String[][] effectsMatrix = generateMatrix(input);
        //Check if inputs are all different
        checkDifferentInputs(effectsMatrix);
        //fill effect fields with player choices
        fillWithInput(player, effectsMatrix, groupID);
        //fill effects with real targets
        fillFields(player.getCurrentCardEffects(), groupID);
        //execute moves
        for(CardEffect c:player.getCurrentCardEffects()){
            for(Effect e: c.getEffects()){
                e.execute(player, groupID);
            }
        }
        unloadWeaponInUse(player);
    }

    public void checkDifferentInputs(String[][] effectsMatrix) {
        HashSet<String> set = new HashSet<>();
        for (String[] array : effectsMatrix) {
            for(String s: array ){
                if (! set.add(s)) {
                    throw new InvalidMoveException("");
                }
            }
        }
    }

    private void unloadWeaponInUse(Player player) {
        for(int i=0; i<player.getWeapons().size(); i++){
            if(player.getWeapons().get(i).getId() == player.getWeaponInUse()) {
               player.getWeapons().remove(i);
               player.getWeapons().add(new Weapon().initializeWeapon(player.getWeaponInUse()));
               player.getWeapons().get(i).setStatus(WeaponStatus.UNLOADED);
               break;
            }
        }
        System.out.println(player.getWeapons().get(0).getStatus()+player.getWeapons().get(0).getName());
    }

    //---------------------------------USE POWERUPS-------------------------------------------//


    public String preparePowerup(Player player, Powerup powerup, int groupID){
        //TODO TEST AND CHECK IMPLEMENTATION
        //Add effects to player
        player.getCurrentMoves().add(powerup.getMoves().get(0));
        //Discard powerup
        GameContext.get().getGame(groupID).getBoard().getPowerupsLeft().discardCard(powerup);
        //Ask player to fill effects
        return getEffectsToFill(player);
    }

    //---------------------------------GENERAL CHECKS-------------------------------------------//

    private void fillFields(List<CardEffect> currentCardEffects, int groupID) {
        for(CardEffect c: currentCardEffects){
            for(Effect e: c.getEffects()){
                e.fillFields(groupID);
            }
        }
    }

    String[][] generateMatrix(String input) {
        String[] inputSplitted = input.split(";");
        String[][] inputMatrix = new String[inputSplitted.length][];
        for (int i = 0; i < inputSplitted.length; i++) {
            inputMatrix[i] = inputSplitted[i].split(",");
        }
        return inputMatrix;
    }

    private void fillWithInput(Player player, String[][] inputMatrix, int groupID) {
        int index2=0;
        for(int i=0; i<player.getCurrentCardEffects().size(); i++){
            for (int j=0; j<player.getCurrentCardEffects().get(i).getEffects().size(); j++) {
                try {
                    Effect e =player.getCurrentCardEffects().get(i).getEffects().get(j);
                    int index=0;
                    if(!e.getFieldsToFill().isEmpty() &&
                            e.setFieldsToFill(inputMatrix[index2], index, groupID) > 0)
                        index2++;
                } catch (NullPointerException | IndexOutOfBoundsException d) {
                    if(i < player.getCurrentCardEffects().size() - 1)
                        throw new InvalidMoveException("Not enough inputs");
                    if(!player.getCurrentCardEffects().get(i).getEffects().get(j).getOptionality())
                        throw new InvalidMoveException("Not enough inputs");
                    for(int k=j; k<player.getCurrentCardEffects().get(i).getEffects().size(); k++){
                        player.getCurrentCardEffects().get(i).getEffects().remove(k);
                        //  if(!player.getCurrentCardEffects().get(i).getEffects().get(i).getOptionality()) throw d;
                    }
                }
            }
        }
    }

    public void checkTarget(Target target, String inputName, int groupID) {
        Target realTarget = target.findRealTarget(inputName, groupID);
        checkMinDistance(target.getMinDistance(), realTarget.getCurrentPosition(), groupID);
        checkMaxDistance(target.getMaxDistance(), realTarget.getCurrentPosition(), groupID);
        checkTargetType(target, realTarget, groupID);
    }

    public void checkMaxDistance(Integer maxDistance, Square targetPosition, int groupID) {
        Square firstPosition = GameContext.get().getGame(groupID).getCurrentPlayer().getCurrentPosition();
        Field field = GameContext.get().getGame(groupID).getBoard().getField();
        if(maxDistance != null){
            if (maxDistance == 0) {
                if(!targetPosition.equals(firstPosition)) {
                    throw new InvalidMoveException("Invalid distance");
                }
            }else {
                targetPosition.getReachSquares().clear();
                targetPosition.createReachList(maxDistance, targetPosition.getReachSquares(), field);
                if (!targetPosition.getReachSquares().contains(firstPosition))
                    throw new InvalidMoveException("Invalid max distance");
            }
        }
    }

    public void checkMinDistance(Integer minDistance, Square targetPosition, int groupID) {
        Square firstPosition = GameContext.get().getGame(groupID).getCurrentPlayer().getCurrentPosition();
        targetPosition.getReachSquares().clear();
        if(minDistance != null) {
            targetPosition.createReachList(minDistance - 1, targetPosition.getReachSquares(),
                    GameContext.get().getGame(groupID).getBoard().getField());
            if (targetPosition.getReachSquares().contains(firstPosition)) {
                throw new InvalidMoveException("Invalid distance");
            }
        }
    }

    private void checkTargetType(Target target, Target realTarget, int groupID) {
        Player player = GameContext.get().getGame(groupID).getCurrentPlayer();
        player.generateVisible(groupID);
        switch (target.getTargetType()) {
            case BASIC_VISIBLE:
                Player basic = (Player) player.getBasicTarget(groupID);
                basic.generateVisible(groupID);
                if(!basic.getVisible().contains(realTarget.getCurrentPosition()))
                    throw new InvalidMoveException("Target is not basic visible");
                break;
            case VISIBLE:
                if (!player.getVisible().contains(realTarget.getCurrentPosition()))
                    throw new InvalidMoveException("Not visible target");
                break;
            case NOT_VISIBLE:
                if (player.getVisible().contains(realTarget.getCurrentPosition()))
                    throw new InvalidMoveException("Not not visible target");
                break;
            case MINE:
                if(!realTarget.sameAsMe(groupID)) throw new InvalidMoveException("You must use yourself");
                break;
            case NOT_MINE:
                if(realTarget.sameAsMe(groupID)) throw new InvalidMoveException("You can't use yours");
                break;
            case NONE: default:
                break;
        }
        if(!target.getTargetType().equals(TargetType.MINE) && realTarget.sameAsMe(groupID))
            throw new InvalidMoveException("You can't use yourself");
    }

}
