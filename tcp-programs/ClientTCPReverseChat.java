/*
 * TCP Client Program for Reverse String Chat
 * Connects to server at port 1312, sends messages, and receives responses.
 */
package reversestringchat;

import java.io.*;
import java.net.*;

public class ClientTCPReverseChat {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1312);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader user = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("[Client] Connected to Server at port 1312");

            String msg;
            while (true) {
                System.out.print("You: ");
                String input = user.readLine();
                if (input == null || input.equalsIgnoreCase("exit")) {
                    System.out.println("[Client] Disconnected.");
                    break;
                }

                // Send message to server
                out.println(input);

                // Receive response from server
                msg = in.readLine();
                if (msg == null) break;

                System.out.println("Server: " + msg);
            }
        } catch (IOException e) {
            System.out.println("[Client Error] " + e.getMessage());
        }
    }
}
