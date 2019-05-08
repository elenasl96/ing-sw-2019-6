package controller;

import model.room.*;

import java.util.Timer;
import java.util.TimerTask;


public class TimerController implements GroupChangeListener, GameUpdateObserver {
    private User serverUser;
    private Group group;

    private Timer timer;

    public TimerController(Group group, User serverUser){
        this.group = group;
        group.observe(this);
        this.serverUser = serverUser;
    }

    public void startTimer(){
        TimerTask timerTask = new TimerTask(){
            int seconds = 2;
            @Override
            public void run() {
                if(seconds == 60){
                    group.sendMessage(new Message(group, serverUser, "Timer started: " + seconds + "seconds left..."));
                }else if(seconds == 10) {
                    group.sendMessage(new Message(group, serverUser, "Hurry, 10 seconds left!"));
                } else if (seconds <= 5 && seconds > 0) {
                    group.sendMessage(new Message(group, serverUser, "Seconds remaining left: " + seconds + "..."));
                } else if (seconds == 0){
                    group.sendMessage(new Message(group, serverUser, "Game starting"));
                    group.createGame();
                    timer.cancel();
                }
                seconds--;
            }
        };
        this.timer.schedule(timerTask, 0,1000);
    }

    @Override
    public void onJoin(User u) {
        if(this.group.size() == 3){
            timer = new Timer();

        }
        if(this.group.isFull()){
            timer.cancel();
            group.createGame();
        }
    }

    @Override
    public void onLeave(User u) {
        if(this.group.size() == 2 && !this.group.isFull()){
            timer.cancel();

            group.sendMessage(new Message(group, serverUser, "Timer stopped. Waiting for players..."));
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
