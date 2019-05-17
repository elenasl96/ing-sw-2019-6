package network.socket.commands.request;

import network.socket.commands.Request;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;

public class SendInput implements Request {

    private int input;

    public SendInput(int input){
        this.input = input;
    }

    public int getInput(){
        return this.input;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
