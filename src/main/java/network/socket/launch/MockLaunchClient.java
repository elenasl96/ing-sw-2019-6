package network.socket.launch;

import network.socket.ClientController;

import java.io.IOException;

public class MockLaunchClient {
    private static int uniqueGroupID = 0;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        uniqueGroupID++;
        Client client = new Client("", 8234);
        client.init();
        ClientController controller = new ClientController(client);
        String username = "Client"+uniqueGroupID;
        controller.mockRun(username, 0);

        client.close();
    }
}
