package socket.model;

import model.enums.Character;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class UserPlayer implements Serializable {
    private transient List<GameUpdateObserver> observers;
    private Character character;

    public UserPlayer(Character character) {
        super();
        this.character = character;
        this.observers = new LinkedList<>();
    }

    public void listenToGameUpdate(GameUpdateObserver gameUpdateObserver){
        observers.add(gameUpdateObserver);
    }

    public void receiveGameUpdate(Update update) {
        for (GameUpdateObserver observer : observers) {
            observer.onUpdate(update);
        }
    }
}
