package model.room;

public interface ModelObserver {

    void onJoin(User u);
    void onLeave(User u);
    void onStart();
    void onUpdate(Update update);
}
