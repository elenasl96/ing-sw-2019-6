package model.moves;
import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import model.Player;

public interface Move{
    void execute(Player player, int groupId) throws InvalidMoveException;
    void handle(MoveRequestHandler moveRequestHandler, int groupId) throws InvalidMoveException;
}
