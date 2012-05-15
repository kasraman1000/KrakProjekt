package routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import models.Loader;
import models.PathPreface;
import errorHandling.ClientInputException;
import errorHandling.NoSuchAddressException;
import errorHandling.NoSuchRoadnameException;
import errorHandling.ServerStartupException;


/**
 * Finds the directed edges associated with roadname/number/cityname/zipcode
 * 
 * @author Group 1, B-SWU, 2012E
 * 
 */
public class EdgeParser {
	private static HashMap<String, Bag<KrakEdge>> edgeMap = new HashMap<String, Bag<KrakEdge>>();
	private static HashMap<String, Integer> zipCodeMap;

	/**
	 * Builds up the structure for finding directed edges
	 * 
	 * @param edges All directed edges for navigation
	 */
	public static void build(ArrayList<KrakEdge> edges)
	{
		for(KrakEdge e : edges){
			String roadName = e.getName().toLowerCase();

			if (edgeMap.containsKey(roadName)) {
				edgeMap.get(roadName).add(e); // Add this edge under the roadname
			}
			else {
				// new roadname, create new bag and add edge
				Bag<KrakEdge> bag = new Bag<KrakEdge>();
				bag.add(e);
				edgeMap.put(roadName, bag);
			}
		}
		zipCodeMap = Loader.getZipCodeMap();
		System.out.println("Total roadnames: " + edgeMap.size());
	}

	/**
	 * Finds the KrakEdge corresponding to a stringArray of address search parameters
	 *  
	 * @param address The string array returned from AddressParser.parse()
	 * @return A PathPreface with the corresponding KrakEdges
	 */
	public static PathPreface findPreface(String[] address) throws ClientInputException {
		if (edgeMap.containsKey(address[0].toLowerCase())) {
			Iterator<KrakEdge> edges = edgeMap.get(address[0].toLowerCase()).iterator();
			ArrayList<KrakEdge> results = new ArrayList<KrakEdge>();

			// Find the house number (if none specified, default to 1)
			int houseNumber;
			if (address[1].length() > 0) houseNumber = Integer.parseInt(address[1]);
			else houseNumber = 1;

			// Find the zip code (if specified)
			int zipcode;
			if (address[3].length() > 0) zipcode = Integer.parseInt(address[3]);
			// else, derive from city name
			else if (address[4].length() > 0 && zipCodeMap.containsKey(address[4].toLowerCase())) { 
				zipcode = zipCodeMap.get(address[4].toLowerCase());
			}
			else zipcode = 0;

			// For every edge, check if housenumber (and if specified, zipcode) matches, 
			// and add to result array
			while (edges.hasNext()) {
				KrakEdge ke = edges.next();
				int fromHHN = ke.gethFromHouseNumber();
				int toHHN = ke.gethToHouseNumber();
				int fromVHN = ke.getvFromHouseNumber();
				int toVHN = ke.getvToHouseNumber();
				if(fromHHN > toHHN){
					int swap = fromHHN;
					fromHHN = toHHN;
					toHHN = swap;
				}
				if(fromVHN > toVHN){
					int swap = fromVHN;
					fromVHN = toVHN;
					toVHN = swap;
				}


				if (((houseNumber >= fromHHN && houseNumber <= toHHN) ||
						(houseNumber >= fromVHN && houseNumber <= toVHN)) &&
						(zipcode == 0 || (Integer.parseInt(address[3]) == ke.getvPost() || Integer.parseInt(address[3]) == ke.gethPost())))
					results.add(ke);
			}

			// Build the PathPreface object
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
				throw new NoSuchAddressException(); // if no result is found
			}
		}
		else{
			throw new NoSuchRoadnameException(address[0]); // if roadname doesn't match
		}
	}

	public static void main(String[] args) {
		try {	
			// set up data
			System.out.println("Attempting to load...");
			//Loader.load("kdv_node_unload.txt","kdv_unload.txt", "zip_codes.txt");
						Loader.load("src\\kdv_node_unload.txt","src\\kdv_unload.txt", "zip_codes.txt");
			System.out.println("Loading complete!");
			System.out.println("Attempting to build EdgeParser...");
			EdgeParser.build(Loader.getEdgesForTranslator());
			System.out.println("EdgeParser built!");

			String[] address = {"Vordingborgvej","2","","",""};

			try {
				System.out.println(EdgeParser.findPreface(address));
			} catch (ClientInputException e) {
				System.out.println("Address not found!");
				e.printStackTrace();
			}
		} 
		catch (ServerStartupException e1) {
			e1.printStackTrace();
		}
	}
}