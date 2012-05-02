package routing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import models.Loader;
import models.PathPreface;
import routing.Bag;
import routing.KrakEdge;


public class EdgeParser {

	private static HashMap<String, Bag<KrakEdge>> edgeMap = new HashMap<String, Bag<KrakEdge>>();

	public static void build(ArrayList<KrakEdge> edges)
	{
		for(KrakEdge e : edges){
			String roadName = e.getName();
			//System.out.println(roadName + ",\t" + roadName.hashCode());

			if (edgeMap.containsKey(roadName)) {
				edgeMap.get(roadName).add(e); // Add this edge under the roadname
			}
			else {
				edgeMap.put(roadName, new Bag<KrakEdge>()); // new roadname, create new bag
			}

		}
		System.out.println("Total roadnames: " + edgeMap.size());
	}

	/**
	 * Finds the KrakEdge corresponding to a stringArray of address search parameters 
	 * @return A PathPreface with the corresponding KrakEdges
	 */
	public static PathPreface findPreface(String[] address) throws Exception { //TODO: Fix exception til noget mere specifikt (AddressNotFoundException?)
		if (edgeMap.containsKey(address[0])) {
			Iterator<KrakEdge> edges = edgeMap.get(address[0]).iterator();
			ArrayList<KrakEdge> results = new ArrayList<KrakEdge>();
			
			// Find the house number (if none specified, default to 1)
			int houseNumber;
			if (address[1].length() > 0) houseNumber = Integer.parseInt(address[1]);
			else houseNumber = 1;
			
			
			
			
			//TODO: add some zipcode selection as well
			if (address[3].length() > 0) {
				while (edges.hasNext()) {
					KrakEdge ke = edges.next();
					System.out.println(ke);
					if (((houseNumber >= ke.gethFromHouseNumber() && houseNumber <= ke.gethToHouseNumber()) ||
							(houseNumber >= ke.getvFromHouseNumber() && houseNumber <= ke.getvToHouseNumber())) &&
							(Integer.parseInt(address[3]) == ke.getvPost() || Integer.parseInt(address[3]) == ke.gethPost()))
						results.add(ke);
				}
			}
			else {
				while (edges.hasNext()) {
					KrakEdge ke = edges.next();
					System.out.println(ke);
					if ((houseNumber >= ke.gethFromHouseNumber() && houseNumber <= ke.gethToHouseNumber()) ||
							(houseNumber >= ke.getvFromHouseNumber() && houseNumber <= ke.getvToHouseNumber()))
						results.add(ke);
				}
			}
			
			System.out.println("Bag.size(): " + edgeMap.get(address[0]).size());
			System.out.println("Results.size(): " + results.size());
			System.out.println("Results: ");
			
			Iterator<KrakEdge> i = results.iterator();
			while (i.hasNext()) 
				System.out.println(i.next());
			
			System.out.println();
			
			if (!results.isEmpty()) {
				
				KrakEdge k1 = results.remove(0);
				KrakEdge k2 = null;
				if (!results.isEmpty())
					if (results.get(0).from() == k1.to() || results.get(0).to() == k1.from())
						k2 = results.get(0);
				
				// return result;
				return new PathPreface(k1, k2, houseNumber);
			}
			else{
				System.out.println("EdgeParser.findPreface() - No result found");
				throw new Exception(); // if no result is found
			}
		}
		else{
			System.out.println("EdgeParser.findPreface() - Roadname doesn't match");
			throw new Exception(); // if roadname doesn't match
		}
	}
	
	public static void main(String[] args) {
		try {
			
			
			// set up data
			System.out.println("Attempting to load...");

			Loader.load("kdv_node_unload.txt","kdv_unload.txt");
//			Loader.load("src\\kdv_node_unload.txt","src\\kdv_unload.txt");

			System.out.println("Loading complete!");
			System.out.println("Attempting to build EdgeParser...");
			EdgeParser.build(Loader.getEdgesForTranslator());
			System.out.println("EdgeParser built!");
			
			String tempAdr = "Annasvej 14";
			String[] address = AddressParser.parseAddress(tempAdr);
			
			for(int index=0; index<address.length; index++){
				System.out.println(address[index] +",");
			}
			System.out.println(" but should be something like: " + tempAdr);
			
			
//			String[] address = {"Annasvej","14","","",""};
			//System.out.println(address[0]);
			try {
				System.out.println(EdgeParser.findPreface(address));
			} catch (Exception e) {
				System.out.println("Address not found!");
				e.printStackTrace();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
