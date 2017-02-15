package copie_de_fichier;
import java.io.*;

public class Copie_binaire {

	public static void main(String[] args){
		File src;
		File desc;
		
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		/*if(args.length != 2)
			System.out.println("Erreur Arguments");*/
		
		src = new File("src/copie_de_fichier/Test1.txt");
		desc = new File("src/copie_de_fichier/Test2.txt");
		
		if(src.exists()){
			try {
				inputStream = new BufferedInputStream(new FileInputStream(src));
				outputStream = new BufferedOutputStream(new FileOutputStream(desc));
				int n = 0;
				byte[] buf = new byte[50];
				while((n = inputStream.read(buf)) >= 0){
					outputStream.write(buf,0,n);
				}
				inputStream.close();
				outputStream.close();
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
