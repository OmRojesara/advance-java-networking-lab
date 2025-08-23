package javanetwork;

import java.net.*;

public class JavaNetwork {

    public static void main(String[] args) {
        String host = "www.google.com";

        try {
            // Print local and remote host info
            printLocalHostInfo();
            printHostInfo(host);

            // Print details for all IP addresses of the host
            printAllAddresses(host);

        } catch (UnknownHostException e) {
            System.out.println("Host Not Found!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to print local host details
    private static void printLocalHostInfo() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println("Local Host: " + localHost);
    }

    // Method to print basic info for a host
    private static void printHostInfo(String host) throws Exception {
        InetAddress inet = InetAddress.getByName(host);
        System.out.println("\nHost Information for: " + host);
        System.out.println("Host Name: " + inet.getHostName());
        System.out.println("Host Address: " + inet.getHostAddress());
        System.out.println("Reachable (2s): " + inet.isReachable(2000));
    }

    // Method to print details for all IP addresses (IPv4 / IPv6)
    private static void printAllAddresses(String host) throws Exception {
        InetAddress[] addresses = InetAddress.getAllByName(host);

        System.out.println("\nAll IP addresses for: " + host);
        for (InetAddress addr : addresses) {
            if (addr instanceof Inet4Address) {
                System.out.println("Type: IPv4 -> " + addr.getHostAddress());
            } else if (addr instanceof Inet6Address) {
                System.out.println("Type: IPv6 -> " + addr.getHostAddress());
            }

            // Extra details
            System.out.println("Reachable (1s): " + addr.isReachable(1000));
            System.out.println("Loopback Address: " + addr.isLoopbackAddress());
            System.out.println("Site Local: " + addr.isSiteLocalAddress());
            System.out.println("Multicast: " + addr.isMulticastAddress());

            // Multicast details
            if (addr.isMulticastAddress()) {
                System.out.println("MC Global: " + addr.isMCGlobal());
                System.out.println("MC Link Local: " + addr.isMCLinkLocal());
                System.out.println("MC Node Local: " + addr.isMCNodeLocal());
                System.out.println("MC Org Local: " + addr.isMCOrgLocal());
                System.out.println("MC Site Local: " + addr.isMCSiteLocal());
            }
            System.out.println("----------------------");
        }
    }
}
