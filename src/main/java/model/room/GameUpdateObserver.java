package model.room;

import model.Player;

public interface GameUpdateObserver {
    void onUpdate(Update update);
}
