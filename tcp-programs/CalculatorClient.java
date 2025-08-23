package calculator;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CalculatorClient {
    private static final String SERVER = "localhost";
    private static final int PORT = 1300;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connected to Server");
            System.out.println(in.readLine()); // welcome message
            System.out.println(in.readLine()); // usage instructions

            String userInput;
            while (true) {
                System.out.print("\nEnter operation (e.g., 10 + 5) or 'exit' to quit: ");
                userInput = scanner.nextLine();

                out.println(userInput);
                String response = in.readLine();
                if (response == null || response.equalsIgnoreCase("Goodbye!")) {
                    System.out.println("Disconnected from Server.");
                    break;
                }
                System.out.println("Server: " + response);
            }
        } catch (IOException e) {
            System.err.println("Client Error: " + e.getMessage());
        }
    }
}
