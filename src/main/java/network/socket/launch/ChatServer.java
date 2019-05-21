package network.socket.launch;

import network.socket.ClientHandler;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatServer {
    private final ServerSocket serverSocket;
    private final ExecutorService pool;

    private final AtomicBoolean forcedClose;
    private InetAddress localPoisonAddress;
    /**
     * if true, local indicates that local Client (running on localhost) are allowed
     * else the server would recognize any localhost as a poison pill
     */
    private final boolean local;

    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer(8234, true);
        try {
            server.run();
        } finally {
            server.close();
        }
    }

    public ChatServer(int port, boolean local) throws IOException {
        serverSocket = new ServerSocket(port);
        pool = Executors.newCachedThreadPool();
        System.out.println(">>> Listening on " + port);
        this.local = local;
        if(!local){
            this.localPoisonAddress = InetAddress.getByName("localhost");
        }
        forcedClose = new AtomicBoolean(false);
    }

    public void run() {
        while (!forcedClose.get()) {
            try {
                Socket clientSocket = serverSocket.accept();
                InetSocketAddress remoteSocketAddress = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
                if((local && remoteSocketAddress.getPort() == 6000) ||
                        (!local && remoteSocketAddress.getAddress().equals(localPoisonAddress))) {
                    System.out.println(">>> Shutting down with Poison Pill...");
                    clientSocket.close();
                    this.forcedClose.set(true);
                    break;
                } else {
                    System.out.println(">>> New connection " + clientSocket.getRemoteSocketAddress());
                    pool.submit(new ClientHandler(clientSocket));
                }
            } catch (IOException ex) {
                pool.shutdown();
            }
        }
        try{
            this.close();
        } catch(IOException e){
            //closing
        }
    }

    public void close() throws IOException {
        serverSocket.close();
        pool.shutdown();
    }
}
