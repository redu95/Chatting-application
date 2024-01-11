import java.io.*;
import java.net.*;

public class Client {

    Socket socket;

    BufferedReader br;
    PrintWriter out;
    public Client(){
        try {
            System.out.println("Sending request to server");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("connection Done!");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            startReading();
            startWrinting();

        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public void startReading() {
        // thread that read the data
        Runnable r1 = () -> {
            System.out.println("reader started...");

            try {
                while (true) {
                
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Server terminated chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Server : " + msg);
            
            }
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("Connection Closed");
            }
        };

        new Thread(r1).start();

    }

    public void startWrinting() {
        // thread that send the data which the client talk
        Runnable r2 = () -> {
            System.out.println("writer started...");

            try {
                while (!socket.isClosed()) {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
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
        System.out.println("this is client...");
        new Client();
    }
}