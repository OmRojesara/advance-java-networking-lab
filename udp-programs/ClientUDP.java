/*
 * Simple UDP Client Program
 * Sends a message to the UDP server running on localhost:6000
 */
package serverudp;

import java.net.*;

public class ClientUDP {
    public static void main(String[] args) {
        try {
            // Create DatagramSocket for sending data
            DatagramSocket ds = new DatagramSocket();

            // Message to send
            String msg = "Hello UDP Server, this is Client!";
            byte[] b = msg.getBytes();

            // Destination: localhost at port 6000
            InetAddress ip = InetAddress.getByName("localhost");
            DatagramPacket dp = new DatagramPacket(b, b.length, ip, 6000);

            // Send packet
            ds.send(dp);
            System.out.println("[Client] Message sent to server: " + msg);

            // Close socket
            ds.close();
        } catch (Exception e) {
            System.out.println("[Client Error] " + e.getMessage());
        }
    }
}
