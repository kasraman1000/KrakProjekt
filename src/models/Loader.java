package models;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import routing.In;
import routing.KrakEdge;
import routing.KrakEdgeWeightedDigraph;

public class Loader {
	private static ArrayList<Node> nodesForKDTree;
	private static KrakEdgeWeightedDigraph graph;
	private static ArrayList<KrakEdge> edges = new ArrayList<KrakEdge>();
	private static HashMap<String, Integer> zipCodeMap = new HashMap<String, Integer>();
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


	public static void load(String nodePath, String edgePath, String zipPath) throws IOException{
		//Creates the map, that contains each city's zipcode
		buildZipCodeMap(zipPath);
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
		String[] textLineNodeArray = null;
		int nodeId;
		double xCoord;
		double yCoord;
		
		//Running through all nodes
		while(inNodes.hasNextLine()){
			textLineNodeArray = inNodes.readLine().split(",");

			/**
			 * OBS Læg mærke til at ID'et bliver minuset med 1!!!!
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

		String[] textLineRoadArray;
		int from;
		int to;
		int type;
		int vPost;
		int hPost;
		int hFromHusnummer;
		int hToHusnummer;
		int vFromHusnummer;
		int vToHusnummer;
		double dist;
		double time;
		String name;
		Road tempRoad;
		double[] fromPoint;
		double[] toPoint;
		String direction;
		while(inEdges.hasNextLine()){
			textLineRoadArray = inEdges.readLine().split(",");
			//To make sure the data is read in a correct way (ie. the name of the road is "Røde Sti, Den")
			if(textLineRoadArray.length > 33){
				for(int index=0; index<textLineRoadArray.length-1; index++){
					if(!(textLineRoadArray[index].contains("''") || textLineRoadArray[index+1].contains("''")) &&
								(textLineRoadArray[index].startsWith("'") && textLineRoadArray[index+1].endsWith("'"))){
						
						textLineRoadArray[index] = textLineRoadArray[index] + "," + textLineRoadArray[index+1];
	
						//Move the rest of the element one place to the left
						for(int a=index+2; a<textLineRoadArray.length; a++){
							textLineRoadArray[a-1] = textLineRoadArray[a];
						}
					}
				}
			}
			
			

			/**
			 * OBS Læg mærke til at ID'erne bliver minuset med 1!!!!
			 */
			from = Integer.valueOf(textLineRoadArray[0])-1;
			to = Integer.valueOf(textLineRoadArray[1])-1;
			dist = Double.valueOf(textLineRoadArray[2]);
			type = Integer.valueOf(textLineRoadArray[5]);
			name = textLineRoadArray[6].substring(1, textLineRoadArray[6].length()-1);
			vPost = Integer.valueOf(textLineRoadArray[17]);
			hPost = Integer.valueOf(textLineRoadArray[18]);
			vFromHusnummer = Integer.valueOf(textLineRoadArray[7]);
			vToHusnummer = Integer.valueOf(textLineRoadArray[8]);
			hFromHusnummer = Integer.valueOf(textLineRoadArray[9]);
			hToHusnummer = Integer.valueOf(textLineRoadArray[10]);
			
			//time in minutes
			time = Double.valueOf(textLineRoadArray[26]);

			//tf = to->from
			//ft = from->to
			//n = no driving allowed
			//<blank> = no restrictions
			direction = textLineRoadArray[27]; 

			//Reading coordinates from the beginning of the road to the end of the road
			fromPoint = new double[]{nodeList.get(from).getCoord(0), nodeList.get(from).getCoord(1)};
			toPoint = new double[]{nodeList.get(to).getCoord(0), nodeList.get(to).getCoord(1)};

			
			if      (direction.equals("'tf'")) edges.add(new KrakEdge(to, from, name, dist, time, toPoint, fromPoint ,vPost, hPost, vFromHusnummer, vToHusnummer, hFromHusnummer, hToHusnummer));
			else if (direction.equals("'ft'")) edges.add(new KrakEdge(to, from, name, dist, time, fromPoint, toPoint, vPost, hPost, vFromHusnummer, vToHusnummer, hFromHusnummer, hToHusnummer));
			else if (!direction.equals("'n'")) {
				edges.add(new KrakEdge(from, to, name, dist, time, fromPoint, toPoint, vPost, hPost, vFromHusnummer, vToHusnummer, hFromHusnummer, hToHusnummer));
				edges.add(new KrakEdge(to, from, name, dist, time, toPoint, fromPoint, vPost, hPost, vFromHusnummer, vToHusnummer, hFromHusnummer, hToHusnummer));
			}

			tempRoad = new Road(fromPoint[0], fromPoint[1], toPoint[0], toPoint[1], type, name);

			//Adding references from node to road
			nodeList.get(from).addRoad(tempRoad);
			nodeList.get(to).addRoad(tempRoad);

		}
		
		nodesForKDTree.addAll(nodeList.values());

		graph = new KrakEdgeWeightedDigraph(nodeList.size());

		
		for(KrakEdge e : edges){
			graph.addEdge(e);
		}
	}

	/**
	 * Returns the collection that the KDTree is build from
	 * @return ArrayList of all nodes
	 */
	public static ArrayList<Node> getNodesForKDTree(){
		//TODO Add a nice Exception to throw
		//		if(nodesForKDTree == null) throw new DataNotLoadedException();
		//Using temporary variable to nullify nodesForKDTree
		ArrayList<Node> tempNodes = nodesForKDTree;
		nodesForKDTree = null;
		return tempNodes;
		
	}
	
	public static void buildZipCodeMap(String zipPath)
	{
		In inZipCodes = new In(new File(zipPath));
		String[] zipCityLine;
		
		while(inZipCodes.hasNextLine()){
			zipCityLine = inZipCodes.readLine().split(",");
			zipCodeMap.put(zipCityLine[1], Integer.parseInt(zipCityLine[0]));
		}
		
	}
	
	public static HashMap<String, Integer> getZipCodeMap()
	{
		return zipCodeMap;
	}
	
	public static KrakEdgeWeightedDigraph getGraph(){
		//TODO Add a nice Exception to throw
		//		if(graph == null) throw new DataNotLoadedException();
		return graph;
	}

	public static ArrayList<KrakEdge> getEdgesForTranslator() {
		ArrayList<KrakEdge> tempEdges = edges;
		edges = null;
		return tempEdges;
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
