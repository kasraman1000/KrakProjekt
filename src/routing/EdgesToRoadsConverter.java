/**
 * 
 */
package routing;

import java.util.Iterator;
import models.*;

/**
 * @author Yndal
 *
 */
public class EdgesToRoadsConverter {
	private static double xMin;
	private static double yMin;
	private static double xMax;
	private static double yMax;
	
	static{
		xMin=10000000;
		yMin=10000000;
		xMax=0;
		yMax=0;
	}


	public static Road[] convertEdgesToRoads(Stack<KrakEdge> route, int firstHouseNumber, int lastHouseNumber){
		Stack<Road> routeStack = new Stack<Road>();
		
		//Find min and max
		//Create new Road[] and add all the route-Roads (and set the roadType to 50)
		int routeType = 50;
		Road tempRoad;
		KrakEdge edge;
		KrakEdge firstEdge = null;
		KrakEdge lastEdge = null;
		while(!route.isEmpty()){
			edge = route.pop();
			
			findMinAndMaxValues(edge.getFromPoint()[0],edge.getFromPoint()[1]);
			findMinAndMaxValues(edge.getToPoint()[0],edge.getToPoint()[1]);
			
			if(firstEdge==null){
				firstEdge = edge;
				//Don't add the first Edge
				continue;
			}
			lastEdge = edge;
			
			tempRoad = new Road(
					edge.getFromPoint()[0],
					edge.getFromPoint()[1],
					edge.getToPoint()[0],
					edge.getToPoint()[1],
					routeType,
					edge.getName()
					);
			
			routeStack.push(tempRoad);
		}
		//Remove last Road of route
		routeStack.pop();
		
		//TODO MayAdd a big circle at the destination/startpoint
		KrakEdge newFirstEdge = divideKrakEdge(firstEdge, true, firstHouseNumber);
		KrakEdge newLastEdge = divideKrakEdge(lastEdge, false, lastHouseNumber);
		
		
		Road[] roads = RoadSelector.searchRange(new Region(xMin, yMin, xMax, yMax));
		
		//New empty array for the  combined roads and route (including start and target)
		Road[] roadsAndRoute = new Road[roads.length+routeStack.size()+2];
		
		
		for(int counter=0; counter<roads.length; counter++){
			roadsAndRoute[counter] = roads[counter];
		}
		
		//Add the part of the first Edge to the Road[]
		roadsAndRoute[roads.length] = new Road(newFirstEdge.getFromPoint()[0], newFirstEdge.getFromPoint()[1], newFirstEdge.getToPoint()[0], newFirstEdge.getToPoint()[1], routeType, newFirstEdge.getName());
		
		
		int counter = roads.length+1;
		while(!routeStack.isEmpty()){
			roadsAndRoute[counter] = routeStack.pop();
			counter++;
		}
		
		//Add the part of the last Edge to the Road[]
		roadsAndRoute[roadsAndRoute.length-1] = new Road(newLastEdge.getFromPoint()[0], newLastEdge.getFromPoint()[1], newLastEdge.getToPoint()[0], newLastEdge.getToPoint()[1], routeType, newLastEdge.getName());
		
//		System.out.println("xMin: " + xMin);
//		System.out.println("yMin: " + yMin);
//		System.out.println("xMax: " + xMax);
//		System.out.println("yMax: " + yMax);
		
		
		return roadsAndRoute;
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
			vFromHouseNumber = (int) (edge.getvToHouseNumber() - ((edge.getvToHouseNumber()-edge.getvFromHouseNumber())*factor)); //TODO May be wrong (odd or even regarding the casting to and int)
			vToHouseNumber = edge.getvToHouseNumber();
			hFromHouseNumber = (int) (edge.gethToHouseNumber() - ((edge.gethToHouseNumber()-edge.gethFromHouseNumber())*factor));; //TODO May be wrong (odd or even regarding the casting to and int)
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
			vToHouseNumber = (int) (edge.getvFromHouseNumber()+((edge.getvToHouseNumber()-edge.getvFromHouseNumber())*factor)); //TODO May be wrong (odd or even regarding the casting to and int)
			hFromHouseNumber = edge.gethFromHouseNumber(); 
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
}
