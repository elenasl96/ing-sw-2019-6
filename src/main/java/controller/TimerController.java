package controller;

import model.room.*;
import network.socket.Manager;

import java.util.Timer;
import java.util.TimerTask;


public class TimerController implements GroupChangeListener, GameUpdateObserver {
    private User serverUser;
    private int groupID;
    private Timer timer;

    public TimerController(Group group, User serverUser){
        groupID = group.getGroupID();
        group.observe(this);
        this.serverUser = serverUser;
    }

    public void startTimer(){
        TimerTask timerTask = new TimerTask(){
            int seconds = 2;
            @Override
            public void run() {
                if(seconds == 60){
                    Manager.get().getGroup(groupID).sendMessage(new Message(
                            Manager.get().getGroup(groupID), serverUser, "Timer started: " + seconds + "seconds left..."));
                }else if(seconds == 10) {
                    Manager.get().getGroup(groupID).sendMessage(new Message(
                            Manager.get().getGroup(groupID), serverUser, "Hurry, 10 seconds left!"));
                } else if (seconds <= 5 && seconds > 0) {
                    Manager.get().getGroup(groupID).sendMessage(new Message(
                            Manager.get().getGroup(groupID), serverUser, "Seconds remaining left: " + seconds + "..."));
                } else if (seconds == 0){
                    Manager.get().getGroup(groupID).sendMessage(new Message(
                            Manager.get().getGroup(groupID), serverUser, "setGame starting"));
                    Manager.get().getGroup(groupID).createGame();
                    timer.cancel();
                }
                seconds--;
            }
        };
        this.timer.schedule(timerTask, 0,1000);
    }

    @Override
    public void onJoin(User u) {
        if(Manager.get().getGroup(groupID).size() == 3){
            timer = new Timer();

        }
        if(Manager.get().getGroup(groupID).isFull()){
            timer.cancel();
            Manager.get().getGroup(groupID).createGame();
        }
    }

    @Override
    public void onLeave(User u) {
        if(Manager.get().getGroup(groupID).size() == 2 && !Manager.get().getGroup(groupID).isFull()){
            timer.cancel();

            Manager.get().getGroup(groupID).sendMessage(new Message(Manager.get().getGroup(groupID), serverUser, "Timer stopped. Waiting for players..."));
        }
    }

    @Override
    public void onUpdate(Update update) {
        //I haven't programmed that path yet
    }

    @Override
    public void onStart() {
        //essentially does nothing
    }
}
