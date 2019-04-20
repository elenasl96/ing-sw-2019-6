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

    public void playTimer(){
        this.timerController.startTimer();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
