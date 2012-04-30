import java.util.ArrayList;
import java.util.HashMap;

import routing.DirectedEdge;


public class RoadTranslator {
	
	private static HashMap<Integer, ArrayList<CompleteRoad>> translatorMap = new HashMap<Integer, ArrayList<CompleteRoad>>();
	
	public static void build(ArrayList<DirectedEdge> edges)
	{
		for(DirectedEdge e : edges){
			if(HashMap.contains(e.getZipCode())){
				
			}
			else {
				translatorMap.put(e.getZipCode(), new ArrayList<CompleteRoad>());
				
			}
			
		}
	}
}
