package calculator;

import java.io.*;
import java.net.*;

public class CalculatorServer {
    private static final int PORT = 1300;

    public static void main(String[] args) {
        System.out.println("Server started, waiting for clients...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());
                new Thread(new ClientHandler(socket)).start(); // handle multiple clients
            }
        } catch (IOException e) {
            System.err.println("Server Error: " + e.getMessage());
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println("Welcome to Calculator Server!");
            out.println("Send operations in format: num1 operator num2 (e.g., 10 + 5). Type 'exit' to quit.");

            String input;
            while ((input = in.readLine()) != null) {
                if ("exit".equalsIgnoreCase(input)) {
                    out.println("Goodbye!");
                    break;
                }

                try {
                    String[] parts = input.split(" ");
                    if (parts.length != 3) {
                        out.println("Invalid format. Use: num1 operator num2");
                        continue;
                    }

                    double num1 = Double.parseDouble(parts[0]);
                    String operator = parts[1];
                    double num2 = Double.parseDouble(parts[2]);
                    double result;

                    switch (operator) {
                        case "+": result = num1 + num2; break;
                        case "-": result = num1 - num2; break;
                        case "*": result = num1 * num2; break;
                        case "/": 
                            if (num2 == 0) {
                                out.println("Error: Division by zero!");
                                continue;
                            }
                            result = num1 / num2; break;
                        default:
                            out.println("Invalid operator! Use +, -, *, /");
                            continue;
                    }

                    out.println("Result: " + result);

                } catch (NumberFormatException e) {
                    out.println("Error: Please enter valid numbers.");
                }
            }
        } catch (IOException e) {
            System.err.println("Client disconnected: " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
        }
    }
}
