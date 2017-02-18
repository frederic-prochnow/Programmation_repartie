package udp_simple;
/**
 * @author Frederic PROCHNOW
 */
import java . io . IOException ;
import java . net . DatagramPacket ;
import java . net . DatagramSocket ;

public class ServeurUDP {
	
	private DatagramSocket dgSocket ;
	
	ServeurUDP ( int pSrv ) throws IOException {
		dgSocket = new DatagramSocket ( pSrv ) ;
	}
	
	void go() throws IOException {
		DatagramPacket dgPacket = new DatagramPacket (new byte[0] , 0) ;
		String str ;
		while(true){
			dgSocket.receive(dgPacket);
			System.out.println("Datagramme recu de " + dgPacket.getSocketAddress());
			dgPacket.setSocketAddress(dgPacket.getSocketAddress());
			str = new java.util.Date().toString() + "\n";
			byte[] bufDate = str.getBytes();
			dgPacket.setData(bufDate,0,bufDate.length);
			dgSocket.send(dgPacket);
		}
	}

	public static void main (String[] args) throws IOException {
		System.out.println("_____SERVEUR UDP_____\n");
		final int DEFAULT_PORT = 9876 ;
		new ServeurUDP(args.length == 0 ? DEFAULT_PORT : Integer.parseInt(args[0])).go();
	}
}
