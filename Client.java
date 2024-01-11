import java.io.*;
import java.net.*;

public class Client {

    Socket socket;

    BufferedReader br;
    PrintWriter out;

    // Constructor
    public Client() {
        try {
            System.out.println("Sending request to server");
            // Connect to the server on localhost (127.0.0.1) and port 7777
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("Connection established!");

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

    // Thread to read messages from the server
    public void startReading() {
        Runnable r1 = () -> {
            System.out.println("Reader started...");

            try {
                while (true) {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Server terminated chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Server: " + msg);
                }
            } catch (Exception e) {
                System.out.println("Connection Closed");
            }
        };

        new Thread(r1).start();
    }

    // Thread to write messages to the server
    public void startWriting() {
        Runnable r2 = () -> {
            System.out.println("Writer started...");

            try {
                while (!socket.isClosed()) {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();

                    // Send the message to the server
                    out.println(content);
                    out.flush();

                    if (content.equals("exit")) {
                        socket.close();
                        break;
                    }
                }
                System.out.println("Connection is Closed");

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Connection is Closed");
        };

        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is the client...");
        new Client();
    }
}
