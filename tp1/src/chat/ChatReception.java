package chat;
/**
 * @author Frederic PROCHNOW
 */

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

class ChatReception extends Thread {
	InetAddress  adresse;
	int port;
	MulticastSocket sReception;

	ChatReception(InetAddress adresse, int port)  throws Exception { 
	   this.adresse = adresse;
	   this.port = port;
	   sReception = new MulticastSocket(port);
	   sReception.joinGroup(adresse);
	   start();
	}

	public void run() {
		DatagramPacket message;
		byte[] contenuMessage;
		String texte;
    
		while(true) {
			contenuMessage = new byte[1024];
			message = new DatagramPacket(contenuMessage, contenuMessage.length);
			try {
				sReception.receive(message);
				texte = (new DataInputStream(new ByteArrayInputStream(contenuMessage))).readUTF();
				if (!texte.startsWith("Chat")) continue;
				System.out.println(texte);
			}
			catch(Exception e) {
	    		System.out.println(e);
			}
		}
	}
	
	public static void main(String[] arg) throws Exception{ 
		InetAddress adresses = InetAddress.getByName("224.0.0.24");
		int port = 9876; 
		new ChatReception(adresses, port);
   }
}
