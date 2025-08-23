/*
 * Two-Way UDP Client
 * Sends message to server and receives reply.
 */
package serverudp;

import java.net.*;

public class TwoWayUDPClient {
    public static void main(String[] args) {
        try {
            DatagramSocket ds = new DatagramSocket();

            // Send message
            String msg = "Hello Server, this is Client!";
            byte[] sendData = msg.getBytes();

            InetAddress ip = InetAddress.getByName("localhost");
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, 6000);

            ds.send(sendPacket);
            System.out.println("[Client] Sent: " + msg);

            // Receive reply
            byte[] buffer = new byte[1024];
            DatagramPacket recPacket = new DatagramPacket(buffer, buffer.length);
            ds.receive(recPacket);

            String reply = new String(recPacket.getData(), 0, recPacket.getLength());
            System.out.println("[Client] Server replied: " + reply);

            ds.close();
        } catch (Exception e) {
            System.out.println("[Client Error] " + e.getMessage());
        }
    }
}
