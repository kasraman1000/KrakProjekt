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
	private KrakEdgeWeightedDigraph graph;
	
	public RouteFinder(KrakEdgeWeightedDigraph graph){
		this.graph = graph;
	}
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
		
		Road[] result = getRoute(pathPrefaceFrom, pathPrefaceTo, isLengthWeighted);
		
		return result;
	}
	
	
	public Road[] getRoute(PathPreface pathPrefaceFrom, PathPreface pathPrefaceTo, boolean isLengthWeighted){
		//Randomly chosen because of later tests of the exact id (performed in EdgesAndRoadsConverter.checkStartAndTargetOfDijkstra())
		int firstNodeId; 
		int lastNodeId; 
		
		if(pathPrefaceFrom.getEdge1() != null) firstNodeId = pathPrefaceFrom.getEdge1().to();
		else firstNodeId = pathPrefaceFrom.getEdge2().to();

		if(pathPrefaceTo.getEdge1() != null) lastNodeId = pathPrefaceTo.getEdge1().to();
		else lastNodeId = pathPrefaceFrom.getEdge2().to();

		
		//Load the graph into Dijkstra and find the path
		DijkstraSP dij = new DijkstraSP(graph);
		Stack<KrakEdge> routeEdges = dij.findRoute(firstNodeId,  lastNodeId, isLengthWeighted);

		//Convert from stack to []
		KrakEdge[] routeEdgesArray = EdgesAndRoadsConverter.convertRouteStackToArray(routeEdges);
		
		//Correct start and end of [] - and compute the exact length of the first and last road 
		Road[] route = null;
		try {
			route = EdgesAndRoadsConverter.checkStartAndTargetOfDijkstra(routeEdgesArray, pathPrefaceFrom, pathPrefaceTo);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		
		return route;
	}
}
