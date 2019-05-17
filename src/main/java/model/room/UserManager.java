package model.room;

import controller.TimerController;

public class UserManager extends User {
    private transient TimerController timerController;

    public UserManager(String username) {
        super(username);
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
