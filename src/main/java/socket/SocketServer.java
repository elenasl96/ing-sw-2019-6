package socket;

import controller.Controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer implements Closeable{
    private final int port;
    private ServerSocket serverSocket;
    private ExecutorService pool;

    //added
    private Controller controller;

    public SocketServer(int port){
        this.port = port;
        this.controller = new Controller();
        pool = Executors.newCachedThreadPool();
    }

    public void init() throws IOException{
        serverSocket = new ServerSocket(port);
        System.out.println(">>> Listening on "+ port);
    }

    public Socket acceptConnection() throws IOException{
        Socket accepted = serverSocket.accept();
        System.out.println("Connection accepted: "+ accepted.getRemoteSocketAddress());
        return accepted;
    }

    public void handleConnection(Socket clientConnection) throws IOException{
        InputStream is = null;
        OutputStream os = null;
        try(
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            PrintWriter out = new PrintWriter(os, true);
                ){
            is = clientConnection.getInputStream();
            os = clientConnection.getOutputStream();

            String command;

            do {
                command = in.readLine();
                if (command != null && !command.startsWith("quit")){
                    System.out.println(">>> "+clientConnection.getRemoteSocketAddress()
                    + ": "+command);
                    controller.update(command);

                    out.flush();
                }
            } while (command != null && !command.startsWith("quit"));
        }finally{
            if(is != null && os != null){
                is.close();
                os.close();
            }
            clientConnection.close();
        }
    }

    public void lifeCycle() throws IOException{
        init();
        boolean t = true;
        while(t){
            final Socket socket = acceptConnection();

            pool.submit(() -> {
               try{
                   handleConnection(socket);
               } catch(IOException e){
                   System.err.println("Problem with "+ socket.getLocalAddress()+
                           ": "+e.getMessage());
               }
            });
        }
    }


    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Provide port please");
            return;
        }

        int port = Integer.parseInt(args[0]);

        SocketServer server = new SocketServer(port);
        try{
            server.lifeCycle();} finally{
            server.close();
        }
    }

    @Override
    public void close() throws IOException{
        serverSocket.close();
    }
}
