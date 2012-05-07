/**
 * 
 */
package models;

import routing.*;

/**
 * @author Yndal
 *
 */
public class RouteFinder {
	KrakEdgeWeightedDigraph graph;
	
	public Road[] getRoute(String from, String to, boolean isLengthWeighted){
		String[] fromAddressArray = AddressParser.parseAddress(from);
		String[] toAddressArray = AddressParser.parseAddress(to);
		PathPreface pathPrefaceFrom = null;
		PathPreface pathPrefaceTo = null;
		
		try {
			pathPrefaceFrom = EdgeParser.findPreface(fromAddressArray);
			pathPrefaceTo = EdgeParser.findPreface(toAddressArray);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//TODO For debugging
		if(pathPrefaceFrom == null) System.err.println("Controller.getRoadAndRoute() - pathPrefaceFrom == null at line 108!!!!");
		if(pathPrefaceTo == null) System.err.println("Controller.getRoadAndRoute() - pathPrefaceTo == null at line 109!!!!");
		
		//TODO
		//TODO
		//TODO What if getEdge1 is null?
		//TODO
		//TODO
		
		
		//Randomly chosen because of later tests of the exact id (performed in EdgesAndRoadsConverter.checkStartAndTargetOfDijkstra())
		int firstNodeId; 
		int lastNodeId; 
		
		if(pathPrefaceFrom.getEdge1() != null) firstNodeId = pathPrefaceFrom.getEdge1().to();
		else firstNodeId = pathPrefaceFrom.getEdge2().to();

		if(pathPrefaceTo.getEdge1() != null) lastNodeId = pathPrefaceTo.getEdge1().to();
		else lastNodeId = pathPrefaceFrom.getEdge2().to();

		//Load the graph into Dijkstra and find the path
		DijkstraSP dij = new DijkstraSP(Loader.getGraph());
		Stack<KrakEdge> routeEdges = dij.findRoute(firstNodeId,  lastNodeId, isLengthWeighted);

		//Convert from stack to []
		KrakEdge[] routeEdgesArray = EdgesAndRoadsConverter.convertRouteStackToArray(routeEdges);
		
		//Correct start and end of [] and do the house number thing 
		Road[] route = null;
		try {
			route = EdgesAndRoadsConverter.checkStartAndTargetOfDijkstra(routeEdgesArray, pathPrefaceFrom, pathPrefaceTo);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		
		return route;
	}
	
	
	public Road[] getRoute(KrakEdge start, KrakEdge target, boolean isLengthWeighted){
		
		
		
		
		return null;
	}
	
	
	

}
