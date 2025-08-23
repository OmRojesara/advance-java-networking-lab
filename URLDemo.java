package urldemo;

import java.net.*;
import java.io.*;

/**
 * A simple program to fetch and display HTML content from a given URL.
 * Example: Downloads HTML source of https://www.wikipedia.com
 */
public class URLDemo {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.wikipedia.com");
            URLConnection con = url.openConnection();

            // try-with-resources ensures reader closes automatically
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String line;
                System.out.println("---- HTML Source of " + url + " ----\n");
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }

        } catch (MalformedURLException e) {
            System.out.println("Invalid URL: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading data: " + e.getMessage());
        }
    }
}
