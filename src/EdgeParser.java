import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import routing.Bag;
import routing.KrakEdge;


public class EdgeParser {

	private static HashMap<String, Bag<KrakEdge>> edgeMap = new HashMap<String, Bag<KrakEdge>>();

	public static void build(ArrayList<KrakEdge> edges)
	{
		for(KrakEdge e : edges){
			String roadName = e.getName();

			if (edgeMap.containsKey(roadName)) {
				edgeMap.get(roadName).add(e); // Add this edge under the roadname
			}
			else {
				edgeMap.put(roadName, new Bag<KrakEdge>());
			}

		}
	}

	/**
	 * Finds a KrakEdge corresponding to a stringArray of address search parameters 
	 * @return A KrakEdge corresponding 
	 */
	public static KrakEdge findEdge(String[] address) throws Exception { //TODO: Fix exception til noget mere specifikt
		KrakEdge result;
		
		if (edgeMap.containsKey(address[0])) {
			
		}
		else throw new Exception();
		
		return result;
	}
}

