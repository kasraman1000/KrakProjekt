import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import routing.AddressParser;
import errorHandling.AddressInputFormatException;

public class TestAddressParser {

	public static void main(String[] args) throws FileNotFoundException {
		
		// reading in
		File file = new File("src\\BlackBoxInput.txt"); // test input file
		FileInputStream fis =new FileInputStream(file);
		Scanner scanner = new Scanner(fis, "UTF-8");
		
		ArrayList<String> input = new ArrayList<String>();
		while (scanner.hasNextLine()) 
			input.add(scanner.nextLine());
		
		System.out.println("Amount of lines: " + input.size());
		
		try {
			// run though and check every pair for equality
			for (int i = 0; i < input.size(); i++) {
				// parse the input string
				String[] results = AddressParser.parseAddress(input.get(i).trim());

				// construct whole string of result
				String result = "";
				for (int j = 0; j < results.length; j++) {
					if (j != 0) result += "#";
					result += results[j];
				}

				System.out.println("Input:  " + input.get(i++));
				System.out.println("Result: " + result);
				System.out.println("Chkstr: " + input.get(i));
				if (result.equals(input.get(i)))
					System.out.println("MATCH!");
				else
					System.out.println("NO MATCH;");
				System.out.println();
			}
		} catch (AddressInputFormatException e) {
			e.printStackTrace();
		}


	}

}
