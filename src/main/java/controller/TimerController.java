package controller;

import model.GameContext;
import model.enums.Phase;
import model.exception.NotExistingFieldException;
import model.room.*;
import network.Manager;

import java.util.*;

import static model.enums.Phase.DISCONNECTED;
import static model.enums.Phase.SPAWN;

/**
 * SINGLETON (SERVER SIDE)
 * Handles the timers of all the games
 * @see ModelObserver
 * @see Timer
 * @see TimerTask
 */
public class TimerController implements ModelObserver {
    /**
     * A list of timers
     * Every timer is associated with its groupID being the groupID-th in the list
     */
    private List<Timer> timers;

    private static TimerController instance;
    private TimerController(){
        timers = new ArrayList<>();
    }

    public static synchronized TimerController get() {
        if (instance == null) {
            instance = new TimerController();
        }
        return instance;
    }

    /**
     * Adds itself to the list of observers
     * @param group the group you want to be observed by the timer controller
     */
    public void addGroup(Group group){
        //Observer of every group, starts the timer when called
        group.observe(this);
    }

    /**
     * Starts the waiting timer of the designated group in the waiting room.
     * The task consists in 60 seconds of waiting, with notices at 60, 10 and from 5 to 0 seconds
     * If the group is complete before the timer elapses that the timer gets canceled.
     * If the group passes to less than 3 players the timer is purged, waiting to be rescheduled
     * when there are enough players to play (at least 3).
     * @param groupID   The groupID of the group you want to start the timer of
     * @see Timer
     * @see TimerTask
     */
    synchronized void startTimer(int groupID){
        while(timers.size()<groupID+1){
            timers.add(null);
        }
        this.timers.remove(groupID);
        this.timers.add(groupID, new Timer());
        TimerTask timerTask = new TimerTask(){
            int seconds = 2;
            @Override
            public void run() {
                if(seconds == 60){
                    Manager.get().getGroup(groupID).sendUpdate(new Update("Timer started: " + seconds + "seconds left..."));
                 }else if(seconds == 10) {
                    Manager.get().getGroup(groupID).sendUpdate(new Update("Hurry, 10 seconds left!"));
                } else if (seconds <= 5 && seconds > 0) {
                    Manager.get().getGroup(groupID).sendUpdate(new Update("Seconds remaining: " + seconds + "..."));
                } else if (seconds == 0){
                    Manager.get().getGroup(groupID).sendUpdate(new Update("Game starting"));
                    try {
                        Manager.get().getGroup(groupID).createGame();
                    } catch (NotExistingFieldException e) {
                        System.err.println(e.getMessage());
                    }
                    timers.get(groupID).purge();
                }
                seconds--;
            }
        };
        this.timers.get(groupID).schedule(timerTask, 0,1000);
    }

    /**
     * Starts the timer of the designated group in the game's current player's turn.
     * The task consists in 60 seconds of waiting, with notices at 60, 5 and 0 seconds.
     * If the player completes the turn before the timer elapses that the timer gets purged.
     * If the timer elapses the player loses its turn and the game is updated to the next turn.
     * @param groupID   The groupID of the group you want to start the timer of
     * @see Timer
     * @see TimerTask
     */
    synchronized void startTurnTimer(int groupID){
        this.timers.get(groupID).cancel();
        this.timers.remove(groupID);
        this.timers.add(groupID, new Timer());
        this.timers.get(groupID).purge();
        TimerTask timerTask2 = new TimerTask(){
            int seconds = 120;
            @Override
            public void run() {
                //TODO add GUI support
                if (seconds == 5) {
                    GameContext.get().getGame(groupID).getCurrentPlayer().getUser().receiveUpdate(new Update("Seconds remaining: " + seconds + "...", null));
                } else if (seconds == 0){
                    GameContext.get().getGame(groupID).getCurrentPlayer().getUser().receiveUpdate(new Update("Move lost! No more time.", null));
                    if(GameContext.get().getGame(groupID).getCurrentPlayer().getPhase().equalsTo(SPAWN)){
                        GameContext.get().getGame(groupID).getCurrentPlayer().setPhase(Phase.SECOND);
                        GameContext.get().getGame(groupID).getCurrentPlayer().getUser().receiveUpdate(new Update("You didn't spawn and lost the turn. " +
                                "You're lucky you can at least reload.", null));
                    }
                    GameContext.get().getGame(groupID).getCurrentPlayer().setPhase(DISCONNECTED);
                    System.out.println(">>> Timer expired, disconnecting: "+GameContext.get().getGame(groupID).getCurrentPlayer().getUser().getUsername());
                    GameController.get().updatePhase(groupID);
                    timers.get(groupID).purge();
                }
                seconds--;
            }
        };
        this.timers.get(groupID).schedule(timerTask2, 0,1000);
    }

    /**
     * Looks for the user's group, if the group is full, purges the timer, since the game is starting
     * @param u the user joining
     */
    @Override
    public void onJoin(User u) {
        //Finding the user's group
        int groupID = 0;
        for(Group group: Manager.get().getGroups()){
            for(User user: group.getUsers()){
                if(u.equals(user)){
                    groupID = group.getGroupID();
                    break;
                }
            }
        }
        if(Manager.get().getGroup(groupID).isFull()){
            timers.get(groupID).purge();
            try {
                Manager.get().getGroup(groupID).createGame();
            } catch (NotExistingFieldException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Looks for the user's group,
     * if the group passes to less than 3 players the timer is purged, waiting to be rescheduled
     * when there are enough players to play (at least 3).
     * @param u the user leaving
     */
    @Override
    public void onLeave(User u) {
        //Finding the user's group
        int groupID = 0;
        for(Group group: Manager.get().getGroups()){
            for(User user: group.getUsers()){
                if(u.equals(user)){
                    groupID = group.getGroupID();
                    break;
                }
            }
        }
        if(Manager.get().getGroup(groupID).size() == 2 && !Manager.get().getGroup(groupID).isFull()){
            timers.get(groupID).cancel();
            GameContext.get().getGame(groupID).sendUpdate(new Update("Timer stopped. Waiting for players..."));
        }
    }

    @Override
    public void onUpdate(Update update) {
        //Does nothing
    }

    @Override
    public void onStart() {
        //Does nothing
    }
}
