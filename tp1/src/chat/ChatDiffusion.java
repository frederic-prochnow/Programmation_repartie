package chat;
/**
 * @author Frederic PROCHNOW
 */

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

class ChatDiffusion extends Thread{
	   InetAddress  adresse;
	   int port;
	   MulticastSocket sDiffusion;
	   
	   ChatDiffusion(InetAddress adresse, int port) throws Exception {
	      this.adresse= adresse;
	      this.port = port;
	      sDiffusion = new MulticastSocket();
	      start();
	  }
	    
	  public void run() {
	    BufferedReader clavier;
	    
	    try {
	       clavier = new BufferedReader(new InputStreamReader(System.in));
	       while(true) {
				  String texte = clavier.readLine();
				  emettre(texte);
	       }
	    }
	    catch (Exception e) {
	       System.out.println(e);
	    }
	  } 

	  void emettre(String texte) throws Exception {
			byte[] contenuMessage;
			DatagramPacket message;
		
			ByteArrayOutputStream sortie = new ByteArrayOutputStream(); 
			texte = "Chat : " + texte ;
			(new DataOutputStream(sortie)).writeUTF(texte); 
			contenuMessage = sortie.toByteArray();
			message = new DatagramPacket(contenuMessage, contenuMessage.length, adresse, port);
			sDiffusion.send(message);
	  }
	  
	  public static void main(String[] arg) throws Exception{ 
			InetAddress adresses = InetAddress.getByName("224.0.0.24");
			int port = 9876; 
			new ChatDiffusion(adresses, port);
	   }
}
