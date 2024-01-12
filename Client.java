import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Client extends JFrame {

    Socket socket;

    BufferedReader br;
    PrintWriter out;

    private JLabel heading = new JLabel("Client Area");
    private JTextArea messageArea = new JTextArea();
    private JTextField messageInput = new JTextField();
    private Font font = new Font("Roboto" ,Font.PLAIN,20);

    public Client(){
        try {
            System.out.println("Sending request to server");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("connection Done!");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            creatGUI();
            handleEvents();
             startReading();
            // startWrinting();

        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    private void handleEvents() {
        messageInput.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                
                System.out.println ("keyTyped" +e.getKeyCode());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
                System.out.println ("keyPressed" +e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
                System.out.println ("keyReleased" +e.getKeyCode());
                if (e.getKeyCode()== 10) {
                    //System.out.println("you have pressed enter button");
                    String contentToSend = messageInput.getText();
                    messageArea.append("Me :"+contentToSend +"\n" );
                    out.println(contentToSend);
                    out.flush();
                    messageInput.setText("");
                    messageInput.requestFocus();
                }
            }
            
        });
    }
    private void creatGUI(){

        this.setTitle("Client Messanger[END]");
        this.setSize(600,700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //coding for component
        heading.setFont(font);
        messageArea.setFont(font);
        messageInput.setFont(font);

        heading.setIcon(new ImageIcon("unnamed.png"));
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        messageArea.setEditable(false);
        messageInput.setHorizontalAlignment(SwingConstants.CENTER);
        //frame layout
        this.setLayout(new BorderLayout());

        //adding the components to the frame
        this.add(heading,BorderLayout.NORTH);
        JScrollPane jScrollPane = new JScrollPane(messageArea);
        this.add(jScrollPane,BorderLayout.CENTER);
        this.add(messageInput,BorderLayout.SOUTH);

        this.setVisible(true);

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
                        JOptionPane.showMessageDialog(this,"Server Terminted the chat");
                        messageInput.setEditable(false);
                        socket.close();
                        break;
                    }
                    //System.out.println("Server : " + msg);
                    messageArea.append("Server :" + msg +"\n");
            
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