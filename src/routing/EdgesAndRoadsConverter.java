/**
 * 
 */
package routing;

import models.PathPreface;
import models.Road;
import models.RoadStatus;

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







	public static int getNearestNodeId(PathPreface pathPreface) {
		KrakEdge edge1 = pathPreface.getEdge1();
		KrakEdge edge2 = pathPreface.getEdge2();
		int idForEdge1=-1;
		int idForEdge2=-1;
		double distanceForEdge1=1000000;
		double distanceForEdge2=1000000;
		
		if(edge1 != null){
			
			
		}
		if(edge2 != null){
			
		}
		
		//TODO
		if(idForEdge1 == -1 && idForEdge2 == -1){
			System.out.println("EdgesAndRoadsConverter.getNearestNodeId(): Invalid result!!");
//			throw new InvalidResultException;
		}
		if(distanceForEdge1<distanceForEdge2 && idForEdge1!=-1) return idForEdge1;
		return idForEdge2;
	}







	
	private static boolean compareEdges(KrakEdge firstEdge, KrakEdge secondEdge){
		//TODO Is it enough to compare hashCodes???
		
		//Compare all data from each Edge: If all true, then return true else return false
//		firstEdge.from();
		secondEdge.from();
		
		
		//Id's
		if(firstEdge.from() != secondEdge.from()) 
		if(firstEdge.to() != secondEdge.to())
		
		//Coordinates
		if((firstEdge.getFromPoint()[0] != secondEdge.getFromPoint()[0]) ||
			(firstEdge.getFromPoint()[1] != secondEdge.getFromPoint()[1]))
		if((firstEdge.getToPoint()[0] != secondEdge.getToPoint()[0]) ||
			(firstEdge.getToPoint()[1] != secondEdge.getToPoint()[1]))
		
		//House numbers
		if(firstEdge.getvFromHouseNumber() != secondEdge.getvFromHouseNumber())
		if(firstEdge.getvToHouseNumber() != secondEdge.getvToHouseNumber())
		if(firstEdge.gethFromHouseNumber() != secondEdge.gethFromHouseNumber())
		if(firstEdge.gethToHouseNumber() !=  secondEdge.gethToHouseNumber())
		
		//Postal numbers
		if(firstEdge.getvPost() != secondEdge.getvPost())
		if(firstEdge.gethPost() != secondEdge.gethPost())
		
		//Lengths
		if(firstEdge.getLength() != secondEdge.getLength())
		
		//Names
		if(firstEdge.getName() != secondEdge.getName())
		
		//Time
		if(firstEdge.getTime() != secondEdge.getTime())
			return true;
			
			return false;
	}







	public static KrakEdge[] convertRouteStackToArray(Stack<KrakEdge> routeEdges) {
		KrakEdge[] routeRoads = new KrakEdge[routeEdges.size()]; 
		
		for(int index=0; index<routeRoads.length; index++){
			routeRoads[index] = routeEdges.pop();
		}

		return routeRoads;
	}







	public static Road[] checkStartAndTargetOfDijkstra(KrakEdge[] routeEdges, PathPreface pathPrefaceFrom, PathPreface pathPrefaceTo) {
		/**
		 * 
		 * Hvis Den første Edge i ruten er lig PreFace.edge 1 eller 2 skal den første Edge tilpasses mht. husnummer
		 * ellers skal PreFace.edge 1 eller 2 lægges til og tilpasset med husnummer
		 * 
		 */
		
		
		//Will test if the first edge is the same as the one in the preface
		KrakEdge edgeToBeAddedInFrontOfRoute = null;
		KrakEdge edgeToBeAddedAtEndOfRoute = null;
		
		//If NOT either of the two Edge's in the preface are equal to the first edge in the route...
		if(!(compareEdges(routeEdges[0], pathPrefaceFrom.getEdge1()) || compareEdges(routeEdges[0], pathPrefaceFrom.getEdge2())))
			
			//... the correct Edge are to be added in front of the route
			if(routeEdges[0].to() == pathPrefaceFrom.getEdge1().to()) edgeToBeAddedInFrontOfRoute = pathPrefaceFrom.getEdge1();//add first edge in front of route
			else if(routeEdges[0].to() == pathPrefaceFrom.getEdge2().to()) edgeToBeAddedInFrontOfRoute = pathPrefaceFrom.getEdge2();//add second edge in front of route
			else System.out.println("EdgesAndRoadsConverter.comparePathPrefaceAndRoute() - not corresponding to-id's!! Something serious is wrong!!");
		
		//If NOT either of the two Edge's in the pathPreface are equal to the last Edge in the route...
		if(!(compareEdges(routeEdges[routeEdges.length-1], pathPrefaceTo.getEdge1()) || 
				compareEdges(routeEdges[routeEdges.length-1], pathPrefaceTo.getEdge2())))
			
			//...the correct Edge must be added 
			if(routeEdges[routeEdges.length-1].from() == pathPrefaceTo.getEdge1().from()) edgeToBeAddedAtEndOfRoute = pathPrefaceTo.getEdge1();
			else if(routeEdges[routeEdges.length-1].from() == pathPrefaceTo.getEdge2().from()) edgeToBeAddedAtEndOfRoute = pathPrefaceTo.getEdge2();
			else System.out.println("EdgesAndRoadsConverter.comparePathPrefaceAndRoute() - not corresponding from-id's!! Something serious is wrong!!");
		
		int edgesToBeAdded = 0;
		if(edgeToBeAddedInFrontOfRoute != null) edgesToBeAdded++;
		if(edgeToBeAddedAtEndOfRoute != null) edgesToBeAdded++;
		
		KrakEdge[] newEdges = new KrakEdge[routeEdges.length+edgesToBeAdded];
	
		//TODO
		if(edgeToBeAddedInFrontOfRoute==null) System.out.println("Tuttelu");
		if(edgeToBeAddedAtEndOfRoute==null) System.out.println("tralala");
		
		//If Edges are to be added
		if(edgesToBeAdded != 0){
			int startPoint = 0;
			
			//If edge are going to be added in front of route, do it now
			if(edgeToBeAddedInFrontOfRoute != null){
				newEdges[0] = edgeToBeAddedInFrontOfRoute;
				startPoint = 1;
			}
			
			//Add every old edge to the new array
			int indexForOldEdges = 0;
			for(int index=startPoint; index<routeEdges.length+startPoint; index++){
				newEdges[index] = routeEdges[indexForOldEdges];
				indexForOldEdges++;
			}
			
			
			//If edge are going to be added at end of route, do it now
			if(edgeToBeAddedAtEndOfRoute != null)
				newEdges[newEdges.length-1] = edgeToBeAddedAtEndOfRoute;
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
		int routeType = RoadStatus.getRouteType();
		
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
	
		System.out.println("xMin: " + xMin);
		System.out.println("yMin: " + yMin);
		System.out.println("xMax: " + xMax);
		System.out.println("yMax: " + yMax);
	
		
		return routeRoads;
		
		
		
		
//		Stack<Road> roadStack = new Stack<Road>();
		
			//Find min and max
			//Create new Stack containing all the roads except the first and last.
			/**
			 * For some reason the routeStack.size() is invalid! 
			 * Therefore this solution
			 */
//			Road[] routeRoads = new Road[newEdges.length];
//			int routeType = 50;
//			Road tempRoad;
//			KrakEdge firstEdge = routeEdges[0];
//			KrakEdge lastEdge = routeEdges[routeEdges.length-1];
//			int routeIndexCounter=0;
//			
//			//
//			for(int index=1; index<routeEdges.length-1; index++){
//				
//				//Find highest and lowest x and y-coordinates
//				findMinAndMaxValues(routeEdges[index].getFromPoint()[0],routeEdges[index].getFromPoint()[1]);
//				findMinAndMaxValues(routeEdges[index].getToPoint()[0],routeEdges[index].getToPoint()[1]);
//				
//				routeRoads[index] = new Road(routeEdges[index].getFromPoint()[0],
//										  	 routeEdges[index].getFromPoint()[1],
//											 routeEdges[index].getToPoint()[0],
//											 routeEdges[index].getToPoint()[1],
//											 routeType,
//											 routeEdges[index].getName()
//											 );
//				
//				
//			}
//			
//			
//			//Kept as edges for easing further development
//			KrakEdge newFirstEdge = divideKrakEdge(firstEdge, true, firstHouseNumber);
//			KrakEdge newLastEdge = divideKrakEdge(lastEdge, false, lastHouseNumber);
//			
//			//Add the part of the first Edge to the Road[]
//			routeRoads[0] = new Road(newFirstEdge.getFromPoint()[0], 
//									newFirstEdge.getFromPoint()[1], 
//									newFirstEdge.getToPoint()[0], 
//									newFirstEdge.getToPoint()[1], 
//									routeType, 
//									newFirstEdge.getName());
//					
//			//Add the part of the last Edge to the Road[]
//			routeRoads[routeRoads.length-1] = new Road(newLastEdge.getFromPoint()[0], 
//														newLastEdge.getFromPoint()[1], 
//														newLastEdge.getToPoint()[0], 
//														newLastEdge.getToPoint()[1], 
//														routeType, 
//														newLastEdge.getName());
//					
//				
////			System.out.println("xMin: " + xMin);
////			System.out.println("yMin: " + yMin);
////			System.out.println("xMax: " + xMax);
////			System.out.println("yMax: " + yMax);
//			
//			
//			//Sets the Region for the route
//			routeRoads[0].setOrigo(new double[]{xMin, yMin});
//			routeRoads[0].setTop(new double[]{xMax, yMax});
//
//			
//			return routeRoads;
//			
//			
//			
//			int routeType = 50;
//			Road tempRoad;
//			KrakEdge edge;
//			KrakEdge firstEdge = null;
//			KrakEdge lastEdge = null;
//			
//			Road[] route = new Road[routeStack.size()];
//			System.out.println("RouteStack.size(): " + routeStack.size());
//			
//			while(!routeStack.isEmpty()){
//				edge = routeStack.pop();
//				
//				findMinAndMaxValues(edge.getFromPoint()[0],edge.getFromPoint()[1]);
//				findMinAndMaxValues(edge.getToPoint()[0],edge.getToPoint()[1]);
//				
//				if(firstEdge==null){
//					firstEdge = edge;
//					//Don't add the first Edge
//					continue;
//				}
//				lastEdge = edge;
//				
//				tempRoad = new Road(
//						edge.getFromPoint()[0],
//						edge.getFromPoint()[1],
//						edge.getToPoint()[0],
//						edge.getToPoint()[1],
//						routeType,
//						edge.getName()
//						);
//			
//			}
//			
//			//First and last edge are kept as edges, because of the possibility
//			//to expand the project further later on
//			for(int index=0; index<routeStack.size(); index++){
//				edge = routeStack.pop();
//				
//				findMinAndMaxValues(edge.getFromPoint()[0],edge.getFromPoint()[1]);
//				findMinAndMaxValues(edge.getToPoint()[0],edge.getToPoint()[1]);
//				
//				if(firstEdge==null){
//					firstEdge = edge;
//					//Don't add the first Edge
//					continue;
//				}
//				lastEdge = edge;
//				
//				tempRoad = new Road(
//						edge.getFromPoint()[0],
//						edge.getFromPoint()[1],
//						edge.getToPoint()[0],
//						edge.getToPoint()[1],
//						routeType,
//						edge.getName()
//						);
//				route[index] = tempRoad;
//				System.out.println(index);
//			}
//			
//			//Sets the Region for the route
//			route[0].setOrigo(new double[]{xMin, yMin});
//			route[0].setTop(new double[]{xMax, yMax});
	//
//			
//			int tempCounter=0;
//			for(Road r : route){
//				if(r == null){
//					tempCounter++;
//				}
//			}
//			System.out.println("tempCounter: " + tempCounter);
//			System.out.println("EdgesAndRoadsConverter.convertEdgesToRoads() - Size of route array: " + route.length);
//			System.out.println("EdgesAndRoadsConverter.convertEdgesToRoads() - ");
//			
//			//Remove last Road of route
//			routeStack.pop();
//			
//			KrakEdge newFirstEdge = divideKrakEdge(firstEdge, true, firstHouseNumber);
//			KrakEdge newLastEdge = divideKrakEdge(lastEdge, false, lastHouseNumber);
//			
//			
//			
//			//Add the part of the first Edge to the Road[]
//			route[0] = new Road(newFirstEdge.getFromPoint()[0], newFirstEdge.getFromPoint()[1], newFirstEdge.getToPoint()[0], newFirstEdge.getToPoint()[1], routeType, newFirstEdge.getName());
//			
//			//Add the part of the last Edge to the Road[]
//			route[route.length-1] = new Road(newLastEdge.getFromPoint()[0], newLastEdge.getFromPoint()[1], newLastEdge.getToPoint()[0], newLastEdge.getToPoint()[1], routeType, newLastEdge.getName());
//			
////			System.out.println("xMin: " + xMin);
////			System.out.println("yMin: " + yMin);
////			System.out.println("xMax: " + xMax);
////			System.out.println("yMax: " + yMax);
//			
//			
//			return route;
		
		
		
		
		
		
		
		
		
		
		
	}
}
