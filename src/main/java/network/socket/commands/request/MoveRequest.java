package network.socket.commands.request;

import model.moves.Move;
import network.socket.commands.Request;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;

public class MoveRequest implements Request {
    private Move move;

    public void addMove(Move move){
        this.move = move;
    }

    public Move getMove(){
        return this.move;
    }

    @Override
    public Response handle(RequestHandler handler) {
        handler.handle(this);
        return null;
    }
}
