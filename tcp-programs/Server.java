/*
 * Simple Multi-threaded Server Program using TCP Socket
 * Listens on port 5005 and handles multiple client connections.
 * Each client’s message is read and printed on the server console.
 */
package clientserver;

import java.net.*;
import java.io.*;

public class Server {
    public static void main(String args[]) {
        try {
            // Create a server socket listening on port 5005
            ServerSocket ss = new ServerSocket(5005);
            System.out.println("Server started. Waiting for clients...");

            // Run server continuously
            while (true) {
                // Accept incoming client connection
                Socket s = ss.accept();
                System.out.println("Client connected: " + s.getInetAddress());

                // Handle client request in a new thread
                new Thread(() -> {
                    try {
                        // Read message from client
                        DataInputStream din = new DataInputStream(s.getInputStream());
                        String msg = din.readUTF();
                        System.out.println("Message from client: " + msg);

                        // Close resources for this client
                        din.close();
                        s.close();
                    } catch (Exception e) {
                        System.out.println("Client handling error: " + e.getMessage());
                    }
                }).start();
            }
        } catch (Exception e) {
            System.out.println("Server Error: " + e.getMessage());
        }
    }
}
