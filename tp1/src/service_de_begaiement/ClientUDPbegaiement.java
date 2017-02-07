package service_de_begaiement;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientUDPbegaiement {

	public static void main(String args[]) {
		System.out.println("Client...\n");
		Scanner sc = new Scanner(System.in);
		System.out.println("Quel est votre niveau de begaiement (entre 0 et 9)?");
		String niveaubegai = sc.nextLine();
		System.out.println("Quel est votre message ?");
		String messagebegai = sc.nextLine();
		sc.close();
		DatagramSocket aSocket = null;
		try {
			// ENVOIE DES DONNES AU SERVEUR
			aSocket = new DatagramSocket();
			String string = niveaubegai + messagebegai;
			byte[] m = string.getBytes();
			InetAddress aHost = InetAddress.getByName("localhost"); // @
																	// destination
			int serverPort = 9876;
			DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
			aSocket.send(request);

			// RECEPTION REPONSE DU SERVEUR
			byte[] buffer = new byte[1000];
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(reply);
			System.out.println("Reply: " + new String(reply.getData(), 0, reply.getLength()));

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (aSocket != null)
				aSocket.close();
		}
	}
}