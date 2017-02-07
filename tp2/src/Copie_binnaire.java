import java.io.*;

public class Copie_binnaire {

	public void main(String[] args){
		File src;
		File desc;
		
		if(args.length != 2)
			System.out.println("Erreur Arguments");
		
		src = new File(args[0]);
		desc = new File(args[1]);
		
		if(src.exists() && !desc.exists()){
			InputStream inputStream = new InputStream(src);
			OutputStream outputStream = new OutputStream(desc);
		}
		
	}

}
