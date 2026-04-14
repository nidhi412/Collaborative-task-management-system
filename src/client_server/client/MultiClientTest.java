package client_server.client;

public class MultiClientTest {
    public static void main(String[] args) {
        int clientCount = 5; // Number of clients to simulate
        String host = "127.0.0.1";
        int port = 50003;

        Thread[] clientThreads = new Thread[clientCount];

        // Start multiple clients in parallel
        for (int i = 0; i < clientCount; i++) {
            final int clientId = i + 1;
            clientThreads[i] = new Thread(() -> {
                MyClient client = new MyClient(host, port);
                try {
                    client.openConnection();
                    System.out.println("Client " + clientId + " connected.");

                    String message = "Hello from Client " + clientId;
                    client.sendToServer(message);
                    System.out.println("Client " + clientId + " sent: " + message);

                } catch (Exception e) {
                    System.err.println("Error in Client " + clientId + ": " + e.getMessage());
                } finally {
                    try {
                        client.closeConnection();
                        System.out.println("Client " + clientId + " connection closed.");
                    } catch (Exception e) {
                        System.err.println("Error closing Client " + clientId + ": " + e.getMessage());
                    }
                }
            });
        }

        // Start all threads
        for (Thread thread : clientThreads) {
            thread.start();
        }

        // Wait for all threads to finish
        for (Thread thread : clientThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e.getMessage());
            }
        }

        System.out.println("All clients have finished their tasks.");
    }
}

