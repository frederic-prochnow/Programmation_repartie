package tcp_cote_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client_echo {

	public static void main(String[] args){
		try {
			Socket client = new Socket("localhost",7);
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()),true);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			
			String s = "Ceci est la plus petite histoire du monde ! ";
			printWriter.println(s + "FIN");
				
			while(bufferedReader.ready())
				System.out.println(bufferedReader.readLine());
			
			client.close();
			printWriter.close();
			bufferedReader.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
