/*
 * Simple UDP Server Program
 * Listens on port 6000 and receives messages from clients.
 */
package serverudp;

import java.net.*;

public class ServerUDP {
    public static void main(String[] args) {
        try {
            // Create DatagramSocket to listen on port 6000
            DatagramSocket ds = new DatagramSocket(6000);
            byte[] b = new byte[1024];

            System.out.println("[Server] Waiting for client messages...");

            // Receive packet
            DatagramPacket recp = new DatagramPacket(b, b.length);
            ds.receive(recp);

            // Extract message
            String msg = new String(recp.getData(), 0, recp.getLength());
            System.out.println("[Server] Received message from client: " + msg);

            // Close socket
            ds.close();
        } catch (Exception e) {
            System.out.println("[Server Error] " + e.getMessage());
        }
    }
}
