package tcp_cote_client;
/**
 * @author Frederic PROCHNOW
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client_daytime {

	public static void main(String[] args) {
			try {
				
				Socket client = new Socket("localhost",13);
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				
				while(bufferedReader.ready())
					System.out.println(bufferedReader.readLine());
				
				client.close();
				bufferedReader.close();
				
			} catch (UnknownHostException e) {
				e.printStackTrace();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}

}
