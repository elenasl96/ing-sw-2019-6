package socket.model;

public interface GroupChangeListener {

    void onJoin(User u);
    void onLeave(User u);
    void onStart();
}
