package model.room;

import controller.TimerController;

public class UserManager extends User {
    private transient TimerController timerController;

    public UserManager(String username) {
        super(username);
    }

    public void createTimerController(Group group){
        this.timerController = new TimerController(group, this);
    }
}
