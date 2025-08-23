/*
 * TCP Server Chat Program
 * Listens on port 1312, receives messages from client, and replies interactively.
 */
package serverchat;

import java.io.*;
import java.net.*;

public class ServerTCPChat {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1312)) {
            System.out.println("[Server] Started. Waiting for client...");
            Socket socket = serverSocket.accept();
            System.out.println("[Server] Client connected!");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader user = new BufferedReader(new InputStreamReader(System.in));

            String msg;
            while (true) {
                // Receive message from client
                msg = in.readLine();
                if (msg == null || msg.equalsIgnoreCase("exit")) {
                    System.out.println("[Server] Client disconnected.");
                    break;
                }
                System.out.println("Client: " + msg);

                // Send response to client
                System.out.print("You: ");
                String reply = user.readLine();
                out.println(reply);

                if (reply.equalsIgnoreCase("exit")) {
                    System.out.println("[Server] Closing connection.");
                    break;
                }
            }

            socket.close();
            System.out.println("[Server] Connection closed.");
        } catch (IOException e) {
            System.out.println("[Server Error] " + e.getMessage());
        }
    }
}
