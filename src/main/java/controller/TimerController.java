package controller;

import socket.model.Group;
import socket.model.GroupChangeListener;
import socket.model.Message;
import socket.model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;


public class TimerController implements GroupChangeListener {
    private Group group;
    private Timer timer;
    private TimerTask timerTask;
    private User serverUser;

    public TimerController(Group group, User serverUser){
        this.group = group;
        group.observe(this);
        this.serverUser = serverUser;
    }

    @Override
    public void onJoin(User u) {
        if(this.group.size() == 3){
            timer = new Timer(60*1000, new StartGame());
        }
    }

    @Override
    public void onLeave(User u) {
        if(timer.isRunning() && this.group.size() < 3){
            timer.stop();
        }
    }

    public class StartGame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timer.stop();
            group.setFull();
        }
    }
}
