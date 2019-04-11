package socket.network;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private final ServerSocket serverSocket;
    private final ExecutorService pool;

    public ChatServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        pool = Executors.newCachedThreadPool();
        System.out.println(">>> Listening on " + port);
    }

    public void run() throws IOException {
        while (!this.pool.isShutdown()) {
            Socket clientSocket = serverSocket.accept();
            System.out.println(">>> New connection " + clientSocket.getRemoteSocketAddress());
            pool.submit(new ClientHandler(clientSocket));
        } this.close();
    }

    public void close() throws IOException {
        serverSocket.close();
        pool.shutdown();
    }
}
