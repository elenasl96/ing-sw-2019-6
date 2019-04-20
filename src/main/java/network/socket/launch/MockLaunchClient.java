package network.socket.launch;

import network.socket.ClientController;

import java.io.IOException;

public class MockLaunchClient {
    public static void main(String[] args) throws IOException {
        Client client = new Client("", 8234);
        client.init();
        ClientController controller = new ClientController(client);
        String username = "Client";
        controller.mockRun(username, 0);

        client.close();
    }
}
