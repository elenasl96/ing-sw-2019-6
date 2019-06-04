package controller;

import model.GameContext;
import model.room.*;
import network.Manager;
import network.commands.Response;

import java.util.*;

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
                    timers.get(groupID).cancel();
                }
                seconds--;
            }
        };
        this.timers.get(groupID).schedule(timerTask, 0,1000);
    }

    synchronized void startTurnTimer(int groupID){
        timers.get(groupID).cancel();
        TimerTask timerTask = new TimerTask(){
            int seconds = 5;
            @Override
            public void run() {
                if(seconds == 60){
                    Manager.get().getGroup(groupID).sendUpdate(new Update(seconds + " seconds left..."));
                }else if(seconds == 10) {
                    Manager.get().getGroup(groupID).sendUpdate(new Update("Hurry, 10 seconds left!"));
                } else if (seconds <= 5 && seconds > 0) {
                    Manager.get().getGroup(groupID).sendUpdate(new Update("Seconds remaining: " + seconds + "..."));
                } else if (seconds == 0){
                    Manager.get().getGroup(groupID).sendUpdate(new Update("Move lost! No more time."));
                    GameController.get().updatePhase(groupID);
                    timers.get(groupID).cancel();
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
            timers.get(groupID).cancel();
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
