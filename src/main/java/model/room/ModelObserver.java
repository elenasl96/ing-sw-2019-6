package model.room;

import network.socket.commands.Response;

public interface ModelObserver {

    void onJoin(User u);
    void onLeave(User u);
    void onStart();
    Response onUpdate(Update update);
}
