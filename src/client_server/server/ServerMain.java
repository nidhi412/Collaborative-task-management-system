package client_server.server;

import java.util.Scanner;

public class ServerMain {
    public static void main(String[] args) {
        int port = 50004; // Use the same port as the client
        CTMSServer server = new CTMSServer(port);

        try {
            server.listen();
            System.out.println("Server started on port: " + port);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Enter message to broadcast (or type 'exit' to stop): ");
                String message = scanner.nextLine();
                if ("exit".equalsIgnoreCase(message)) {
                    server.close();
                    System.out.println("Server stopped.");
                    break;
                }
                server.sendToAllClients("Server: " + message);
                System.out.println("Broadcasted message: " + message);
            }
            scanner.close();
        } catch (Exception e) {
            System.err.println("Error starting the server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
