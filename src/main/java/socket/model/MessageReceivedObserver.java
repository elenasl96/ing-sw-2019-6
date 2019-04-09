package socket.model;

public interface MessageReceivedObserver {
    void onMessage(Message message);
}
