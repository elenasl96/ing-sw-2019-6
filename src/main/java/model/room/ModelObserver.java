package model.room;

import network.commands.Response;

public interface ModelObserver {

    void onJoin(User u);
    void onLeave(User u);
    void onStart();
    Response onUpdate(Update update);
}
