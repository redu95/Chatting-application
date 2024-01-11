import java.net.*;
import java.io.*;

class Server {

    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    // Constructor
    public Server() {

        try {
            // Create a server socket on port 7777
            server = new ServerSocket(7777);
            System.out.println("Server is ready to accept connection");
            System.out.println("waiting...");
            
            // Accept client connection
            socket = server.accept();

            // Set up input and output streams for communication
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Start reading and writing threads
            startReading();
            startWriting();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Thread to read messages from the client
    public void startReading() {
        Runnable r1 = () -> {
            System.out.println("Reader started...");

            try {
                while (true) {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Client terminated chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Client: " + msg);
                }
            } catch (Exception e) {
                System.out.println("Connection Closed");
            }

        };
        new Thread(r1).start();
    }

    // Thread to write messages to the client
    public void startWriting() {
        Runnable r2 = () -> {
            System.out.println("Writer started...");

            try {
                while (!socket.isClosed()) {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();

                    // Send the message to the client
                    out.println(content);
                    out.flush();

                    if (content.equals("exit")) {
                        socket.close();
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Connection Closed");
            }
        };

        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is the server... Going to start the server");
        new Server();
    }
}
