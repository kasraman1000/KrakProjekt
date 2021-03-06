import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import routing.Bag;
import routing.DirectedEdge;
import routing.EdgeWeightedDigraph;
import routing.In;

class Loader {
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
		//Creates a Scanner for the filenames specified
		In inEdges = new In(new File(edgePath));
		In inNodes = new In(new File(nodePath));
		//Skip first line
		inEdges.readLine();
		inNodes.readLine();
		
		
		//For graph
		HashMap<Integer, double[]> coordArray = new HashMap<Integer, double[]>();
		
		//For KD-Tree
		HashMap<Integer, Node> nodeList = new HashMap<Integer, Node>();
		
		//String for storing the current line, which is being read
		String[] textLineNodeArray;
		int nodeId;
		double xCoord;
		double yCoord;
		//Running through all nodes
		while(inNodes.hasNextLine()){
			textLineNodeArray = inNodes.readLine().split(",");
			
			/**
			 * OBS L�g m�rke til at ID'et bliver minuset med 1!!!!
			 */
			nodeId = Integer.valueOf(textLineNodeArray[2])-1;
			xCoord = Double.valueOf(textLineNodeArray[3]);
			yCoord = Double.valueOf(textLineNodeArray[4]);

			coordArray.put(nodeId, new double[]{xCoord, yCoord});
			
			findMinAndMaxValues(xCoord, yCoord);
		}
		
		//Rearranges the coordinates to the actual places in the graph and
		//make the nodeMap for the KDTree
		double newX;
		double newY;
		for(int index=0; index<coordArray.size(); index++){
			newX = coordArray.get(index)[0]- xMin;
			newY = yMax - coordArray.get(index)[1];
	
			coordArray.get(index)[0] = newX;
			coordArray.get(index)[1] = newY;
	
			nodeList.put(index, new Node(new double[]{newX, newY}));
		}
		
		coordArray = null;
		Road.setOrigo(new double[]{0, 0});
		Road.setTop(new double[]{xMax-xMin, yMax-yMin});
		
		ArrayList<DirectedEdge> edges = new ArrayList<DirectedEdge>();
 
		EdgeWeightedDigraph graph;
		String[] textLineRoadArray;
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
			textLineRoadArray = inEdges.readLine().split(",");
			
			
			/**
			 * OBS L�g m�rke til at ID'erne bliver minuset med 1!!!!
			 */
			from = Integer.valueOf(textLineRoadArray[0])-1;
			to = Integer.valueOf(textLineRoadArray[1])-1;
			dist = Double.valueOf(textLineRoadArray[2]);
			type = Integer.valueOf(textLineRoadArray[5]);
			name = textLineRoadArray[6];
			//time in minuttes

			time = Double.valueOf(textLineRoadArray[26]);
			
			//tf = to->from
			//ft = from->to
			//n = no driving allowed
			//<blank> = no restrictions
			direction = textLineRoadArray[27]; 
			
			//Reading coordinates from the beginning of the road to the end of the road
			fromPoint = new double[]{nodeList.get(from).getCoord(0), nodeList.get(from).getCoord(1)};
			toPoint = new double[]{nodeList.get(to).getCoord(0), nodeList.get(to).getCoord(1)};
			
			//TODO fromPoint/toPoint might be changed
			if      (direction.equals("'tf'")) edges.add(new DirectedEdge(from, to, name, dist, time, fromPoint, toPoint));
			else if (direction.equals("'ft'")) edges.add(new DirectedEdge(to, from, name, dist, time, fromPoint, toPoint));
			else if (!direction.equals("'n'")) {
				edges.add(new DirectedEdge(from, to, name, dist, time, fromPoint, toPoint));
				edges.add(new DirectedEdge(to, from, name, dist, time, fromPoint, toPoint));
			}
			
			tempRoad = new Road(fromPoint[0], fromPoint[1], toPoint[0], toPoint[1], type, name);

			//Adding references from node to road
			nodeList.get(from).addRoad(tempRoad);
			nodeList.get(to).addRoad(tempRoad);

		}
		nodesForKDTree.addAll(nodeList.values());
		
		graph = new EdgeWeightedDigraph(nodeList.size());
		
		for(DirectedEdge e : edges){
			graph.addEdge(e);
		}
		
	}
	

	public static void buildRoadTranslator(String[] s){
		

	}
	/**
	 * Returns the collection that the KDTree is build from
	 * @return ArrayList of all nodes
	 */
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
