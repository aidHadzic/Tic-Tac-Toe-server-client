package tictactoeserver;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 *
 * @author aid
 */
public class Server extends JFrame {

    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;
    public static final int PLAYER1_WON = 1;
    public static final int PLAYER2_WON = 2;
    public static final int DRAW = 3;
    public static final int CONTINUE = 4;
    
    public static void main(String[] args) {
        Server display = new Server();
    }

    public Server() {
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        setSize(550, 300);
        setTitle("TicTacToeServer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            textArea.append(new Date() + ":     Server started at socket 8000\n");
            int sessionNum = 1;
            while (true) {
                textArea.append(new Date() + ":     Waiting for players to join session " + sessionNum + "\n");
                
                //connection to player1
                Socket firstPlayer = serverSocket.accept();
                textArea.append(new Date() + ":     Player 1 joined session " + sessionNum + ". Player 1's IP address " + firstPlayer.getInetAddress().getHostAddress() + "\n");
                //notify first player that he is first player
                new DataOutputStream(firstPlayer.getOutputStream()).writeInt(PLAYER1);

                //connection to player2
                Socket secondPlayer = serverSocket.accept();
                textArea.append(new Date() + ":     Player 2 joined session " + sessionNum + ". Player 2's IP address " + secondPlayer.getInetAddress().getHostAddress() + "\n");
                //notify second player that he is second player
                new DataOutputStream(secondPlayer.getOutputStream()).writeInt(PLAYER2);

                //starting the thread for two players
                textArea.append(new Date() + ":     Starting a thread for session " + sessionNum++ + "...\n");
                NewSession task = new NewSession(firstPlayer, secondPlayer);
                Thread t1 = new Thread(task);
                t1.start();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }

    }

}

