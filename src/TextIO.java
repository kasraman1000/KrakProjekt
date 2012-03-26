import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class TextIO{

	public static void main(String[] args){
		write(read("test.txt"));
		
	}

	public static String[] read(String name){
		try {
			File file = new File(name);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			ArrayList<String> adresses = new ArrayList<String>();
			String adress;
			while((adress = reader.readLine()) != null){
				if(adress.equals(""))continue;
				adresses.add(adress);
			}
			String[] arrayOfAdresses = new String[adresses.size()];
			for(int i = 0 ; i < adresses.size() ; i++){
				arrayOfAdresses[i] = adresses.get(i);
			}
			reader.close();
			return arrayOfAdresses;
		} catch (Exception e) {
		}
		return null;

	}

	public static void write(String [] adresses){
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("printOfAdresses.txt"),"UTF-8"));
			for(int i = 0 ; i < adresses.length ; i++){
				out.write(adresses[i]);
				out.newLine();
				
			}
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}