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
import model.exception.*;
import model.field.Field;
import model.field.Square;
import model.moves.Effect;
import model.moves.Pay;
import model.moves.Target;

import java.util.*;

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

    synchronized String prepareWeapon(Player player, String weaponEffects, int groupID) throws InvalidMoveException {
        String[] weaponEffectsSplitted = weaponEffects.split(" ");
        Weapon weapon = player.getWeapons().get(Integer.parseInt(weaponEffectsSplitted[0]) - 3);
        if (!checkWeaponEffects(weapon, weaponEffectsSplitted, groupID))
            throw new InvalidMoveException("Not valid sequence");
        //Add effects to player
        player.addEffectsToPlay(weaponEffectsSplitted);
        player.setWeaponInUse(weapon.getId());
        //Ask player to fill effects
        return getEffectsLayout(player.getCurrentCardEffects());
    }

    private String getEffectsLayout(List<CardEffect> currentCardEffects) {
        StringBuilder effectsLayout = new StringBuilder();
        for(CardEffect c : currentCardEffects){
            effectsLayout.append(c.getDescription()).append(";");
        }
        return effectsLayout.toString();
    }


    private synchronized boolean checkWeaponEffects(Weapon weapon, String[] weaponEffectsSplitted, int groupID) throws InvalidMoveException {
        //Check if player has the weapon
        int sequenceSize = weaponEffectsSplitted.length - 1;
        EffectType[] sequence = new EffectType[sequenceSize];
        ArrayList<Ammo> ammosToPay = new ArrayList<>();
        if (!weapon.isLoaded())
            throw new InvalidMoveException("Weapon is not loaded");
        //check if the player has enough ammos to pay for effects
        for (int i = 1; i < weaponEffectsSplitted.length; i++) {
            int index = 0;
            CardEffect effect = weapon.getEffectsList().get(Integer.parseInt(weaponEffectsSplitted[i]));
            if (!effect.getCost().isEmpty() && !effect.getEffectType().equals(BASIC))
                ammosToPay.addAll(effect.getCost().subList(index, effect.getCost().size() - 1));
            sequence[i - 1] = effect.getEffectType();
        }
        Pay payEffects = new Pay(ammosToPay);
        payEffects.execute(GameContext.get().getGame(groupID).getCurrentPlayer(), groupID);
        System.out.println(Arrays.toString(sequence));
        //Compare the sequence given with the correct sequences
        if (sequenceSize == 1) {
            return Arrays.equals(sequence, new EffectType[]{BASIC}) ||
                    Arrays.equals(sequence, new EffectType[]{ALTERNATIVE});
        } else if (sequenceSize == 2) {
            return Arrays.equals(sequence, new EffectType[]{BASIC, OPTIONAL}) ||
                    Arrays.equals(sequence, new EffectType[]{BASIC, OPTIONAL1}) ||
                    Arrays.equals(sequence, new EffectType[]{BASIC, BEFORE_AFTER_BASIC}) ||
                    Arrays.equals(sequence, new EffectType[]{BASIC, EVERY_TIME}) ||
                    Arrays.equals(sequence, new EffectType[]{BASIC, OPTIONAL_VORTEX}) ||
                    Arrays.equals(sequence, new EffectType[]{BEFORE_BASIC, BASIC});
        } else if (sequenceSize == 3) {
            return Arrays.equals(sequence, new EffectType[]{BASIC, OPTIONAL, OPTIONAL}) ||
                    Arrays.equals(sequence, new EffectType[]{BASIC, OPTIONAL1, OPTIONAL2}) ||
                    Arrays.equals(sequence, new EffectType[]{BASIC, OPTIONAL, EVERY_TIME}) ||
                    Arrays.equals(sequence, new EffectType[]{BASIC, EVERY_TIME, OPTIONAL}) ||
                    Arrays.equals(sequence, new EffectType[]{EVERY_TIME, BASIC, OPTIONAL}) ||
                    Arrays.equals(sequence, new EffectType[]{EVERY_TIME, OPTIONAL, BASIC}) ||
                    Arrays.equals(sequence, new EffectType[]{BASIC, BEFORE_AFTER_BASIC, EVERY_TIME}) ||
                    Arrays.equals(sequence, new EffectType[]{BEFORE_AFTER_BASIC, BASIC, EVERY_TIME}) ||
                    Arrays.equals(sequence, new EffectType[]{EVERY_TIME, BEFORE_AFTER_BASIC, BASIC}) ||
                    Arrays.equals(sequence, new EffectType[]{EVERY_TIME, BASIC, BEFORE_AFTER_BASIC}) ||
                    Arrays.equals(sequence, new EffectType[]{BEFORE_AFTER_BASIC, BASIC, OPTIONAL}) ||
                    Arrays.equals(sequence, new EffectType[]{BASIC, BEFORE_AFTER_BASIC, OPTIONAL}) ||
                    Arrays.equals(sequence, new EffectType[]{BEFORE_BASIC, BASIC, OPTIONAL}) ||
                    Arrays.equals(sequence, new EffectType[]{BEFORE_BASIC, BASIC, BEFORE_AFTER_BASIC}) ||
                    Arrays.equals(sequence, new EffectType[]{OPTIONAL, BEFORE_BASIC, BASIC});
        } else return false;
    }

    private String getEffectsToFill(Player player) {
        StringBuilder string = new StringBuilder();
        int numEffect = 0;
        for (CardEffect c : player.getCurrentCardEffects()) {
            for (Effect e : c.getEffects()) {
                string.append(numEffect).append(" | ").append(e.getFieldsToFill()).append("\n");
                numEffect++;
            }
        }

        return string.toString();
    }

    void playWeapon(Player player, String input, int groupID) throws InvalidMoveException {
       playCard(player, input, groupID);
        unloadWeaponInUse(player);
    }

    public void checkDifferentInputs(String[][] effectsMatrix) throws InvalidMoveException {
        HashSet<String> set = new HashSet<>();
        for (String[] array : effectsMatrix) {
            for (String s : array) {
                if (!set.add(s)) {
                    throw new InvalidMoveException("");
                }
            }
        }
    }

    private void unloadWeaponInUse(Player player) {
        for (int i = 0; i < player.getWeapons().size(); i++) {
            if (player.getWeapons().get(i).getId() == player.getWeaponInUse()) {
                player.getWeapons().remove(i);
                player.getWeapons().add(new Weapon().initializeWeapon(player.getWeaponInUse()));
                player.getWeapons().get(i).setStatus(WeaponStatus.UNLOADED);
                break;
            }
        }
        System.out.println(player.getWeapons().get(0).getStatus() + player.getWeapons().get(0).getName());
    }

    //---------------------------------USE POWERUPS-------------------------------------------//

    /**
     * for every powerup the player owns
     * if it is playable at the moment it is added in the return list
     * @return list of powerups the player can play in that moment
     */
    List<Powerup> getPowerupsToPlay(Player player) {
        List<Powerup> powerupsToPlay = new ArrayList<>();
        for(Powerup powerup: player.getPowerups()){
            if(isPlayable(player, powerup))
                powerupsToPlay.add(powerup);
        }
        return powerupsToPlay;
    }

    boolean isPlayable(Player player, Powerup powerup) {
        switch (powerup.getName()){
            case "teleporter": case "newton":
                return true;
            case "targeting scope":
                if(player.getAmmos().isEmpty())
                    return false;
                for(CardEffect c: player.getCurrentCardEffects()){
                    if(c.doesDamage())
                        return true;
                }
                break;
            case "tagback grenade":
                if(player.getPlayerBoard().wasDamaged())
                    return true;
                break;
            default:
                break;
        }
        return false;
    }

    String preparePowerup(Player player, String input, int groupID) throws InvalidMoveException {
        Powerup powerupToPlay;
        try {
            int powerupIndex = Integer.parseInt(input);
            powerupToPlay = getPowerupsToPlay(player).get(powerupIndex);
            ShootController.get().addMoves(player, powerupToPlay);
            //Put the powerup chosen at the beginning of list in player
            player.getPowerups().remove(powerupToPlay);
            player.getPowerups().add(0, powerupToPlay);
            //Ask player to fill effects
            return powerupToPlay.getEffectsLayout();
        }catch (NumberFormatException | IndexOutOfBoundsException e){
            throw new InvalidMoveException("Not valid powerup");
        }
    }


    void addMoves(Player player, Powerup powerupToPlay) {
        for(CardEffect c: powerupToPlay.getEffects()){
            player.getCurrentCardEffects().add(c);
        }
    }

    void playCard(Player player, String input, int groupID) throws InvalidMoveException {
        //Convert input to matrix
        String[][] effectsMatrix = generateMatrix(input);
        //Check if inputs are all different
        checkDifferentInputs(effectsMatrix);
        //fill effect fields with player choices
        fillWithInput(player, effectsMatrix, groupID);
        //fill effects with real targets
        fillFields(player.getCurrentCardEffects(), groupID);
        //execute moves
        for (CardEffect c : player.getCurrentCardEffects()) {
            for (Effect e : c.getEffects()) {
                e.execute(player, groupID);
            }
        }
    }

    void playPowerup(Player player, String input, int groupID) throws InvalidMoveException {
        Powerup powerupToPlay = player.getPowerups().get(0);
        playCard(player, input, groupID);
        GameContext.get().getGame(groupID).getBoard().getPowerupsLeft().discardCard(player, powerupToPlay);
    }

    //---------------------------------GENERAL CHECKS-------------------------------------------//

    private void fillFields(List<CardEffect> currentCardEffects, int groupID) throws NotExistingTargetException {
        List<Effect> effectsToRemove = new ArrayList<>();
        for (CardEffect c : currentCardEffects) {
            for (Effect e : c.getEffects()) {
                if(e.isFilled())
                    e.fillFields(groupID);
                else
                    effectsToRemove.add(e);
            }
            c.getEffects().removeAll(effectsToRemove);
            effectsToRemove.clear();
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

    private void fillWithInput(Player player, String[][] inputMatrix, int groupID) throws InvalidMoveException {
        int index2 = 0;
        for (int i = 0; i < player.getCurrentCardEffects().size(); i++) {
            for (int j = 0; j < player.getCurrentCardEffects().get(i).getEffects().size(); j++) {
                try {
                    Effect e = player.getCurrentCardEffects().get(i).getEffects().get(j);
                    int index = 0;
                    if (!e.getFieldsToFill().isEmpty()){
                        if(index2 >= inputMatrix.length)
                            e.setFieldsToFill(null, index, groupID);
                        else if(e.setFieldsToFill(inputMatrix[index2], index, groupID) > 0)
                            index2++;
                    }
                } catch (IndexOutOfBoundsException d) {
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

    public void checkTarget(Target target, String inputName, int groupID) throws NotExistingTargetException, NotExistingPositionException, InvalidDistanceException, TargetTypeException {
        Target realTarget = target.findRealTarget(inputName, groupID);
        try {
            checkMinDistance(target.getMinDistance(),
                    realTarget.getCurrentPosition(), groupID);
            checkMaxDistance(target.getMaxDistance(), realTarget.getCurrentPosition(), groupID);
        }catch (NullPointerException n){
            //It's a room, continue
        }
        checkTargetType(target, realTarget, groupID);
    }

    public void checkMaxDistance(Integer maxDistance, Square targetPosition, int groupID) throws NotExistingPositionException, InvalidDistanceException {
        Square firstPosition = GameContext.get().getGame(groupID).getCurrentPlayer().getCurrentPosition();
        Field field = GameContext.get().getGame(groupID).getBoard().getField();
        if(maxDistance != null){
            if (maxDistance == 0) {
                if(!targetPosition.equals(firstPosition)) {
                    throw new InvalidDistanceException();
                }
            }else {
                targetPosition.getReachSquares().clear();
                targetPosition.createReachList(maxDistance, targetPosition.getReachSquares(), field);
                if (!targetPosition.getReachSquares().contains(firstPosition))
                    throw new InvalidDistanceException();
            }
        }
    }

    public void checkMinDistance(Integer minDistance, Square targetPosition, int groupID) throws InvalidDistanceException, NotExistingPositionException {
        Square firstPosition = GameContext.get().getGame(groupID).getCurrentPlayer().getCurrentPosition();
        targetPosition.getReachSquares().clear();
        if(minDistance != null) {
            targetPosition.createReachList(minDistance - 1, targetPosition.getReachSquares(),
                    GameContext.get().getGame(groupID).getBoard().getField());
            if (targetPosition.getReachSquares().contains(firstPosition)) {
                throw new InvalidDistanceException();
            }
        }
    }

    private void checkTargetType(Target target, Target realTarget, int groupID) throws NotExistingPositionException, NotExistingTargetException, TargetTypeException {
        Player player = GameContext.get().getGame(groupID).getCurrentPlayer();
        switch (target.getTargetType()) {
            case BASIC_VISIBLE:
                Player basic = (Player) player.getBasicTarget(groupID);
                basic.generateVisible(groupID);
                if(!basic.getVisible().contains(realTarget.getCurrentPosition()))
                    throw new TargetTypeException(target.getTargetType());
                break;
            case VISIBLE:
                if(!realTarget.canBeSeen(player, groupID))
                    throw new TargetTypeException(target.getTargetType());
                break;
            case NOT_VISIBLE:
                if (realTarget.canBeSeen(player, groupID))
                    throw new TargetTypeException(target.getTargetType());
                break;
            case MINE:
                if(!realTarget.sameAsMe(groupID)) throw new TargetTypeException(target.getTargetType());
                break;
            case NOT_MINE:
                if(realTarget.sameAsMe(groupID)) throw new TargetTypeException(target.getTargetType());
                break;
            case CARDINAL:
                areCardinal(target, realTarget, groupID);
                break;
            case NONE: default:
                break;
        }
        if(!target.getTargetType().equals(TargetType.MINE) && realTarget.sameAsMe(groupID))
            throw new TargetTypeException(TargetType.NOT_MINE);
    }

    private void areCardinal(Target target, Target realTarget, int groupID) throws NotExistingTargetException, NotExistingPositionException, TargetTypeException {
        Player player = GameContext.get().getGame(groupID).getCurrentPlayer();
        List<Target> realTargets = new ArrayList<>();
        List<Target> cardinalTargets = findCardinalTargets(target, groupID);
        realTargets.add(realTarget);
        for(Target t: cardinalTargets){
            if (target.isFilled())
                realTargets.add(t.findRealTarget(null, groupID));
        }
        for(Target t: realTargets) {
            if(
                    t.getCurrentPosition().getCoord().getX() != player.getCurrentPosition().getCoord().getX() &&
                    t.getCurrentPosition().getCoord().getY() != player.getCurrentPosition().getCoord().getY()
            ) throw new TargetTypeException(target.getTargetType());
        }
        for (int i = 1; i < realTargets.size(); i++) {
            if (
                    realTargets.get(i).getCurrentPosition().getCoord().getX() <
                            player.getCurrentPosition().getCoord().getX() &&
                    realTargets.get(i - 1).getCurrentPosition().getCoord().getX() >
                    player.getCurrentPosition().getCoord().getX()
                            ||
                    realTargets.get(i).getCurrentPosition().getCoord().getX() >
                    player.getCurrentPosition().getCoord().getX() &&
                    realTargets.get(i - 1).getCurrentPosition().getCoord().getX() <
                    player.getCurrentPosition().getCoord().getX()
                            ||
                    realTargets.get(i).getCurrentPosition().getCoord().getY() <
                    player.getCurrentPosition().getCoord().getY() &&
                    realTargets.get(i - 1).getCurrentPosition().getCoord().getY() >
                    player.getCurrentPosition().getCoord().getY()
                            ||
                    realTargets.get(i).getCurrentPosition().getCoord().getY() >
                    player.getCurrentPosition().getCoord().getY() &&
                    realTargets.get(i - 1).getCurrentPosition().getCoord().getY() <
                    player.getCurrentPosition().getCoord().getY()
                )
                throw new TargetTypeException(TargetType.CARDINAL);
        }
    }

    private List<Target> findCardinalTargets(Target target, int groupID) throws TargetTypeException {
        List<Target> targets = new ArrayList<>();
        for(CardEffect c:GameContext.get().getGame(groupID).getCurrentPlayer().getCurrentCardEffects()){
            for(Effect e: c.getEffects()){
               if(e.getTarget().contains(target))
                   c.getEffects().forEach(effect ->
                           effect.getTarget().forEach(target1 -> {
                               if(target1.getTargetType().equals(TargetType.CARDINAL))
                                   targets.add(target1);
                           }));
            }
        }
        if(!targets.isEmpty()) return targets;
        else throw new TargetTypeException(TargetType.CARDINAL);
    }

}
