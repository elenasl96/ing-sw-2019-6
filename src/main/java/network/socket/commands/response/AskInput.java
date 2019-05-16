package network.socket.commands.response;

import model.moves.Effect;
import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;

public class AskInput implements Response {

    private StringBuilder display;
    Effect effect;

    public AskInput(){
        this.display = new StringBuilder();
    }

    public void append(String string){
        this.display.append(string);
    }

    public void setEffect(boolean target) {
    }

    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }

    @Override
    public String toString(){
        return this.display.toString();
    }
}
