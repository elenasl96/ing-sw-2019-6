package model.room;


//TODO javadoc
public interface ModelObserver {
    void onJoin(User u);
    void onLeave(User u);
    void onStart();
    void onUpdate(Update update);
}
