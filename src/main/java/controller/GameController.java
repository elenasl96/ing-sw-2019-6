package controller;

import model.decks.CardEffect;
import model.decks.Powerup;
import model.decks.PowerupDeck;
import model.enums.*;
import model.exception.InvalidMoveException;
import model.exception.NotEnoughAmmoException;
import model.Ammo;
import model.GameContext;
import model.Player;
import model.decks.Weapon;
import model.exception.NotExistingPositionException;
import model.field.SpawnSquare;
import model.moves.Pay;
import model.room.Update;

import java.util.*;
import java.util.stream.Collectors;

import static model.enums.Phase.*;

/**
 * SINGLETON (SERVER SIDE)
 * Elaborates the ServerController requests
 * Every method is called receiving the game groupID, to call it from the GameContext
 */

public class GameController{
    private static GameController instance;
    private static final String UPDATECONSOLE = "updateconsole";
    private static final String GRAB = "GRAB";
    private static final String SHOOT = "SHOOT";

    private GameController() {
    }

    public static synchronized GameController get() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    /**
     *
     * @param cards a list of card (weapons or powerups)
     * @param index id number of first card
     * @return a string of cards ordered by id
     */

    public static String cardsToString(List cards, int index) {
        StringBuilder cardsString = new StringBuilder();
        for(Object c : cards){
            cardsString.append("\nID: ").append(index).append(c.toString());
            index ++;
        }
        return cardsString.toString();
    }

    static String powerupToStringForGUI(List<Powerup> cards) {
        StringBuilder cardsString = new StringBuilder();
        for(Powerup c : cards){
            cardsString.append(c.getName()).append(c.getAmmo().getColor().getAbbr()).append(";");
        }
        return cardsString.toString().substring(0,cardsString.toString().length()-1)
                .toLowerCase().replace(" ","");
    }

    boolean isMyTurn (Player player, int groupID){
        return player.equals(GameContext.get().getGame(groupID).getCurrentPlayer());
    }


    /**
     * @param player the player who is asking for moves in first and second phase
     * @param groupID the id of his group
     * @return an update which describes the moves he can play (RUN, GRAB OR SHOOT)
     * @throws InvalidMoveException
     */
    synchronized Update possibleMoves(Player player, int groupID) throws InvalidMoveException {
        StringBuilder content = new StringBuilder();
        if(GameContext.get().getGame(groupID).getCurrentPlayer().isPhaseNotDone() &&
            !player.getCurrentMoves().isEmpty()){
            playPendingMoves(player, groupID);
        }
        else {
            switch (player.getPhase()) {
                case FIRST:
                case SECOND:
                    return firstSecondMoves(player, content, groupID);
                default:
                    break;
            }
            return new Update(content.toString(), UPDATECONSOLE);
        }
        return null;
    }

    private void playPendingMoves(Player player, int groupID) throws InvalidMoveException {
        while(!player.getCurrentMoves().isEmpty()) {
            GameContext.get().getGame(groupID).getCurrentPlayer().getCurrentMoves().get(0)
                    .execute(player, groupID);
            player.getCurrentMoves().remove(0);
        }
    }

    private Update firstSecondMoves(Player player, StringBuilder content, int groupID) {
        Update update;
        content.append("\nThese are the moves you can choose");
        StringBuilder forGui = new StringBuilder();
        if(!GameContext.get().getGame(groupID).isFinalFrenzy()){
            content.append("\n||RUN||");
            forGui.append("RUN;");
            content.append("\n||"+GRAB+"||");
            forGui.append(GRAB+";");
            if(!player.getWeapons().isEmpty()) {
                int index = 3;
                content.append("\n||SHOOT||").append(player.weaponsToString(index));
                forGui.append(SHOOT+";");
            }
        } else {
            if(player.isFirstPlayer()){
                content.append("||shoot || (move up to 2 squares, reload, shoot)\n" +
                        "||grab|| (move up to 3 squares, grab)");
                forGui.append(GRAB+";").append("SHOOT;");
            } else {
                content.append("||shoot|| (move up to 1 squares, reload, shoot)\n" +
                        "||run|| (move up to 4 squares)\n" +
                        "||grab|| (move up to 2 squares, grab)");
                forGui.append("RUN;").append(GRAB+";").append("SHOOT;");
            }
        }
        update = new Update(content.toString(),"disablebutton");
        update.setData(forGui.toString().substring(0,forGui.toString().length()-1));
        return update;
    }

    synchronized void setSpawn(Player player, int spawn, int groupID) throws NotExistingPositionException {
        if(isMyTurn(player, groupID) &&
                player.getPhase().equals(SPAWN) &&
                spawn >= 0 && spawn < player.getPowerups().size()){
            Optional<SpawnSquare> optional = GameContext.get().getGame(groupID).getBoard().getField().getSpawnSquares().stream()
                    .filter(ss -> ss.getColor().equals(player.getPowerups().get(spawn).getAmmo().getColor()))
                    .findFirst();
            optional.ifPresent(player::setCurrentPosition);
            PowerupDeck powerupDeck = GameContext.get().getGame(groupID).getBoard().getPowerupsLeft();
            powerupDeck.discardCard(player, player.getPowerups().get(spawn));
            Update update = new Update(null,"powerup");
            update.setData(player.getPowerups().get(0).getName());
            player.receiveUpdate(update, groupID);
            //send updates
            GameContext.get().getGame(groupID).sendUpdate(new Update("Player " + player.getName() + " spawn in " + player.getCurrentPosition().toString()));
            update = new Update(null,"movement");
            update.setData(player.getCharacter().getNum() + ";" + player.getCurrentPosition().toString().replace("[","").replace("]",""));
            GameContext.get().getGame(groupID).sendUpdate(update);
            updatePhase(groupID);
        } else {
            player.getUser().receiveUpdate(new Update(player, true));
            player.getUser().receiveUpdate(new Update(
                    "not working spawn:" + player.toString()+ "," + player.toString(), UPDATECONSOLE));
        }
    }

    /**
     * get player's powerups to choose the spawn point
     * @param player the player who's asking for spawn
     * @param groupID the id of the player's group
     * @return an update
     */

    synchronized Update getSpawn(Player player, int groupID) {
        player.getPowerups().add(GameContext.get().getGame(groupID)
            .getBoard().getPowerupsLeft().pickCard());
        System.out.println(">>> Powerups picked up: "+player.getPowerups().toString());
        Update update = new Update(" Choose spawn point from: \n========Powerups=========" + cardsToString(player.getPowerups(), 0)  ,"choosecard");
        update.setData(player.getStringIdPowerUp().replaceAll(" ",""));
        return update;
    }

    /**
     * The method is called every time a phase is changing
     * It changes the current player and
     * send an update to the new current player to change his phase locally
     * @param groupID id of the group
     */
    void updatePhase(int groupID){
        Player player = GameContext.get().getGame(groupID).getCurrentPlayer();
        Update update = new Update(null, "timer");
        update.setData("stop");
        player.getUser().receiveUpdate(update);
        //go to next player and set phase
        switch(GameContext.get().getGame(groupID).getCurrentPlayer().getPhase()) {
            case SPAWN:
                //set phase wait to current player and send update
                if(player.isDead()) {
                    player.setDead(false);
                }
                player.setPhase(POWERUP1);
                player.getUser().receiveUpdate(new Update(player, true));
            break;
            case POWERUP1:
                player.getCurrentMoves().clear();
                player.getCurrentCardEffects().clear();
                player.setPhase(FIRST);
                break;
            case FIRST:
                expireCurrentCardEffects(player.getCurrentCardEffects());
                player.setPhase(POWERUP2);
                break;
            case POWERUP2:
                player.getCurrentMoves().clear();
                player.getCurrentCardEffects().clear();
                player.setPhase(SECOND);
                break;
            case SECOND:
                expireCurrentCardEffects(player.getCurrentCardEffects());
                player.setPhase(POWERUP3);
                break;
            case POWERUP3:
                player.getCurrentMoves().clear();
                player.getCurrentCardEffects().clear();
                player.setPhase(RELOAD);
                break;
            case RELOAD:
                player.setPhase(WAIT);
                player.getUser().receiveUpdate(new Update(player, true));
                this.updatePoints(groupID);
                GameContext.get().getGame(groupID).setCurrentPlayer(GameContext.get().getGame(groupID).getPlayers().next());
                player = GameContext.get().getGame(groupID).getCurrentPlayer();
                GameContext.get().getGame(groupID).sendUpdate(new Update("It's "+player.getName()+"'s turn"));
                if(player.isDead()){
                    player.setPhase(SPAWN);
                    player.setCurrentPosition(null);
                }
                else {
                    player.setPhase(POWERUP1);
                }
                break;
            case DISCONNECTED:
                GameContext.get().getGame(groupID).setCurrentPlayer(GameContext.get().getGame(groupID).getPlayers().next());
                updatePhase(groupID);
                break;
            case WAIT:
                if(player.isDead())
                    player.setPhase(SPAWN);
                else
                    player.setPhase(POWERUP1);
                break;
            default:
                break;
        }
        player.getUser().receiveUpdate(new Update(player, true));
        //move sent, timer Starting
        TimerController.get().startTurnTimer(groupID);
    }

    private void expireCurrentCardEffects(List<CardEffect> currentCardEffects) {
        for(CardEffect c: currentCardEffects){
            c.setExpired(true);
        }
    }

    /**
     * The method is called at the end of every players' reload phase;
     * it takes only dead players' boards and count the presence of all players
     * adding points depending on the position on the player board
     * @param groupID id of the group
     */
    private synchronized void updatePoints(int groupID) {
        for(Player p: GameContext.get().getGame(groupID).getPlayers()){
            if(p.isDead() && p.getPlayerBoard()!= null && !p.getPlayerBoard().getDamage().isEmpty() ){
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


    //---------------------------------RELOAD-----------------------------------------------//

    /**
     * the method takes all player's weapons and filter them by the WeaponStatus : LOADED
     * @param player the player who asks for reload
     * @return list of weapons to reload
     */
    List<Weapon> getWeaponToReload(Player player) {
        List<Weapon> weaponsToReload = new ArrayList<>();
        for(Weapon weapon : player.getWeapons()){
            if(weapon.getStatus().equals(WeaponStatus.UNLOADED))
                weaponsToReload.add(weapon);
        }
        return weaponsToReload;
    }

    /**
     * the method takes from the list of the weapons to reload of the player
     * the weapon with index = number
     * @param player player who asked for reload
     * @param number the id number he chose
     * @param groupID id of the player's group
     * @throws NotEnoughAmmoException if the player has not ammos to reload the weapon chosen
     */
    void reloadWeapon (Player player, int number, int groupID) throws NotEnoughAmmoException {
        //Check if the player has the necessary ammos
        Weapon weapon = this.getWeaponToReload(player).get(number);
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
            pay.execute(player, groupID);
            Update update = new Update("Weapon reloaded!\nYou have these ammos: "
                    + player.getAmmos().toString(),"reload");
            update.setData(player.getAmmos().toString().replace("[","").replace("]","")
                    .replace(" ","").toLowerCase());
            player.getUser().receiveUpdate(update);
        weapon.setStatus(WeaponStatus.LOADED);
        updatePhase(groupID);
    }

    public List<Weapon> getWeaponsToShoot(Player player) {
        List<Weapon> weaponsToShoot = new ArrayList<>();
        for(Weapon weapon : player.getWeapons()){
            if(weapon.isLoaded())
                weaponsToShoot.add(weapon);
        }
        return weaponsToShoot;
    }
}
