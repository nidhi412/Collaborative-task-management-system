package client_server.client;

public class ClientMain {
    public static void main(String[] args) {
        MyClient client = new MyClient("10.0.0.218", 50004);
        try {
            client.openConnection();

            client.addProject("Project A");
            client.addProject("Project B");
            client.getProjects();

            client.addTask("Project A", "Task 1: Design");
            client.addTask("Project A", "Task 2: Development");
            client.getTasks("Project A");

            client.getTasks("Project B"); // Empty

            client.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

