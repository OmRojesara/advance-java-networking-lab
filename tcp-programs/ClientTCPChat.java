/*
 * TCP Client Chat Program
 * Connects to server at port 1312 and exchanges messages interactively.
 */
package serverchat;

import java.io.*;
import java.net.*;

public class ClientTCPChat {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1312);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader user = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("[Client] Connected to Server at port 1312");

            String msg;
            while (true) {
                // Send message to server
                System.out.print("You: ");
                String input = user.readLine();
                out.println(input);

                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("[Client] Disconnected.");
                    break;
                }

                // Receive response from server
                msg = in.readLine();
                if (msg == null || msg.equalsIgnoreCase("exit")) {
                    System.out.println("[Client] Server disconnected.");
                    break;
                }

                System.out.println("Server: " + msg);
            }
        } catch (IOException e) {
            System.out.println("[Client Error] " + e.getMessage());
        }
    }
}
