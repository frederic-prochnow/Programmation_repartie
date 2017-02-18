package service_de_begaiement;
/**
 * @author Frederic PROCHNOW
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientUDPbegaiement {
	
	ClientUDPbegaiement(String string){
		DatagramSocket aSocket = null;
		try {
			// ENVOIE DES DONNES AU SERVEUR
			aSocket = new DatagramSocket();
			//concatenation des arguments en une phrase pour traitement serveur
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
			System.out.println("Reponse du serveur: \n" + new String(reply.getData(), 0, reply.getLength()));

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (aSocket != null)
				aSocket.close();
		}	
	}
	
	public static void client(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Pour acceder au mode simplifié taper 's', sinon taper 'n' :");
		String choix = sc.nextLine();
		char c;
		try{
			c = choix.charAt(0);
		}catch(StringIndexOutOfBoundsException e){
			c = 'n';
		}
		if(c=='s'||c=='S'){
			System.out.println("Quel est votre niveau de begaiement ?");
			String niveaubegai = sc.nextLine();
			System.out.println("Quel est votre message ?");
			String messagebegai = sc.nextLine();
			String string = niveaubegai + ":" + messagebegai;
			new ClientUDPbegaiement(string);
			System.out.println();
		} else{
			System.out.println("Ecrivez votre message au format \"nbrepetition:message\"");
			System.out.println("Quel est votre message ?");
			String messagebegai = sc.nextLine();
			new ClientUDPbegaiement(messagebegai);
		}
		sc.close();
	}
	
	public static void main(String args[]) {
		System.out.println("_____CLIENT UDP_____");
		System.out.println("_SERVICE BEGAIEMENT_\n");
		client();
	}
}