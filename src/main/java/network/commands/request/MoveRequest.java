package network.commands.request;

import model.moves.Move;
import network.commands.Request;
import network.commands.RequestHandler;
import network.commands.Response;

//TODO javadoc
public class MoveRequest implements Request {
    private Move move;

    public MoveRequest(){
    }

    public MoveRequest(Move move){
        this.move = move;
    }

    public void addMove(Move move){
        this.move = move;
    }

    public Move getMove(){
        return this.move;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
