package copie_de_fichier;
import java.io.*;

public class Copie_texte {

	public static void main(String[] args){
		File src;
		File desc;
		
		BufferedReader bufferedReader = null;
		PrintWriter printWriter = null;
		
		/*if(args.length != 2)
			System.out.println("Erreur Arguments");*/
		
		src = new File("src/copie_de_fichier/Test1.txt");
		desc = new File("src/copie_de_fichier/Test2.txt");
		
		if(src.exists()){
			try {
				bufferedReader = new BufferedReader(new FileReader(src));
				printWriter= new PrintWriter(new FileWriter(desc));
				String s = "";
				while((s = bufferedReader.readLine()) != null){
					printWriter.println(s);
				}
				bufferedReader.close();
				printWriter.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("src n'existe pas");
		}
		
	}

}
