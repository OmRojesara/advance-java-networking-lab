import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Inet4Add {
    public static void main(String[] args) {
        try {
            Inet4Address ip1 = (Inet4Address) Inet4Address.getByName("www.google.org");
            Inet4Address ip2 = (Inet4Address) InetAddress.getByName("www.yahoo.com");

            printInet4AddressInfo("ip1 (google.org)", ip1);
            printInet4AddressInfo("ip2 (yahoo.com)", ip2);

            System.out.println("ip1 == ip2 : " + ip1.equals(ip2));
            System.out.println("ip1.getCanonicalHostName() : " + ip1.getCanonicalHostName());
            System.out.println("ip2.getCanonicalHostName() : " + ip2.getCanonicalHostName());
            System.out.println("ip1.isReachable(2000) : " + ip1.isReachable(2000));
            System.out.println("ip2.isReachable(2000) : " + ip2.isReachable(2000));
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printInet4AddressInfo(String label, Inet4Address ip) {
        System.out.println("\n--- " + label + " ---");
        System.out.println("Address: " + Arrays.toString(ip.getAddress()));
        System.out.println("Host Address: " + ip.getHostAddress());
        System.out.println("isAnyLocalAddress: " + ip.isAnyLocalAddress());
        System.out.println("isLinkLocalAddress: " + ip.isLinkLocalAddress());
        System.out.println("isLoopbackAddress: " + ip.isLoopbackAddress());
        System.out.println("isMCGlobal: " + ip.isMCGlobal());
        System.out.println("isMCLinkLocal: " + ip.isMCLinkLocal());
        System.out.println("isMCNodeLocal: " + ip.isMCNodeLocal());
        System.out.println("isMCOrgLocal: " + ip.isMCOrgLocal());
        System.out.println("isMCSiteLocal: " + ip.isMCSiteLocal());
        System.out.println("isMulticastAddress: " + ip.isMulticastAddress());
        System.out.println("isSiteLocalAddress: " + ip.isSiteLocalAddress());
        System.out.println("hashCode: " + ip.hashCode());
        System.out.println("getHostName: " + ip.getHostName());
        System.out.println("getCanonicalHostName: " + ip.getCanonicalHostName());
    }
}