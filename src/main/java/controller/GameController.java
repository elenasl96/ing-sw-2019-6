package controller;

import model.decks.CardEffect;
import model.exception.InvalidMoveException;
import model.exception.NotEnoughAmmoException;
import model.Ammo;
import model.GameContext;
import model.Player;
import model.decks.Powerup;
import model.decks.Weapon;
import model.enums.EffectType;
import model.enums.Phase;
import model.enums.WeaponStatus;
import model.field.SpawnSquare;
import model.moves.Effect;
import model.moves.Pay;
import model.moves.Target;
import model.room.Update;

import java.util.*;
import java.util.stream.Collectors;

import static model.enums.EffectType.*;
import static model.enums.Phase.*;

/**
 * SINGLETON (SERVER SIDE)
 * Elaborates the ServerController requests
 * Every method is called receiving the game groupID, to call it from the GameContext
 */

public class GameController{
    private static GameController instance;
    private GameController() {
    }

    public static synchronized GameController get() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    boolean isMyTurn (Player player, int groupID){
        return player.equals(GameContext.get().getGame(groupID).getCurrentPlayer());
    }


    public synchronized Update possibleMoves(Player player, int groupID) {
        StringBuilder content = new StringBuilder();
        switch (player.getPhase()){
            case FIRST: case SECOND:
                content.append("\nThese are the moves you can choose");
                if(!GameContext.get().getGame(groupID).isFinalFrenzy()){
                    content.append("\n||RUN||");
                    if(!player.getCurrentPosition().isEmpty())
                        content.append("\n||GRAB||");
                    if(!player.getWeapons().isEmpty())
                        content.append("\n||SHOOT||").append(player.weaponsToString());
                } else {
                    if(player.isFirstPlayer()){
                        content.append("shoot (move up to 2 squares, reload, shoot)\n" +
                                "grab (move up to 3 squares, grab)");
                    } else {
                        content.append("shoot (move up to 1 squares, reload, shoot)\n" +
                                "run (move up to 4 squares)\n" +
                                "grab (move up to 2 squares, grab)");
                    }
                }
                if(!player.getPowerups().isEmpty())
                    content.append("\n||POWERUPS||").append(player.powerupsToString());
                break;
            case RELOAD:
                content.append("You can reload:\n").append(player.getWeapons());
                break;
            default:
                break;
        }
        return new Update(content.toString(),"updateconsole");
    }

    public synchronized void setSpawn(Player player, int spawn, int groupID){
        //TODO
    }

    public synchronized void setFirstSpawn(Player player, int spawn, int groupID) {
        Powerup discarded;
        if(isMyTurn(player, groupID) &&
                GameContext.get().getGame(groupID).getCurrentPlayer().getPhase().equals(FIRST_SPAWN) &&
                spawn >= 0 &&
                spawn < player.getPowerups().size()){
            //TimerController.get().startTurnTimer(groupID);
            Optional<SpawnSquare> optional = GameContext.get().getGame(groupID).getBoard().getField().getSpawnSquares().stream()
                    .filter(ss -> ss.getColor().equals(GameContext.get().getGame(groupID).getCurrentPlayer().getPowerups().get(spawn).getAmmo().getColor()))
                    .findFirst();
            optional.ifPresent(GameContext.get().getGame(groupID).getCurrentPlayer()::setCurrentPosition);
            discarded = player.getPowerups().remove(spawn);
            //set phase wait to current player and send update
            GameContext.get().getGame(groupID).getCurrentPlayer().setDead(false);
            GameContext.get().getGame(groupID).getCurrentPlayer().setPhase(WAIT);
            GameContext.get().getGame(groupID).getCurrentPlayer().getUser().receiveUpdate(new Update(player, true));
            //go to next player and set phase
            GameContext.get().getGame(groupID).setCurrentPlayer(GameContext.get().getGame(groupID).getPlayers().next());
            System.out.println("CURRENT PLAYER" + GameContext.get().getGame(groupID).getCurrentPlayer());
            GameContext.get().getGame(groupID).sendUpdate(new Update("It's " + GameContext.get().getGame(groupID).getCurrentPlayer()+"'s turn","updateconsole"));
            if(GameContext.get().getGame(groupID).getCurrentPlayer().equals(GameContext.get().getGame(groupID).getPlayers().get(0))) GameContext.get().getGame(groupID).getCurrentPlayer().setPhase(FIRST);
            else GameContext.get().getGame(groupID).getCurrentPlayer().setPhase(FIRST_SPAWN);
            //send updates
            GameContext.get().getGame(groupID).sendUpdate(new Update(
                    "\n>>> Player " + player.getName()+ " discarded:\n" +
                        "==========Powerup========\n"
                        + discarded.toString()
                        +"\n>>> Player " + player.getName() + " spawn in " + player.getCurrentPosition().toString(), "updateconsole"));
            GameContext.get().getGame(groupID).getCurrentPlayer().getUser().receiveUpdate(new Update(GameContext.get().getGame(groupID).getCurrentPlayer(), true));
        } else {
            player.getUser().receiveUpdate(new Update(player, true));
            player.getUser().receiveUpdate(new Update("not working spawn:" + player.toString()+ "," + GameContext.get().getGame(groupID).getCurrentPlayer().toString(),"updateconsole"));
        }
    }

    public synchronized Update getSpawn(Player player, int groupID) {
        if(!player.isDead()){
            player.getPowerups().add(GameContext.get().getGame(groupID)
                    .getBoard().getPowerupsLeft().pickCard());
        }
        player.getPowerups().add(GameContext.get().getGame(groupID)
                .getBoard().getPowerupsLeft().pickCard());
        System.out.println(">>> Powerups picked up: "+player.getPowerups().toString());
        return new Update(" Choose spawn point from:" + player.powerupsToString(), "updateconsole");
    }

    public void updatePhase(int groupID){
        Player player = GameContext.get().getGame(groupID).getCurrentPlayer();
        //go to next player and set phase
        switch(GameContext.get().getGame(groupID).getCurrentPlayer().getPhase()) {
            case FIRST:
                player.getCurrentMoves().clear();
                player.setPhase(Phase.SECOND);
                break;
            case SECOND:
                player.getCurrentMoves().clear();
                player.setPhase(Phase.RELOAD);
                break;
            case RELOAD:
                player.setPhase(WAIT);
                player.getUser().receiveUpdate(new Update(player, true));
                this.updatePoints(groupID);
                GameContext.get().getGame(groupID).setCurrentPlayer(GameContext.get().getGame(groupID).getPlayers().next());
                player = GameContext.get().getGame(groupID).getCurrentPlayer();
                if(player.isDead()){
                    player.setPhase(SPAWN);
                }
                else {
                    player.setPhase(FIRST);
                }
                break;
            case SPAWN:
                if(player.getCurrentPosition()!=null){
                    player.setDead(true);
                    player.setPhase(WAIT);
                } else {
                    player.setPhase(FIRST);
                }
                break;
            default:
                break;
        }
        player.getUser().receiveUpdate(new Update(player, true));
        //move sent, timer Starting
        //TimerController.get().startTurnTimer(groupID);
    }

    private synchronized void updatePoints(int groupID) {
        for(Player p: GameContext.get().getGame(groupID).getPlayers()){
            if(p.isDead()){
                //add the player dead on the killshotTrack
                GameContext.get().getGame(groupID).getBoard().addKillshot(p);
                //overkill: readd the same player and mark the overkiller
                if(p.isOverkilled()){
                    GameContext.get().getGame(groupID).getBoard().addKillshot(p);
                    p.getPlayerBoard().getDamage().get(11).getPlayerBoard().addMarks(p,1);
                }
                //give 1 point to the player who gives the first blood
                p.getPlayerBoard().getDamage().get(0).addPoints(1);
                //decrease the maximum number of points for the killShot of the player
                p.addDeath();
                //counting points from other damages
                Map<Player, Long> playerBoardSorted =  p.getPlayerBoard().getDamage().stream()
                        .collect(Collectors.groupingBy(pm->pm, TreeMap::new,Collectors.counting()));
                int pointsToAdd = 8;
                for (Player pm : playerBoardSorted.keySet()) {
                    pm.addPoints(pointsToAdd);
                    if(pointsToAdd<=2) pointsToAdd=1;
                    else pointsToAdd -= 2;
                }
            }
        }
    }

    public synchronized String prepareWeapon(Player player, String weaponEffects) {
        String[] weaponEffectsSplitted = weaponEffects.split(" ");
            if (!checkWeaponEffects(player, weaponEffectsSplitted))
                throw new InvalidMoveException("Not valid sequence");
            //Add effects to player
            player.addEffectsToPlay(weaponEffectsSplitted);
            //Ask player to fill effects
            return getEffectsToFill(player);
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

    public void playWeapon(Player player, String input, int groupID) {
        //Convert input to matrix
        String[] inputSplitted = input.split(":");
        System.out.println("1");
        String[][] inputMatrix = new String[inputSplitted.length][];
        System.out.println("2");
        for (int i = 0; i < inputSplitted.length; i++) {
            System.out.println("3");
            inputMatrix[i] = inputSplitted[i].split(";");
        }
        System.out.println("4");
        fillEffects(player, inputMatrix, groupID);
        System.out.println("5");
        //execute moves
        /*for(CardEffect c:player.getCurrentCardEffects()){
            for(Effect e: c.getEffects()){
                e.execute(player, groupID);
            }
        }*/
        //fill effect fields with player choices
    }

    private void fillEffects(Player player, String[][] inputMatrix, int groupID) {
        int counter = 0;
        int counter2 = 0;
        for(CardEffect c: player.getCurrentCardEffects()){
            for (Effect e : c.getEffects()) {
                try {
                    for (Target t : e.getTarget()) {
                        checkTarget(t, inputMatrix[counter][counter2], groupID);
                        counter2++;
                        e.fillFields(inputMatrix[counter], groupID);
                    }
                }catch(NullPointerException | InvalidMoveException d){
                    if(e.getOptionality()) break;
                    else throw d;
                }
            }
        }
    }

    private void checkTarget(Target target, String inputName, int groupID) {
        Target player = GameContext.get().getGame(groupID).playerFromName(inputName);
        checkMinDistance(target, groupID);
        checkMaxDistance(target, groupID);
        checkTargetType(target, groupID);
    }

    private void checkMaxDistance(Target t, int groupID) {
        if(t.getMaxDistance() != null){
            t.getCurrentPosition().getReachSquares().clear();
            t.getCurrentPosition().createReachList(t.getMaxDistance(), t.getCurrentPosition().getReachSquares(),
                    GameContext.get().getGame(groupID).getBoard().getField());
            if(!t.getCurrentPosition().getReachSquares().contains(GameContext.get().getGame(groupID).getCurrentPlayer().getCurrentPosition()))
                throw new InvalidMoveException("Invalid max distance");
        }
    }

    private void checkMinDistance(Target t, int groupID) {
        if(t.getMinDistance() != null) {
            if (t.getMinDistance() == 0) {
                if(!t.getCurrentPosition()
                        .equals(GameContext.get().getGame(groupID).getCurrentPlayer().getCurrentPosition()))
                    throw new InvalidMoveException("Invalid distance");
            }else{
                t.getCurrentPosition().createReachList(t.getMinDistance() - 1, t.getCurrentPosition().getReachSquares(),
                        GameContext.get().getGame(groupID).getBoard().getField());
                if (t.getCurrentPosition().getReachSquares().contains(GameContext.get().getGame(groupID).getCurrentPlayer().getCurrentPosition()))
                    throw new InvalidMoveException("Invalid distance");
            }
        }
    }

    private void checkTargetType(Target target, int groupID) {
        Player player = GameContext.get().getGame(groupID).getCurrentPlayer();
        player.generateVisible(groupID);
        switch (target.getTargetType()) {
            case VISIBLE:
                if (!player.getVisible().contains(target.getCurrentPosition()))
                    throw new InvalidMoveException("Not visible target");
                break;
            case NOT_VISIBLE:
                if (player.getVisible().contains(target.getCurrentPosition()))
                    throw new InvalidMoveException("Not not visible target");
                break;
            case NONE:
            case ME:
                break;
            default:
                break;
        }
    }

    private synchronized boolean checkWeaponEffects(Player player, String[] weaponEffectsSplitted) {
        //Check if player has the weapon
        Weapon weapon = player.getWeapons().get(Integer.parseInt(weaponEffectsSplitted[0]) - 3);
        int sequenceSize = weaponEffectsSplitted.length - 1;
        EffectType[] sequence = new EffectType[sequenceSize];
        if(!weapon.isLoaded())
            throw new InvalidMoveException("Weapon is not loaded");
        //Check if effects are in correct order -- generate sequence array of effectTypes
        for(int i=1; i<weaponEffectsSplitted.length; i++){
            sequence[i-1] = weapon.getEffectsList().get(Integer.parseInt(weaponEffectsSplitted[i])).getEffectType();
        }
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

    public void playPowerup(int groupId, Player player, Powerup powerup){
        //TODO
    }



    public List<Weapon> getWeaponToReload(Player player) {
        return player.getWeapons()
                .stream().filter(w -> !w.getStatus().equals(WeaponStatus.LOADED))
                .collect(Collectors.toList());
      /*  } else {
            player.getUser().receiveUpdate(
                    new Update("You can reload these weapons: " + weaponsToReload.toString()));
            return true;
        }*/
    }

    public void reloadWeapon (int number, int groupID) {
        //Check if the player has the necessary ammos
        Player player = GameContext.get().getGame(groupID).getCurrentPlayer();
        Weapon weapon = player.getWeapons().get(number);
        List<Ammo> ammosToPay = new ArrayList<>();
        int i=0;
        if(weapon.getStatus().equals(WeaponStatus.PARTIALLY_LOADED)) {
            i++;
        }
            for(; i<weapon.getEffectsList().get(0).getCost().size(); i++){
                ammosToPay.add(weapon.getEffectsList().get(0).getCost().get(i));
            }

        //check if the player has enough ammos to reload the weapon
        Pay pay = new Pay(ammosToPay);
        try{
            pay.execute(player, groupID);
            Update update = new Update("Weapon reloaded!\nYou have these ammos: "
                    + player.getAmmos().toString(),"reload");
            update.setData(player.getAmmos().toString().replaceAll("[ ]","")
                    .replaceAll(" ","").toLowerCase());
            player.getUser().receiveUpdate(update);
        }catch (NotEnoughAmmoException e){
            e.getMessage();
            player.getUser().receiveUpdate(new Update(e.getMessage(),"updateconsole"));
        }
        weapon.setStatus(WeaponStatus.LOADED);
    }
}
