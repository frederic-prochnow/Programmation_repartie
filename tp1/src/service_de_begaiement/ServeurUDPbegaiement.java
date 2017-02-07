package service_de_begaiement;

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
			System.out.println("Datagram received from " + dgPacket.getSocketAddress());
			String reception = new String(dgPacket.getData(), 0, dgPacket.getLength());
			String niveaubegai = reception.substring(0, 1);
			String messagebegai = reception.substring(1);
			System.out.println(">>" + niveaubegai + " X le message :" + messagebegai);
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
				int nv = Integer.parseInt(niveaubegai);
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
					for (int k = 0; k < nv; k++) {
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
		System.out.println("SERVEUR...\n");
		final int DEFAULT_PORT = 9876;
		new ServeurUDPbegaiement(args.length == 0 ? DEFAULT_PORT : Integer.parseInt(args[0])).go();
	}
}
