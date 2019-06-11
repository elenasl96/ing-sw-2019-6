package controller;

import model.GameContext;
import model.enums.Phase;
import model.room.*;
import network.Manager;
import network.commands.Response;

import java.util.*;

import static model.enums.Phase.SPAWN;

/**
 * SINGLETON (SERVER SIDE)
 * Handles the timers of all the games
 */

public class TimerController implements ModelObserver {
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

    public void addGroup(Group group){
        //Observer of every group, starts the timer when called
        group.observe(this);
    }

    synchronized void startTimer(int groupID){
        while(timers.size()<groupID+1){
            timers.add(null);
        }
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
                    Manager.get().getGroup(groupID).createGame();
                    timers.get(groupID).purge();
                }
                seconds--;
            }
        };
        this.timers.get(groupID).schedule(timerTask, 0,1000);
    }

    public synchronized void startTurnTimer(int groupID){
        this.timers.get(groupID).purge();
        TimerTask timerTask = new TimerTask(){
            int seconds = 60;
            @Override
            public void run() {
                if(seconds == 60){
                    Manager.get().getGroup(groupID).getGame().getCurrentPlayer().getUser().receiveUpdate(new Update(seconds + " seconds left..."));
                } else if (seconds == 5) {
                    Manager.get().getGroup(groupID).getGame().getCurrentPlayer().getUser().receiveUpdate(new Update("Seconds remaining: " + seconds + "..."));
                } else if (seconds == 0){
                    Manager.get().getGroup(groupID).getGame().getCurrentPlayer().getUser().receiveUpdate(new Update("Move lost! No more time."));
                    if(GameContext.get().getGame(groupID).getCurrentPlayer().getPhase().equalsTo(SPAWN)){
                        GameContext.get().getGame(groupID).getCurrentPlayer().setPhase(Phase.SECOND);
                        Manager.get().getGroup(groupID).getGame().getCurrentPlayer().getUser().receiveUpdate(new Update("You didn't spawn and lost the turn. " +
                                "You're lucky you can at least reload."));
                    }
                    GameController.get().updatePhase(groupID);
                    timers.get(groupID).purge();
                }
                seconds--;
            }
        };
        this.timers.get(groupID).schedule(timerTask, 0,1000);
    }

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
            Manager.get().getGroup(groupID).createGame();
        }
    }

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
    public Response onUpdate(Update update) {
        return null;
    }

    @Override
    public void onStart() {
        //essentially does nothing
    }
}
