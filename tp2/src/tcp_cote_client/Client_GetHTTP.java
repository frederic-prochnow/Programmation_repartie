package tcp_cote_client;
/**
 * @author Frederic PROCHNOW
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Client_GetHTTP {


		public static void main(String[] args) throws Exception {
			String url = "http://127.0.0.1/index.html";

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");


			int response = con.getResponseCode();
			System.out.println("Requête 'GET' sur l'url : " + url);
			System.out.println("Code de reponse de la page : " + response);
			System.out.println("Code source de la page :\n");
			
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String ligne;
			StringBuffer buff = new StringBuffer();

			while ((ligne = in.readLine()) != null) {
				buff.append(ligne);
			}
			in.close();
			System.out.println(buff.toString());
		}

}
