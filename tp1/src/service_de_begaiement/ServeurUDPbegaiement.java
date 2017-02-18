package service_de_begaiement;
/**
 * @author Frederic PROCHNOW
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServeurUDPbegaiement {

	private DatagramSocket dgSocket;

	ServeurUDPbegaiement(int pSrv) throws IOException {
		dgSocket = new DatagramSocket(pSrv);
	}

	void go() throws IOException {
		byte[] buffer = new byte[1000];
		DatagramPacket dgPacket = new DatagramPacket(buffer, buffer.length);
		String str = "";
		
		int n = 0 ;
		while (true) {
			// RECEPTION MESSAGE DU CLIENT
			dgSocket.receive(dgPacket);
			System.out.println("Datagramme recu de " + dgPacket.getSocketAddress());
			String reception = new String(dgPacket.getData(), 0, dgPacket.getLength());
			int cpt=0;
			String niveaubegai = "",messagebegai = "";
			while(reception.charAt(cpt)!=':' && reception.length() != cpt){
				niveaubegai += reception.charAt(cpt);
				cpt++;
			}
			cpt+=1;
			for(int j=cpt;j<reception.length();j++){
				messagebegai += reception.charAt(j);
			}
			System.out.println("Le serveur envoie " + niveaubegai + " fois le message \"" + messagebegai+"\"");
			str = "0";
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
			// REPONSE AU CLIENT
			byte[] buf = str.getBytes();
			dgPacket.setSocketAddress(dgPacket.getSocketAddress());
			dgPacket.setData(buf, 0, buf.length);
			dgSocket.send(dgPacket);
		}
	}

	public static void main(String[] args) throws IOException {
		System.out.println("_____SERVEUR UDP_____");
		System.out.println("__SERVICE BEGAIEMENT_\n");
		final int DEFAULT_PORT = 9876;
		new ServeurUDPbegaiement(args.length == 0 ? DEFAULT_PORT : Integer.parseInt(args[0])).go();
	}
}
