package network.socket.launch;


import java.io.IOException;

public class LaunchServer {
    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer(8234, true);
        try {
            server.run();
        } finally {
            server.close();
        }
    }
}
