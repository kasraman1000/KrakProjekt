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
		double start = System.nanoTime();
		Routing ty = new Routing();
		EdgeWeightedDigraph graph = ty.loadGraph("kdv_unload.txt", "kdv_node_unload.txt");
		double end = System.nanoTime();
		System.out.println("Time taken: " + (end-start)/1000000000 + " seconds");
	}
	
	
	/**
	 * This class may most likely be edited to cooperate with the KrakLoader in some way.
	 */
	//TODO Do not handle the nodes (with the X's and Y's) yet
	public EdgeWeightedDigraph loadGraph(String edgeFilePath, String nodeFilePath){
		In inEdges = new In(new File(edgeFilePath));
		In inNodes = new In(new File(nodeFilePath));
		//Skip first line
		inEdges.readLine();
		inNodes.readLine();
		
		
		
		HashMap<Integer,Point> coordArray = new HashMap<Integer,Point>();
		String[] nodeArray;
		int nodeId;
		double xCoord;
		double yCoord;
		Point point = new Point();
		while(inNodes.hasNextLine()){
			nodeArray = inNodes.readLine().split(",");
			
			/**
			 * OBS Læg mærke til at ID'et bliver minuset med 1!!!!
			 */
			nodeId = Integer.valueOf(nodeArray[2])-1;
			xCoord = Double.valueOf(nodeArray[3]);
			yCoord = Double.valueOf(nodeArray[4]);
			point.setLocation(xCoord, yCoord);
			coordArray.put(nodeId, point);
		}
	
				
		ArrayList<DirectedEdge> edges = new ArrayList<>();
 
		EdgeWeightedDigraph graph;
		Set<Integer> nodes = new HashSet<Integer>();
		String[] tempStringArr;
		int from;
		int to;
		double dist;
		double time;
		double x1;
		double y1;
		double x2;
		double y2;
		String direction;
		while(inEdges.hasNextLine()){
			tempStringArr = inEdges.readLine().split(",");
			
			
			/**
			 * OBS Læg mærke til at ID'erne bliver minuset med 1!!!!
			 */
			from = Integer.valueOf(tempStringArr[0])-1;
			to = Integer.valueOf(tempStringArr[1])-1;
			dist = Double.valueOf(tempStringArr[2]);
			
			//time in minuttes
			time = Double.valueOf(tempStringArr[26]);
			
			//tf = to->from
			//ft = from->to
			//n = no driving allowed
			//<blank> = no restrictions
			direction = tempStringArr[27]; 
			
			x1 = coordArray.get(from).getX();
			y1 = coordArray.get(from).getY();
			x2 = coordArray.get(to).getX();
			y2 = coordArray.get(to).getY();
			
			
			if      (direction.equals("'tf'")) edges.add(new DirectedEdge(from, to, dist, time, x1, y1, x2, y2));
			else if (direction.equals("'ft'")) edges.add(new DirectedEdge(to, from, dist, time, x1, y1, x2, y2));
			else if (!direction.equals("'n'")) {
				edges.add(new DirectedEdge(from, to, dist, time, x1, y1, x2, y2));
				edges.add(new DirectedEdge(to, from, dist, time, x1, y1, x2, y2));
			}
			nodes.add(to);
			nodes.add(from);
		}
		
		graph = new EdgeWeightedDigraph(nodes.size());
		System.out.println("Edges.size(): " + edges.size());
		
		for(DirectedEdge e : edges){
			graph.addEdge(e);
		}
		
		System.out.println("Graph:");
		System.out.println(graph.E() + " edges and " + graph.V() + " nodes");
		
		return graph;
	}


}
