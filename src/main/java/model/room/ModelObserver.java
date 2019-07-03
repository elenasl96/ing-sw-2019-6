package model.room;


/**
 * PATTERN OBSERVER
 */
public interface ModelObserver {
    void onJoin(User u);
    void onLeave(User u);
    void onStart();
    void onUpdate(Update update);

    void onEndGame();
}
