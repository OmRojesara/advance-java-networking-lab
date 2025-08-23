package calculator;

import java.io.*;
import java.net.*;

public class UDPCalculatorClient {
    public static void main(String[] args) {
        final String SERVER = "localhost";
        final int PORT = 1300;

        try (BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
             DatagramSocket socket = new DatagramSocket()) {

            System.out.print("Enter No 1: ");
            String num1 = user.readLine();

            System.out.print("Enter No 2: ");
            String num2 = user.readLine();

            // Send numbers as "num1,num2"
            String message = num1 + "," + num2;
            byte[] sendBuffer = message.getBytes();

            InetAddress serverAddress = InetAddress.getByName(SERVER);
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, PORT);
            socket.send(sendPacket);

            // Receive result
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("\nServer Response:\n" + response);

        } catch (Exception e) {
            System.out.println("Client Error: " + e.getMessage());
        }
    }
}
