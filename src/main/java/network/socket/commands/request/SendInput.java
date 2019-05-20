package network.socket.commands.request;

import network.socket.commands.Request;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;

public class SendInput implements Request {

    private int input;
    private String inputType;

    public SendInput(int askNumber, String inputType) {
        this.input = askNumber;
        this.inputType = inputType;
    }

    public int getInput(){
        return this.input;
    }

    public String getInputType() {
        return inputType;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
