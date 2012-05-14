/**
 * 
 */
package routing;

import models.PathPreface;
import models.Road;

/**
 * @author Yndal
 *
 */
public class EdgesAndRoadsConverter {
	private static final double RESET_XMIN;
	private static final double RESET_YMIN;
	private static final double RESET_XMAX;
	private static final double RESET_YMAX;
	private static double xMin;
	private static double yMin;
	private static double xMax;
	private static double yMax;
	
	static{
		RESET_XMIN = 1e9;
		RESET_YMIN = 1E9;
		RESET_XMAX = 0;
		RESET_YMAX = 0;
	}

	private static void resetCoordinateValues(){
		xMin = RESET_XMIN;
		yMin = RESET_YMIN;
		xMax = RESET_XMAX;
		yMax = RESET_YMAX;
	}

	private static KrakEdge divideKrakEdge(KrakEdge edge, boolean firstEdge, int houseNumber) {
		//The new values
		if(edge == null) System.out.println("EdgesAndRoadsConverter.divideKrakEdge() - Edge is null");
		String name = edge.getName();
		int vPost = edge.getvPost(); //TODO May not be true
		int hPost = edge.gethPost(); //TODO May not be true
		
		//Not defined yet
		int from;
		int to;
		double length;
		double time;
		double[] fromPoint = new double[2];
		double[] toPoint = new double[2];;
		int vFromHouseNumber;
		int vToHouseNumber;
		int hFromHouseNumber;
		int hToHouseNumber;
		
		//Used for calculations
		double distX = edge.getToPoint()[0]-edge.getFromPoint()[0];
		double distY = edge.getToPoint()[1]-edge.getFromPoint()[1];
		double factor;
		
		//The house is on the left side of the road
		if(houseNumber%2 == edge.getvFromHouseNumber()%2){
			factor = ((houseNumber-edge.getvFromHouseNumber())/(edge.getvToHouseNumber()-edge.getvFromHouseNumber()+1)); 
			
		//The house is on the right side of the road
		} else {
			factor = ((houseNumber-edge.gethFromHouseNumber())/(edge.gethToHouseNumber()-edge.gethFromHouseNumber()+1)); 
		}
		
		//It's the first edge
		if(firstEdge){
			factor = 1-factor;
			//Calculation of the new values (some remain untouched)
			from = edge.to();
			to = edge.to();
			fromPoint[0] = edge.getToPoint()[0] - (distX*factor);
			fromPoint[1] = edge.getToPoint()[1] - (distY*factor);
			toPoint = edge.getToPoint();
			
			//TODO May be wrong (odd or even regarding the casting to and int)
			vFromHouseNumber = (int) (edge.getvToHouseNumber() - ((edge.getvToHouseNumber()-edge.getvFromHouseNumber())*factor)); 
			vToHouseNumber = edge.getvToHouseNumber();
			
			//TODO May be wrong (odd or even regarding the casting to and int)
			hFromHouseNumber = (int) (edge.gethToHouseNumber() - ((edge.gethToHouseNumber()-edge.gethFromHouseNumber())*factor)); 
			hToHouseNumber = edge.gethToHouseNumber();
			length = edge.getLength()-edge.getLength()*factor;
			time = edge.getTime()-edge.getTime()*factor;
			
		//It's the last edge
		} else {
			//Calculation of the new values (some remain untouched)
			from = edge.from();
			to = edge.from();
			fromPoint = edge.getFromPoint();
			toPoint[0] = edge.getFromPoint()[0]+(edge.getToPoint()[0]-edge.getFromPoint()[0])*factor;
			toPoint[1] = edge.getFromPoint()[1]+(edge.getToPoint()[1]-edge.getFromPoint()[1])*factor;
			vFromHouseNumber = edge.getvFromHouseNumber();
			
			//TODO May be wrong (odd or even regarding the casting to and int)
			vToHouseNumber = (int) (edge.getvFromHouseNumber()+((edge.getvToHouseNumber()-edge.getvFromHouseNumber())*factor)); 
			hFromHouseNumber = edge.gethFromHouseNumber(); 
			
			//TODO May be wrong (odd or even regarding the casting to and int)
			hToHouseNumber = (int) (edge.gethFromHouseNumber() + (edge.gethToHouseNumber()-edge.gethFromHouseNumber())*factor);
			length = edge.getLength()*factor;
			time = edge.getLength()*factor;
		
		}
		
		KrakEdge tempEdge = new KrakEdge(from, to, name, length, time, fromPoint, toPoint, vPost, hPost, vFromHouseNumber, vToHouseNumber, hFromHouseNumber, hToHouseNumber);
		
		return tempEdge;
	}

	
	/**
	 * Checking if two doubles is bigger than the maximum x or y value or smaller than the minimum x or y value
	 * If it is, the maximum or minimum is updated.
	 * @param x coordinate
	 * @param y coordinate
	 */
	private static void findMinAndMaxValues(double x, double y){
		if(x < xMin) xMin = x;
		if(x > xMax) xMax = x;
		if(y < yMin) yMin = y;
		if(y > yMax) yMax = y;
	}







	private static boolean edgesAreEqual(KrakEdge firstEdge, KrakEdge secondEdge){
		//Test if both are null have already been checked in chachkStartAndTargetOfDijsktra() in this class
		if(firstEdge == null || secondEdge == null) return false;
		//TODO Is it enough to compare hashCodes???
		
		//Compare all data from each Edge: If all true, then return true else return false
		
		//Id's
		if(firstEdge.from() == secondEdge.from()) 
		if(firstEdge.to() == secondEdge.to())
		
		//Coordinates
		if((firstEdge.getFromPoint()[0] == secondEdge.getFromPoint()[0]) ||
			(firstEdge.getFromPoint()[1] == secondEdge.getFromPoint()[1]))
		if((firstEdge.getToPoint()[0] == secondEdge.getToPoint()[0]) ||
			(firstEdge.getToPoint()[1] == secondEdge.getToPoint()[1]))
		
		//House numbers
		if(firstEdge.getvFromHouseNumber() == secondEdge.getvFromHouseNumber())
		if(firstEdge.getvToHouseNumber() == secondEdge.getvToHouseNumber())
		if(firstEdge.gethFromHouseNumber() == secondEdge.gethFromHouseNumber())
		if(firstEdge.gethToHouseNumber() ==  secondEdge.gethToHouseNumber())
		
		//Postal numbers
		if(firstEdge.getvPost() == secondEdge.getvPost())
		if(firstEdge.gethPost() == secondEdge.gethPost())
		
		//Lengths
		if(firstEdge.getLength() == secondEdge.getLength())
		
		//Names
		if(firstEdge.getName().equals(secondEdge.getName()))
		
		//Time
		if(firstEdge.getTime() != secondEdge.getTime()){
			System.err.println("EdgesAndRoadsConverter.edgesAreEqual() - two roads have been tested equal! :O)");
			return true;
		}
			
			return false;
	}







	public static KrakEdge[] convertRouteStackToArray(Stack<KrakEdge> routeEdges) {
		KrakEdge[] routeRoads = new KrakEdge[routeEdges.size()]; 
		
		for(int index=0; index<routeRoads.length; index++){
			routeRoads[index] = routeEdges.pop();
		}

		return routeRoads;
	}







	public static Road[] checkStartAndTargetOfDijkstra(KrakEdge[] routeEdges, PathPreface pathPrefaceFrom, PathPreface pathPrefaceTo) throws Exception{
		//Both edges at the Start or Target can't be null! There must be at least one valid value!
		if(pathPrefaceFrom.getEdge1() == null && pathPrefaceFrom.getEdge2() == null) throw new Exception();
		if(pathPrefaceTo.getEdge1() == null && pathPrefaceTo.getEdge2() == null) throw new Exception();
		
		//Will test if the first edge is the same as the one in the preface
		KrakEdge edgeToBeAddedInFrontOfRoute = null;
		KrakEdge edgeToBeAddedAtEndOfRoute = null;
		
		//If NOT either of the two Edge's in the preface are equal to the first edge in the route...
		if(!(edgesAreEqual(routeEdges[0], pathPrefaceFrom.getEdge1()) || edgesAreEqual(routeEdges[0], pathPrefaceFrom.getEdge2())))
			
			//... the correct Edge are to be added in front of the route
			if(routeEdges[0].from() == pathPrefaceFrom.getEdge1().to()) edgeToBeAddedInFrontOfRoute = pathPrefaceFrom.getEdge1();//add first edge in front of route
			else if(routeEdges[0].from() == pathPrefaceFrom.getEdge2().to()) edgeToBeAddedInFrontOfRoute = pathPrefaceFrom.getEdge2();//add second edge in front of route
			//TODO May add error handling
			else System.out.println("EdgesAndRoadsConverter.comparePathPrefaceAndRoute() - FIRST NODE - not corresponding to-id's!!\n" +
					"routeEdges[0].from(): " + routeEdges[0].from() + " and pathPreface.getEdge1().to(): " + pathPrefaceFrom.getEdge1().to() + 
					" and pathPrefaceFrom.getEdge2().to(): " + pathPrefaceFrom.getEdge2().to());
		
		//If NOT either of the two Edge's in the pathPreface are equal to the last Edge in the route...
		if(!(edgesAreEqual(routeEdges[routeEdges.length-1], pathPrefaceTo.getEdge1()) || 
				edgesAreEqual(routeEdges[routeEdges.length-1], pathPrefaceTo.getEdge2())))
			
			//...the correct Edge must be added 
			if(routeEdges[routeEdges.length-1].to() == pathPrefaceTo.getEdge1().from()) edgeToBeAddedAtEndOfRoute = pathPrefaceTo.getEdge1();
			else if(routeEdges[routeEdges.length-1].to() == pathPrefaceTo.getEdge2().from()) edgeToBeAddedAtEndOfRoute = pathPrefaceTo.getEdge2();
			//TODO May add error handling
			else System.out.println("EdgesAndRoadsConverter.comparePathPrefaceAndRoute() - LAST NODE - not corresponding to-id's!!\n" +
					"routeEdges[routeEdges.length-1].to(): " + routeEdges[routeEdges.length-1].to() + " and pathPreface.getEdge1().from(): " + pathPrefaceFrom.getEdge1().from() + 
					" and pathPrefaceFrom.getEdge2().from(): " + pathPrefaceFrom.getEdge2().from());
		
		int edgesToBeAdded = 0;
		if(edgeToBeAddedInFrontOfRoute != null) edgesToBeAdded++;
		if(edgeToBeAddedAtEndOfRoute != null) edgesToBeAdded++;
		
		KrakEdge[] newEdges = new KrakEdge[routeEdges.length+edgesToBeAdded];

		//If Edges are to be added
		int startPoint = 0;
		if(edgesToBeAdded != 0){
			
			//If edge are going to be added in front of route, do it now
			if(edgeToBeAddedInFrontOfRoute != null){
				newEdges[0] = edgeToBeAddedInFrontOfRoute;
				startPoint = 1;
			}
			
			//If edge are going to be added at end of route, do it now
			if(edgeToBeAddedAtEndOfRoute != null)
				newEdges[newEdges.length-1] = edgeToBeAddedAtEndOfRoute;
		} 
		
		//Add every old edge to the new array
		int indexForOldEdges = 0;
		for(int index=startPoint; index<routeEdges.length+startPoint; index++){
			newEdges[index] = routeEdges[indexForOldEdges];
			indexForOldEdges++;
		}
		
		return changeFirstAndLastEdgeToHouseNumber(newEdges, pathPrefaceFrom, pathPrefaceTo);
	}
	
		
	private static Road[] changeFirstAndLastEdgeToHouseNumber(KrakEdge[] edges, PathPreface p1, PathPreface p2){
		
		/**
		 * Check of the starting and ending edge is done - now they will be changed to start/stop at the house number
		 * and out into an array of Road's 
		 */
		
		//Cut of some of the starting and ending edge
		edges[0] = divideKrakEdge(edges[0], true, p1.getHouseNumber());
		edges[edges.length-1] = divideKrakEdge(edges[edges.length-1], false, p2.getHouseNumber());
		
		
		
		//Convert the array of edges into and array of roads (defined as the road type set to be Route)
		Road[] routeRoads = new Road[edges.length];
		int routeType = Road.getRouteType();
		
		//Will reset the coordinates, so they can be used for the next search for min and max coordinates
		resetCoordinateValues();
		
		for(int index=0; index<edges.length; index++){
			//Find highest and lowest x and y-coordinates
			findMinAndMaxValues(edges[index].getFromPoint()[0], edges[index].getFromPoint()[1]);
			findMinAndMaxValues(edges[index].getToPoint()[0], edges[index].getToPoint()[1]);
			
			routeRoads[index] = new Road(edges[index].getFromPoint()[0],
									  	 edges[index].getFromPoint()[1],
										 edges[index].getToPoint()[0],
										 edges[index].getToPoint()[1],
										 routeType,
										 edges[index].getName()
										 );
		}
		
		//Sets the Region for the route
		Road.setOrigo(new double[]{xMin, yMin});
		Road.setTop(new double[]{xMax, yMax});
		
		return routeRoads;
	}
}
