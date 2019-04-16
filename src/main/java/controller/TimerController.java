package controller;

import socket.model.Group;
import socket.model.GroupChangeListener;
import socket.model.Message;
import socket.model.User;
import java.util.Timer;
import java.util.TimerTask;


public class TimerController implements GroupChangeListener {
    private User serverUser;
    private Group group;

    private Timer timer;
    private TimerTask timerTask = new TimerTask(){
        int seconds = 60;
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

    public TimerController(Group group, User serverUser){
        this.group = group;
        group.observe(this);
        this.serverUser = serverUser;
    }

    @Override
    public void onJoin(User u) {
        if(this.group.size() == 3){
            timer = new Timer();
            timer.schedule(timerTask, 0, 1000);
        }
        if(this.group.isFull()){
            timer.cancel();
            this.group.createGame();
        }
    }

    @Override
    public void onLeave(User u) {
        if(this.group.size() == 2){
            this.group.setNotFull();
            timer.cancel();
        }
    }

    @Override
    public void onStart() {
        this.group.createGame();
    }
}
