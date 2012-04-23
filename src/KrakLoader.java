import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import routing.DirectedEdge;
import routing.EdgeWeightedDigraph;
import routing.In;

class KrakLoader {
	private static ArrayList<Node> nodesForKDTree;
	private static EdgeWeightedDigraph graph;
	private static double xMin;
	private static double yMin;
	private static double xMax;
	private static double yMax;
	
	static{
		nodesForKDTree = new ArrayList<Node>();
		xMin = 10000000.0;
		yMin = 10000000.0;
		xMax = 0.0;
		yMax = 0.0;
	}


	public static void load(String nodePath, String edgePath) throws IOException{
		In inEdges = new In(new File(edgePath));
		In inNodes = new In(new File(nodePath));
		//Skip first line
		inEdges.readLine();
		inNodes.readLine();
		
		
		//For graph
		HashMap<Integer, double[]> coordArray = new HashMap<Integer, double[]>();
		
		//For KD-Tree
		HashMap<Integer, Node> nodeList = new HashMap<Integer, Node>();
		
		
		String[] nodeArray;
		int nodeId;
		double xCoord;
		double yCoord;
		while(inNodes.hasNextLine()){
			nodeArray = inNodes.readLine().split(",");
			
			/**
			 * OBS L�g m�rke til at ID'et bliver minuset med 1!!!!
			 */
			nodeId = Integer.valueOf(nodeArray[2])-1;
			xCoord = Double.valueOf(nodeArray[3]);
			yCoord = Double.valueOf(nodeArray[4]);

			coordArray.put(nodeId, new double[]{xCoord, yCoord});
			
			findMinAndMaxValues(xCoord, yCoord);
		}
		
		//Rearranges the coordinates to the actual places in the graph and
		//make the nodeMap for the KDTree
		double newX;
		double newY;
		for(int index=0; index<coordArray.size(); index++){
			newX = coordArray.get(index)[0]- xMin;
			newY = coordArray.get(index)[1]*(-1) + yMax;
	
			coordArray.get(index)[0] = newX;
			coordArray.get(index)[1] = newY;
	
			nodeList.put(index, new Node(new double[]{newX, newY}));
		}
		
		Road.setOrigo(new double[]{0, 0});
		Road.setTop(new double[]{xMax-xMin, yMax-yMin});
		
		ArrayList<DirectedEdge> edges = new ArrayList<>();
 
		EdgeWeightedDigraph graph;
		Set<Integer> nodesForGraph = new HashSet<Integer>();
		String[] tempStringArr;
		int from;
		int to;
		int type;
		double dist;
		double time;
		String name;
		Road tempRoad;
		double[] fromPoint;
		double[] toPoint;
		String direction;
		while(inEdges.hasNextLine()){
			tempStringArr = inEdges.readLine().split(",");
			
			
			/**
			 * OBS L�g m�rke til at ID'erne bliver minuset med 1!!!!
			 */
			from = Integer.valueOf(tempStringArr[0])-1;
			to = Integer.valueOf(tempStringArr[1])-1;
			dist = Double.valueOf(tempStringArr[2]);
			type = Integer.valueOf(tempStringArr[5]);
			name = tempStringArr[6];
			
			//time in minuttes
			time = Double.valueOf(tempStringArr[26]);
			
			//tf = to->from
			//ft = from->to
			//n = no driving allowed
			//<blank> = no restrictions
			direction = tempStringArr[27]; 
			
			fromPoint = new double[]{coordArray.get(from)[0], coordArray.get(from)[1]};
			toPoint = new double[]{coordArray.get(to)[0], coordArray.get(to)[1]};
			
			//TODO fromPoint/toPoint might be changed
			if      (direction.equals("'tf'")) edges.add(new DirectedEdge(from, to, name, dist, time, fromPoint, toPoint));
			else if (direction.equals("'ft'")) edges.add(new DirectedEdge(to, from, name, dist, time, fromPoint, toPoint));
			else if (!direction.equals("'n'")) {
				edges.add(new DirectedEdge(from, to, name, dist, time, fromPoint, toPoint));
				edges.add(new DirectedEdge(to, from, name, dist, time, fromPoint, toPoint));
			}
			
			tempRoad = new Road(fromPoint[0], fromPoint[1], toPoint[0], toPoint[1], type, name);

			nodeList.get(from).addRoad(tempRoad);
			nodeList.get(to).addRoad(tempRoad);
			
			nodesForGraph.add(to);
			nodesForGraph.add(from);
		}
		
		nodesForKDTree.addAll(nodeList.values());
		
		graph = new EdgeWeightedDigraph(nodesForGraph.size());
	
		for(DirectedEdge e : edges){
			graph.addEdge(e);
		}
		
		
		
		System.out.println("xMin: " + xMin);
		System.out.println("yMin: " + yMin);
		System.out.println("xMax: " + xMax);
		System.out.println("yMax: " + yMax);
		
//		
//		System.out.println("Graph:");
//		System.out.println(graph.E() + " edges and " + graph.V() + " nodes");
//		
//		return graph;
//		
//		//TODO
//		//TODO
//		//TODO
//		//TODO
//		//TODO
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
////		HashMap<Integer, Node> map = new HashMap<Integer, Node>(1000000);
//		//reads the nodes file
//		File file = new File(nodePath);
//		BufferedReader reader = new BufferedReader(new FileReader(file));
//		String curLine;
//		// first line is irrelevant
//		reader.readLine();
//		//variables for finding extremes
//		double biggestX = 0;
//		double biggestY = 0;
//		double smallestX = 10000000;
//		double smallestY = 10000000;
//		
//		
//		//Adding all nodes to result HashMap with their ID as keys
//		while((curLine = reader.readLine()) != null){
//			String[] lineArray = curLine.split(",");
//			double[] coords = {Double.valueOf(lineArray[3]), Double.valueOf(lineArray[4])};
//			if(Double.valueOf(lineArray[3]) > biggestX)
//			{
//				biggestX = Double.valueOf(lineArray[3]);
//			}
//			if(Double.valueOf(lineArray[4]) > biggestY)
//			{
//				biggestY = Double.valueOf(lineArray[4]);
//			}
//			if(Double.valueOf(lineArray[3]) < smallestX)
//			{
//				smallestX = Double.valueOf(lineArray[3]);
//			}
//			if(Double.valueOf(lineArray[4]) < smallestY)
//			{
//				smallestY = Double.valueOf(lineArray[4]);
//			}
//			
//			Node node = new Node(coords);
//			map.put(Integer.valueOf(lineArray[2]), node);
//		}
//		double[] top = {biggestX, biggestY};
//		double[] origo = {smallestX, smallestY};
//		Road.setTop(top);
//		Road.setOrigo(origo);
//
//		//reads the edgeList 
//		File file2 = new File(edgePath);
//		BufferedReader reader2 = new BufferedReader(new FileReader(file2));
//		String curLine2;
//	
//		// first line is irrelevant
//		reader2.readLine();
//		
//		for(Node node : map.values()){
//			node.coords[0] -= smallestX; 
//			node.coords[1] = node.coords[1]*(-1) + biggestY;
//			if(node.coords[1] > 1000000)
//			{
//				System.out.println("OMGWTFBBQ");
//			}
//		}
//		
//		double[] newTop = {biggestX-smallestX, biggestY-smallestY};
//		double[] newOrigo = {0, 0};
//		Road.setTop(newTop);
//		Road.setOrigo(newOrigo);
//		
//		//Creating roads and adding references from nodes to roads. Coordinates from nodes is added to roads.
//		while((curLine2 = reader2.readLine()) != null){
//			String[] lineArray2 = curLine2.split(",");
//			Node node1 = map.get(Integer.valueOf(lineArray2[0]));
//			Node node2 = map.get(Integer.valueOf(lineArray2[1]));
//
//
//			Road road = new Road(
//					node1.coords[0],
//					node1.coords [1],
//					node2.coords [0],
//					node2.coords [1],
//					Integer.valueOf(lineArray2[5]),
//					lineArray2[6]);
//			node1.addRoad(road);
//			node2.addRoad(road);
//		}
//
//		ArrayList<Node> result = new ArrayList<Node>(1000000);
//		result.addAll(map.values());
//		
//		return result;
	}
	
	
	public static ArrayList<Node> getNodesForKDTree(){
		//TODO Add a nice Exception to throw
//		if(nodesForKDTree == null) throw new DataNotLoadedException();
		return nodesForKDTree;
	}
	

	public static EdgeWeightedDigraph getGraph(){
		//TODO Add a nice Exception to throw
//		if(graph == null) throw new DataNotLoadedException();
		return graph;
	}
	
	
	
//	public static void main(String[] args) {
//		try {
//			Collection<Node> nodes = KrakLoader.load("kdv_node_unload.txt", "kdv_unload.txt");
//			
//			for (Node n : nodes) {
//				for (Road r : n.getRoads()) {
//					System.out.println(r);
//				}
//			}
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	private static void findMinAndMaxValues(double x, double y){
		if(x < xMin) xMin = x;
		if(x > xMax) xMax = x;
		if(y < yMin) yMin = y;
		if(y > yMax) yMax = y;
	}
}
