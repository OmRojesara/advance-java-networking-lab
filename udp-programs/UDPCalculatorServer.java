package calculator;

import java.io.*;
import java.net.*;

public class UDPCalculatorServer {
    public static void main(String[] args) {
        final int PORT = 1300;

        try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
            System.out.println("UDP Server started on port " + PORT);

            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer;

            while (true) {
                // Receive data
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);

                String clientData = new String(receivePacket.getData(), 0, receivePacket.getLength());
                String[] numbers = clientData.split(",");

                int num1 = Integer.parseInt(numbers[0].trim());
                int num2 = Integer.parseInt(numbers[1].trim());

                // Perform operations
                StringBuilder result = new StringBuilder();
                result.append("Addition: ").append(num1 + num2).append("\n");
                result.append("Subtraction: ").append(num1 - num2).append("\n");
                result.append("Multiplication: ").append(num1 * num2).append("\n");
                result.append("Division: ").append(num2 != 0 ? (num1 / num2) : "Cannot divide by zero");

                // Send result back
                sendBuffer = result.toString().getBytes();
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);

                System.out.println("Processed request from " + clientAddress + ":" + clientPort);
            }
        } catch (Exception e) {
            System.out.println("Server Error: " + e.getMessage());
        }
    }
}
