/*
 * Simple Client Program using TCP Socket
 * Connects to the server at localhost:5005 and sends a message.
 */
package clientserver;

import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] args) {
        try {
            // Connect to the server running on localhost at port 5005
            Socket s = new Socket("localhost", 5005);

            // Create output stream to send data to the server
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            // Send message to server
            dout.writeUTF("Hello!");

            // Close resources
            dout.close();
            s.close();

            System.out.println("Message sent to server successfully!");
        } catch (Exception e) {
            System.out.println("Client Error: " + e.getMessage());
        }
    }
}
