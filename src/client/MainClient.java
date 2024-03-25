package client;

public class MainClient {
    public static void main(String[] args) {
        Client client = new Client("localhost", 6666);
        client.runClient();
    }
}
