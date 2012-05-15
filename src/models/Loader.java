package models;

//Java library imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

//Program packages
import routing.*;
import errorHandling.*;

/**
 * The class responsible for loading all data into the server
 * 
 * @author Group 1, B-SWU, 2012E
 *
 */

public class Loader {
	private static ArrayList<Node> nodesForKDTree;
	private static KrakEdgeWeightedDigraph graph;
	private static ArrayList<KrakEdge> edges;
	private static HashMap<String, Integer> zipCodeMap;
	
	//The readers
	private static In inEdges;
	private static In inNodes;
	
	//Temporary Maps 
	private static HashMap<Integer, double[]> coordArray;
	private static HashMap<Integer, Node> nodeList;
	
	//Values to reposition the data (to avoid empty "frame" around the map)
	private static double xMin;
	private static double yMin;
	private static double xMax;
	private static double yMax;

	//For prints of start up time
	private static long timeMillis;

	static{
		nodesForKDTree = new ArrayList<Node>();
		edges = new ArrayList<KrakEdge>();
		zipCodeMap = new HashMap<String, Integer>();
		coordArray = new HashMap<Integer, double[]>();  //For graph
		nodeList = new HashMap<Integer, Node>(); 		//For KD-Tree
		xMin = 10000000.0;
		yMin = 10000000.0;
		xMax = 0.0;
		yMax = 0.0;
	}

	/**
	 * Will load all the data needed from the krak files specified
	 * 
	 * @param nodePath Path for the krak nodes file (may not be absolute)
	 * @param edgePath Path for the krak edges file (may not be absolute)
	 * @param zipPath Path for the zip code file (may not be absolute)
	 * @throws ServerStartupException If the server can not start up - will execute some actions afterwards
	 */
	public static void load(String nodePath, String edgePath, String zipPath) throws ServerStartupException{
		try{		
		//Creates the map, that contains each city's zipcode
		printLine();
		setTime();
		buildZipCodeMap(zipPath);
		printTime("Build zip code map");

		//Creates a Scanner for the filenames specified - copied from the BADS course
		inEdges = new In(new File(edgePath));
		inNodes = new In(new File(nodePath));
		}
		catch(FileNotFoundException e) {
			throw new LoaderFileNotFoundException(e);
		}

		//Skip first line
		inEdges.readLine();
		inNodes.readLine();
		
		setTime();
		getCoordinates();
		printTime("Found original extremes");
		//Rearranges the coordinates to the actual places in the graph and
		//make the nodeMap for the KDTree
		setTime();
		rearrangeCoordinates();
		printTime("Recalculated coordinates");
		//not in use past this
		coordArray = null;
		Road.setOrigo(new double[]{0, 0});
		Road.setTop(new double[]{xMax-xMin, yMax-yMin});

		setTime();
		
		//Values to extract from the krak edges file
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
			//To make sure the data is read in a correct way (ie. the name of the road is "R�de Sti, Den")
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
			 * NB Note the ID's are minused by 1
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

			//Adding the edge the right places (will be added to the graph later)
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
		
		printTime("Created edges");
		
		setTime();
		nodesForKDTree.addAll(nodeList.values());
		printTime("Added nodes to KDTree");
		
		setTime();
		buildGraph();
		printTime("Build routing graph");
		printLine();
	}
	
	/**
	 * Set the time field to now 
	 */
	private static void setTime()
	{
		timeMillis = System.currentTimeMillis();
	}
	
	/**
	 * Print the time from setTime() to now with the String s in front
	 * 
	 * @param s String to set in front of time
	 */
	private static void printTime(String s)
	{
		double time = System.currentTimeMillis()-timeMillis;
		System.out.println(s+" in " + time/1000 + " seconds");
	}
	
	/**
	 * Print a line of -'s to the console (only used to get a better overview)
	 */
	private static void printLine()
	{
		System.out.println("------------------------");
	}

	/**
	 * Used for extracting the coordinates from the Krak nodes file
	 */
	private static void getCoordinates()
	{
		//String for storing the current line, which is being read
		String[] textLineNodeArray;
		
		int nodeId;
		double xCoord;
		double yCoord;
		
		//Running through all nodes
		while(inNodes.hasNextLine()){
			textLineNodeArray = inNodes.readLine().split(",");

			/**
			 * NB Note the ID's are minused by 1
			 */
			nodeId = Integer.valueOf(textLineNodeArray[2])-1;
			xCoord = Double.valueOf(textLineNodeArray[3]);
			yCoord = Double.valueOf(textLineNodeArray[4]);

			coordArray.put(nodeId, new double[]{xCoord, yCoord});

			findMinAndMaxValues(xCoord, yCoord);
		}
	}
	
	/**
	 * Remove the empty "frame" around the map (if there is any)
	 */
	private static void rearrangeCoordinates()
	{
		double newX;
		double newY;
		for(int index=0; index<coordArray.size(); index++){
			newX = coordArray.get(index)[0]- xMin;
			newY = yMax - coordArray.get(index)[1];

			coordArray.get(index)[0] = newX;
			coordArray.get(index)[1] = newY;

			nodeList.put(index, new Node(new double[]{newX, newY}));
		}
	}
	
	/**
	 * Returns the collection that the KDTree is build from - Must only be called once!!!
	 * @return ArrayList of all nodes
	 */
	public static ArrayList<Node> getNodesForKDTree(){
		//Using temporary variable to nullify nodesForKDTree to spare use of ram
		ArrayList<Node> tempNodes = nodesForKDTree;
		nodesForKDTree = null;
		return tempNodes;
		
	}
	
	/**
	 * Load all the edges into the graph
	 */
	private static void buildGraph()
	{
		graph = new KrakEdgeWeightedDigraph(nodeList.size());
		for(KrakEdge e : edges){
			graph.addEdge(e);
		}
	}
	
	/**
	 * Create the zip code map
	 * 
	 * @param zipPath Path for the zipCode file (may not be absolute)
	 * @throws FileNotFoundException If the file was not found
	 */
	private static void buildZipCodeMap(String zipPath) throws FileNotFoundException
	{
		In inZipCodes = new In(new File(zipPath));
		String[] zipCityLine;
		
		while(inZipCodes.hasNextLine()){
			zipCityLine = inZipCodes.readLine().split(",");
			zipCodeMap.put(zipCityLine[1].toLowerCase(), Integer.parseInt(zipCityLine[0]));
		}
		
	}
	
	/**
	 * Get the zipeCode map
	 * @return zipCodeMap
	 */
	public static HashMap<String, Integer> getZipCodeMap()
	{
		return zipCodeMap;
	}
	
	/**
	 * Get the graph
	 * @return graph
	 */
	public static KrakEdgeWeightedDigraph getGraph(){
		return graph;
	}

	/**
	 * Get edges - must only be called once!!
	 * @return copy of edges;
	 */
	public static ArrayList<KrakEdge> getEdgesForTranslator() {
		ArrayList<KrakEdge> tempEdges = edges;
		//Set to null to spare use of ram
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