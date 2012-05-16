package models;

import routing.*;
import errorHandling.*;

/**
 * Mostly a class to ease the design:
 * Will manage the process of finding a route 
 * 
 * @author Group 1, B-SWU, 2012E
 *
 */
public class RouteFinder {
	private KrakEdgeWeightedDigraph graph;
	
	/**
	 * The Constructor 
	 * @param graph The graph used to find the route
	 */
	public RouteFinder(KrakEdgeWeightedDigraph graph){
		this.graph = graph;
	}

	/**
	 * Will find the route using address Strings
	 * 
	 * @param from The place to start
	 * @param to The place to end
	 * @param isLengthWeighted Should the route depend on the length or time traveled
	 * @return The route as an array of Roads
	 * @throws ClientInputException If the input is invalid
	 */
	public Road[] getRoute(String from, String to, boolean isLengthWeighted) throws ClientInputException{
		String[] fromAddressArray = AddressParser.parseAddress(from);
		String[] toAddressArray = AddressParser.parseAddress(to);
		
		PathPreface pathPrefaceFrom = EdgeParser.findPreface(fromAddressArray);
		PathPreface pathPrefaceTo = EdgeParser.findPreface(toAddressArray);
		
		Road[] result = getRoute(pathPrefaceFrom, pathPrefaceTo, isLengthWeighted);
		
		return result;
	}
	
	/**
	 * Will find the route using Prefaces for start and destination
	 * 
	 * @param pathPrefaceFrom A PathPreface showing where to start
	 * @param pathPrefaceTo A PathPreface showing where to end
	 * @param isLengthWeighted Tells if the route should depend on the length of travel time
	 * @return An ArrayList of Roads to represent the route
	 */
	public Road[] getRoute(PathPreface pathPrefaceFrom, PathPreface pathPrefaceTo, boolean isLengthWeighted){
		//Randomly chosen because of later tests of the exact id (performed in EdgesAndRoadsConverter.checkStartAndTargetOfDijkstra())
		int firstNodeId; 
		int lastNodeId; 
		
		//Set starting point
		if(pathPrefaceFrom.getEdge1() != null) firstNodeId = pathPrefaceFrom.getEdge1().to();
		else firstNodeId = pathPrefaceFrom.getEdge2().to();

		//Set ending point
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