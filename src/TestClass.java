import java.io.IOException;
import java.util.ArrayList;

// This Class intentionally left blank
// NOT ANYMORE!
public class TestClass {
	
	
	public static void main(String[] args) {
		KDTree kdtree = new KDTree(2);
		
		try {
			System.out.println("Loading in nodes from txts...");
			ArrayList<Node> nodes = KrakLoader.load(
					"C:\\Users\\Yndal\\Desktop\\Dropbox\\1. årsprojekt - gruppe 1\\krak-data\\kdv_node_unload.txt", 
					"C:\\Users\\Yndal\\Desktop\\Dropbox\\1. årsprojekt - gruppe 1\\krak-data\\kdv_unload.txt");
			
			System.out.println("Building kdtree");
			kdtree.build(nodes);
			XML xml = new XML();
			
			//Supposed to be sealand
			double[] lowerRange = {600000,6050000};
			double[] upperRange = {700000,6100000};
			
			System.out.println("Searching for roads... ");
			Road[] roads = kdtree.search(lowerRange, upperRange);
			
			System.out.println("Length of road: " + roads.length);
			
			System.out.println("Creating XML file!...");
			xml.createFile(roads, "C:\\Users\\Yndal\\Desktop\\sjaelland.xml");
			System.out.println("Done!");
		}
		catch (IOException e) {
			
		}
		
	}

}
