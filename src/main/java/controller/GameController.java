package controller;

import model.Game;
import model.Player;
import model.enums.Phase;
import org.jetbrains.annotations.NotNull;
import model.clientRoom.Group;
import model.clientRoom.User;

import java.util.LinkedList;
import java.util.List;

public class GameController {
    /**
     * the current game
     */
    private Game game;
    private final List<User> users = new LinkedList<>();
    private Group group;
    private User serverUser;
    private TimerController timerController;

    public GameController(Group group, @NotNull List<User> groupUsers){
        this.group = group;
        this.serverUser = groupUsers.iterator().next();
        this.timerController = new TimerController(this.group, serverUser);
    }

    public void firstPlayer(Group group, @NotNull List<User> groupUsers){
        int firstPlayer = groupUsers.get(0).getUserID();
        for(User u : groupUsers){
            this.users.add(u);
            if(u.getUserID()<=firstPlayer){
                firstPlayer = u.getUserID();
            }
        }
        game.setNumberPlayers(groupUsers.size());

    }

    public void startGame(){
        //todo Add number of skulls
        game.getBoard().getField().getSquares().forEach(square-> {
            square.setGrabbable(game.getBoard());
        });

    }

    public void update(String command){

    }

    public void play(){
        Player current;
        for(int i=0; i < game.getNumberPlayers(); i++) {
            if(game.getPlayers().get(i).isFirstPlayer()){
                break;
            }
            game.getPlayers().iterator().next();
        }

        while(!game.isDone()) {
            current = game.getPlayers().iterator().next();
            current.setPhase(Phase.FIRST);
            playPhase(current);
            current.setPhase(Phase.SECOND);
            playPhase(current);
            current.setPhase(Phase.RELOAD);
            endPhase(current);
            current.setPhase(Phase.WAIT);
        }
    }

    private void endPhase(Player player) {
    }

    private void playPhase(Player player) {

    }

}
