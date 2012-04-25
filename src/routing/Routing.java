/**
 * 
 */
package routing;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.awt.*;


/**
 * @author Yndal
 *
 */
public class Routing {


	public static void main(String[] args) {
		int source = 0;
		int target = 675901;
		
		//How to start up the graph and Dijkstra
		double start = System.nanoTime();
		Routing ty = new Routing();
		Loader.load("kdv_unload.txt", "kdv_node_unload.txt");
		EdgeWeightedDigraph graph = Loader.getGraph();
		double end = System.nanoTime();
		System.out.println("Time taken to load: " + (end-start)/1000000000 + " seconds");
		
		double dijkstraStart = System.nanoTime();
		DijkstraSP dij = new DijkstraSP(graph);
		Iterable<KrakEdge> path = dij.findRoute(source, target, true);
		double dijkstraEnd = System.nanoTime();
		
		System.out.println("Path from " + source + " to " + target);
		double totalWeight = 0;
		for(KrakEdge edge : path){
			System.out.println(edge.from() + " -> " + edge.to() + ": " + edge.weight());
			totalWeight += edge.weight();
		}
		System.out.println("Total weight: " + totalWeight + "\t\tWeighted by length: " + KrakEdge.isLengthWeighted());
		System.out.println("should be equal to: " + dij.distance(source, target));
		
		System.out.println("Time taken to find route: " + (dijkstraEnd-dijkstraStart)/1000000000 + " seconds");
		
		
	}
	
	
//	/**
//	 * This class may most likely be edited to cooperate with the KrakLoader in some way.
//	 */
//	public EdgeWeightedDigraph loadGraph(String edgeFilePath, String nodeFilePath){
//		In inEdges = new In(new File(edgeFilePath));
//		In inNodes = new In(new File(nodeFilePath));
//		//Skip first line
//		inEdges.readLine();
//		inNodes.readLine();
//		
//		
//		
//		HashMap<Integer,Point> coordArray = new HashMap<Integer,Point>();
//		String[] nodeArray;
//		int nodeId;
//		double xCoord;
//		double yCoord;
//		Point point = new Point();
//		while(inNodes.hasNextLine()){
//			nodeArray = inNodes.readLine().split(",");
//			
//			/**
//			 * OBS Læg mærke til at ID'et bliver minuset med 1!!!!
//			 */
//			nodeId = Integer.valueOf(nodeArray[2])-1;
//			xCoord = Double.valueOf(nodeArray[3]);
//			yCoord = Double.valueOf(nodeArray[4]);
//			point.setLocation(xCoord, yCoord);
//			coordArray.put(nodeId, point);
//		}
//	
//				
//		ArrayList<DirectedEdge> edges = new ArrayList<>();
// 
//		EdgeWeightedDigraph graph;
//		Set<Integer> nodes = new HashSet<Integer>();
//		String[] tempStringArr;
//		int from;
//		int to;
//		double dist;
//		double time;
//		Point fromPoint = new Point();
//		Point toPoint = new Point();
//		String direction;
//		while(inEdges.hasNextLine()){
//			tempStringArr = inEdges.readLine().split(",");
//			
//			
//			/**
//			 * OBS Læg mærke til at ID'erne bliver minuset med 1!!!!
//			 */
//			from = Integer.valueOf(tempStringArr[0])-1;
//			to = Integer.valueOf(tempStringArr[1])-1;
//			dist = Double.valueOf(tempStringArr[2]);
//			
//			//time in minuttes
//			time = Double.valueOf(tempStringArr[26]);
//			
//			//tf = to->from
//			//ft = from->to
//			//n = no driving allowed
//			//<blank> = no restrictions
//			direction = tempStringArr[27]; 
//			
//			fromPoint.setLocation(coordArray.get(from).getX(), coordArray.get(from).getY());
//			toPoint.setLocation(coordArray.get(to).getX(), coordArray.get(to).getY());
//			
//			if      (direction.equals("'tf'")) edges.add(new DirectedEdge(from, to, dist, time, fromPoint, toPoint));
//			else if (direction.equals("'ft'")) edges.add(new DirectedEdge(to, from, dist, time, fromPoint, toPoint));
//			else if (!direction.equals("'n'")) {
//				edges.add(new DirectedEdge(from, to, dist, time, fromPoint, toPoint));
//				edges.add(new DirectedEdge(to, from, dist, time, fromPoint, toPoint));
//			}
//			nodes.add(to);
//			nodes.add(from);
//		}
//		
//		graph = new EdgeWeightedDigraph(nodes.size());
//		System.out.println("Edges.size(): " + edges.size());
//		
//		for(DirectedEdge e : edges){
//			graph.addEdge(e);
//		}
//		
//		System.out.println("Graph:");
//		System.out.println(graph.E() + " edges and " + graph.V() + " nodes");
//		
//		return graph;
//	}


}
