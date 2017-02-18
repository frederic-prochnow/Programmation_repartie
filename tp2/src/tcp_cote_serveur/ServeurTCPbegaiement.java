package tcp_cote_serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
/**
 * @author Frederic PROCHNOW
 */
public class ServeurTCPbegaiement {
 
   public static void main(String[] test) {
  
     final ServerSocket serveurSocket  ;
     final Socket clientSocket ;
     final BufferedReader in;
     final PrintWriter out;
     final Scanner sc=new Scanner(System.in);
     
     System.out.println("_____SERVEUR TCP_____");
     System.out.println("__SERVICE BEGAIEMENT_\n");
  
     try {
    	 int port = 9876;
    	 serveurSocket = new ServerSocket(port);
    	 clientSocket = serveurSocket.accept();
    	 out = new PrintWriter(clientSocket.getOutputStream());
    	 in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
    	 System.out.println("Client connecté sur "+ clientSocket.getInetAddress() +"/"+ clientSocket.getPort());
    	
    	 Thread recevoir= new Thread(new Runnable() {
    		 String msg ;
    		 String niveaubegai,messagebegai;
    		 @Override
    		 public void run() {
    			 try {
    				 msg = in.readLine();
    				 while(msg!=null){
    					 niveaubegai = "";messagebegai = "";
    					 int cpt=0;
        				 while(msg.charAt(cpt)!=':' && msg.length() != cpt){
        					 niveaubegai += msg.charAt(cpt);
        					 cpt++;
        				 }
        				 cpt+=1;
        				 for(int j=cpt;j<msg.length();j++){
        					 messagebegai += msg.charAt(j);
        				 }
        				 System.out.println("Niveaux de begaiements : "+ niveaubegai);
        				 System.out.println("Message a transformer  : "+ messagebegai);
    					 msg = in.readLine();
    					 String str = "0";
    					 int n = 0 ;
    						try{
    							n = Integer.parseInt(niveaubegai);
    						}catch(NumberFormatException e){
    							str = "erreur";
    						}
    						// TRAITEMENT DE LA RECEPTION
    						if (n != 0) {
    							String[] tab = new String[10];
    							int nbTAB = 0;
    							for (int i = 0; i < messagebegai.length(); i++) {
    								char c = messagebegai.charAt(i);
    								if (c != ' ') {
    									if (tab[nbTAB] == null)
    										tab[nbTAB] = "";
    									tab[nbTAB] += c;
    								}
    								if (c == ' ') {
    									nbTAB++;
    								}
    							}
    							for (int l = 0; l <= nbTAB; l++) {
    								for (int k = 0; k < n; k++) {
    									str += tab[l] + " ";
    								}
    							}
    						}
    						System.out.println("Messages transformer : "+str);
    						OutputStream output = clientSocket.getOutputStream();
    						output.write(str.getBytes());
    						out.flush();
    				 	}
    				 
    				 //System.out.println("Client déconecté");
    				 out.close();
    				 clientSocket.close();
    				 serveurSocket.close();
    			 } catch (IOException e) {
    				 e.printStackTrace();
    			 } finally {
    				 out.close();
    				 try {
						clientSocket.close();
						serveurSocket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
    			 }
    		 }
    	 });
    	 recevoir.start();
    	 
    	 
    	 /*Thread envoi= new Thread(new Runnable() {
    		 String msg;
    		 @Override
    		 public void run() {
    			 while(true){
    				 //msg = sc.nextLine();
    				 out.println(msg);
    				 out.flush();
    			 }
    		 }
    	 });
    	 envoi.start();*/
      	}catch (IOException e) {
      		e.printStackTrace();
      	}
   	}
}