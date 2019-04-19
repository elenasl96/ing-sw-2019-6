package model.clientRoom;

public interface GroupChangeListener {

    void onJoin(User u);
    void onLeave(User u);
    void onStart();
}
