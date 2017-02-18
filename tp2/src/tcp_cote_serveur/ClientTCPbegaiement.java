package tcp_cote_serveur;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
/**
 * @author Frederic PROCHNOW
 */
public class ClientTCPbegaiement {

   public static void main(String[] args) {
	   
	   System.out.println("_____CLIENT TCP_____");
	   System.out.println("_SERVICE BEGAIEMENT_\n");
      
      final Socket clientSocket;
      final BufferedReader in;
      final PrintWriter out;
      final Scanner sc = new Scanner(System.in);
  
      try {
    	  int port = 9876;
    	  clientSocket = new Socket("127.0.0.1",port);
    	  //envoie
    	  out = new PrintWriter(clientSocket.getOutputStream());
    	  // reception
    	  in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    	  
    	  Thread envoyer = new Thread(new Runnable() {
    		  String msg;
              public void run() {
            	  //while(true){
            		  System.out.println("Entrer une phrase :");
            		  msg = sc.nextLine();
            		  out.println(msg);
            		  out.flush();
            	  //}
              }
    	  });
    	  envoyer.start();
   
    	  Thread recevoir = new Thread(new Runnable() {
    		  String msg;
    		  @Override
    		  public void run() {
    			  try {
    				  msg = in.readLine();
    				  while(msg!=null){
    					  System.out.println("Serveur : "+msg);
    					  msg = in.readLine();
    				  }
    				  System.out.println("Serveur déconecté");
    				  out.close();
    				  clientSocket.close();
    			  } catch (IOException e) {
    				  e.printStackTrace();
    			  }
    		  }
    	  });
    	  recevoir.start();
   
      	} catch (IOException e) {
      		e.printStackTrace();
      	}
   }
}