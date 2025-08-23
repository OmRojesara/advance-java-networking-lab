/*
 * TCP Server Program for Reverse String Chat
 * Listens on port 1312 and returns reversed messages back to the client.
 */
package reversestringchat;

import java.io.*;
import java.net.*;

public class ServerTCPReverseChat {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1312)) {
            System.out.println("[Server] Listening on port 1312...");
            Socket socket = serverSocket.accept();
            System.out.println("[Server] Client connected!");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String msg;
            while (true) {
                msg = in.readLine();
                if (msg == null) break;

                // Reverse the client’s message
                String rev = new StringBuilder(msg).reverse().toString();
                System.out.println("Client: " + msg + " → Reversed: " + rev);

                // Send reversed message back to client
                out.println(rev);
            }

            socket.close();
            System.out.println("[Server] Connection closed.");
        } catch (IOException e) {
            System.out.println("[Server Error] " + e.getMessage());
        }
    }
}
