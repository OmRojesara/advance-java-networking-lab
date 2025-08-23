/*
 * Two-Way UDP Server
 * Listens on port 6000 and replies to client messages.
 */
package serverudp;

import java.net.*;

public class TwoWayUDPServer {
    public static void main(String[] args) {
        try {
            DatagramSocket ds = new DatagramSocket(6000);
            byte[] buffer = new byte[1024];

            System.out.println("[Server] Waiting for client message...");

            // Receive message
            DatagramPacket recPacket = new DatagramPacket(buffer, buffer.length);
            ds.receive(recPacket);

            String msg = new String(recPacket.getData(), 0, recPacket.getLength());
            System.out.println("[Server] Received: " + msg);

            // Reply back
            String reply = "Hello Client, I received your message!";
            byte[] sendData = reply.getBytes();

            InetAddress clientIP = recPacket.getAddress();
            int clientPort = recPacket.getPort();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIP, clientPort);

            ds.send(sendPacket);
            System.out.println("[Server] Reply sent to client.");

            ds.close();
        } catch (Exception e) {
            System.out.println("[Server Error] " + e.getMessage());
        }
    }
}
