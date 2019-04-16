package socket.model;

public interface GameUpdateObserver {
    void onUpdate(Update update);
    void onStart();

}
