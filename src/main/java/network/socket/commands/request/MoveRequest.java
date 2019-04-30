package network.socket.commands.request;

import model.moves.Move;
import network.socket.commands.Request;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;

import java.util.ArrayList;
import java.util.List;

public class MoveRequest implements Request {
    private List<Move> moves;

    public MoveRequest(){
        this.moves = new ArrayList<>();
    }

    public void addMove(Move move){
        this.moves.add(move);
    }

    public List<Move> getMoves(){
        return this.moves;
    }

    @Override
    public Response handle(RequestHandler handler) {
        handler.handle(this);
        return null;
    }
}
