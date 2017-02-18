package udp_simple;
/**
 * @author Frederic PROCHNOW
 */
import java.io.*;
import java.net.*;

public class ClientUDP {
	
	private DatagramSocket aSocket;
	
	ClientUDP(){
		try {
	        aSocket = new DatagramSocket();
	        String string = "res";
	        byte [] m = string.getBytes();
	        InetAddress aHost = InetAddress.getByName("localhost"); // @ destination
	        int serverPort = 9876;
	        DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
	        aSocket.send(request);
	        byte[] buffer = new byte[1000];
	        DatagramPacket reply = new DatagramPacket(buffer, buffer.length);   
	        aSocket.receive(reply);
	        System.out.println("Reponse du serveur: \n" + new String(reply.getData(),0,reply.getLength()));    
	    }catch (SocketException e){
	    	System.out.println("Socket: " + e.getMessage());
	    }catch (IOException e){
	    	System.out.println("IO: " + e.getMessage());
	    }finally {
	    	if(aSocket != null) 
	    		aSocket.close();
	    }
	}

	public static void main(String args[]){
		System.out.println("_____CLIENT UDP_____\n");
		new ClientUDP();
	} 
}