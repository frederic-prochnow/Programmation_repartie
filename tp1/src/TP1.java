import java.awt.datatransfer.StringSelection;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;

public class TP1 {

	public static void main(String[] args) {
		Enumeration<NetworkInterface> nets;
		try {
			nets = NetworkInterface.getNetworkInterfaces();
			 for (NetworkInterface netint : Collections.list(nets))
		            showInformations(netint);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public static void showInformations(NetworkInterface net){
		System.out.println("-----------------------------------------------");
		System.out.println("INFORMATIONS DE " + net.getDisplayName());
		System.out.println("-----------------------------------------------");
		System.out.println("Nom_____________ : " + net.getName());
		Enumeration<InetAddress> inetAddresses = net.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            System.out.printf("Adresse_________ : %s\n", inetAddress);
        }
		try {
			System.out.println("Valeur de la MTU : "+ net.getMTU());
		} catch (SocketException e) {
			System.out.println("Valeur de la MTU : données indisponible");
	    }
		System.out.println("\n");
	}
}
