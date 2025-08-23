/*
 * Java URL Example Program
 * Demonstrates usage of various methods from java.net.URL
 */
package javaurl;

import java.net.*;
import java.io.*;

public class JavaURL {

    public static void main(String[] args) {
        try {
            // Create a URL object
            URL url = new URL("https://www.javatpoint.com/java-tutorial?topic=networking");

            // Basic information
            System.out.println("Protocol     : " + url.getProtocol());
            System.out.println("Host Name    : " + url.getHost());
            System.out.println("Port Number  : " + url.getPort());   // returns -1 if not set
            System.out.println("Default Port : " + url.getDefaultPort());
            System.out.println("File Name    : " + url.getFile());
            System.out.println("Path         : " + url.getPath());
            System.out.println("Query        : " + url.getQuery());

            // Convert to URI
            System.out.println("URI          : " + url.toURI());

            // Reading content of the webpage
            System.out.println("\n--- Webpage Content ---");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            int count = 0;
            while ((line = br.readLine()) != null && count < 10) { // show only first 10 lines
                System.out.println(line);
                count++;
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
