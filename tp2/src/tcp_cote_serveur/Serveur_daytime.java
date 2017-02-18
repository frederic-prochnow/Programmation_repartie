package tcp_cote_serveur;
/**
 * @author Frederic PROCHNOW
 */
import java.net.*;
import java.io.*;

public class Serveur_daytime {
	
	public static void main(String args[]) {
		ServerSocket ss;
		Socket sc;
		try {
			ss = new ServerSocket(9876);
			while (true) {
				sc = ss.accept();
				PrintWriter out = new PrintWriter(sc.getOutputStream(), true);
				out.println(new java.util.Date());
				sc.close();
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}