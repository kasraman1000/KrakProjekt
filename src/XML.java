import java.awt.Color;
import java.io.File;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class is for the creation of the .xml file. At the moment it will generate a sewer
 * amount of lines to represent all roads in DK and Malmö. 
 * It figures out the position of the lines from the edges: Gets the two nodes connected
 * to the edge. Each node will contain a value for the x-coordinate and y-coordinate.
 * The class will then set to the line to be: x1=node1.xPos, x2=node2.xPos, y1=node1.yPos, y2=node2.yPos
 * 
 *   Because the coordinates are not starting from (0,0) the class has a build-in feature to find
 *   the max and min values for X and Y - then print this in the console
 * 
 * 
 * @author Group KRAX
 *
 */
public class XML{
	final double SCALE = 500;
	double kraxHeight;
	double kraxWidth;
	
	//Is to find the height and width of the data set
	double maxX = 0; //Is 892638.21114
	double minX = 900000; //Is 442254.35659
	double maxY = 0; //Is 6402050.98297
	double minY = 6500000; //Is 6049914.43018
	
	//To reposition the output so it is moved to the right spot
	double repositionX;
	double repositionY;
	
	HashMap<Integer, Color> roadColors = new HashMap<Integer, Color>();
	HashMap<Integer, Integer> roadWidths = new HashMap<Integer, Integer>();

	/**
	 * The main method - only used for testing this specific class
	 * @param args
	 */
	public static void main(String[] args) {
		XML xml = new XML();
		long startTime = System.currentTimeMillis();
//		xml.findMinAndMaxValue(xml.createRoadsForTesting());
		HashSet<Road> tempRoadHash = new HashSet<Road>();
		Road[] roads;
		try {
			double krakLoadStart = System.currentTimeMillis();
			Collection<Node> nodes = KrakLoader.load("C:\\Users\\Yndal\\Desktop\\Dropbox\\1. årsprojekt - gruppe 1\\krak-data\\kdv_node_unload.txt", 
					"C:\\Users\\Yndal\\Desktop\\Dropbox\\1. årsprojekt - gruppe 1\\krak-data\\kdv_unload.txt");
			double krakLoadEnd = System.currentTimeMillis();
			double rearrangeStart = System.currentTimeMillis();
			for (Node n : nodes) {
				for (Road r : n.getRoads()) {
					tempRoadHash.add(r);
				}
			}
			roads = new Road[tempRoadHash.size()];
			Iterator<Road> tempRoadIt = tempRoadHash.iterator();
			for(int index=0; index<tempRoadHash.size(); index++){
				roads[index] = tempRoadIt.next();
			}
			double rearrangeEnd = System.currentTimeMillis();
			double xmlFileStart = System.currentTimeMillis();
//			xml.createString(roads);
			xml.createFile(roads, "C:\\Users\\Yndal\\Desktop\\krax.xml");
			double xmlFileEnd = System.currentTimeMillis();
//			String returnedString = xml.createString(xml.createRoadsForTesting(10000));
//			xmlCreator.createXML(krakEdgeGraph, Color.red, 5, "krax.xml");
			double endTime = System.currentTimeMillis();
			
			
			
			System.out.println("Time taken in seconds");
			System.out.println("KrakLoader.load(): " + (krakLoadEnd - krakLoadStart)/1000);
			System.out.println("Rearrange: " + (rearrangeEnd - rearrangeStart)/1000);
			System.out.println("Create file: " + (xmlFileEnd - xmlFileStart)/1000);
			System.out.println("\nTotal: " + (endTime - startTime)/1000);
			
		} catch (Exception e){
			System.out.println("Exception thrown: " + e.getMessage());
		}
		
		
	}
	
	/**
	 * The Constructor
	 * Makes sure all the road colors and widths are loaded when instantiating this class
	 */
	public XML(){
		loadAllRoadColors();
		loadAllRoadWidths();
	}
	
	/**
	 * Will load all the predefined road colors to a HashMap - only called in the constructor
	 */
	private void loadAllRoadColors(){
		Color largeRoads = Color.red;
		Color mediumRoads = Color.yellow;
		Color smallRoads = Color.black;
		Color tinyRoads = Color.pink;
		Color tunnels = Color.orange;
		Color seaWays  = Color.blue;
		Color walkingPaths = Color.green;
//		Color bicyclePaths = Color.gray;
		
		Color unknownRoads = Color.cyan;
		
		roadColors.put(0, unknownRoads); //"Unknown0"
		roadColors.put(95, unknownRoads); //"Unknown95"
		roadColors.put(1, largeRoads); //"Motorvej"
		roadColors.put(2, mediumRoads); //"Motortrafikvej"
		roadColors.put(3, mediumRoads); //"PrimearruteOver6m"
		roadColors.put(4, mediumRoads); //"SekundearOver6m"
		roadColors.put(5, smallRoads); //"Vej3til6m"
		roadColors.put(6, smallRoads); //"AndenVej"
		roadColors.put(8, walkingPaths); //"Sti"
		roadColors.put(10, tinyRoads); //"Markvej"
		roadColors.put(11, walkingPaths); //"Gaagader //"
		roadColors.put(21, largeRoads); //"Proj.motorvej"
		roadColors.put(22, mediumRoads); //"Proj.motortrafikvej"
		roadColors.put(23, mediumRoads); //"Proj.primearvej"
		roadColors.put(24, mediumRoads); //"Proj.sekundearvej"
		roadColors.put(25, smallRoads); //"Proj.vej3til6m"
		roadColors.put(26, smallRoads); //"Proj.vejUnder3m"
		roadColors.put(28, walkingPaths); //"Proj.sti"
		roadColors.put(31, largeRoads); //"Motorvejsafkoersel"
		roadColors.put(32, mediumRoads); //"Motortrafikvejsafkoersel"
		roadColors.put(33, mediumRoads); //"Primearvejsafkoersel"
		roadColors.put(34, mediumRoads); //"Sekundearvejsafkoersel"
		roadColors.put(35, smallRoads); //"AndenVejafkoersel"
		roadColors.put(41, tunnels); //"Motorvejstunnel"
		roadColors.put(42, tunnels); //"Motortrafikvejstunnel"
		roadColors.put(43, tunnels); //"Primaerstunnel"
		roadColors.put(44, tunnels); //"Sekundaervejstunnel"
		roadColors.put(45, tunnels); //"AndenVejtunnel"
		roadColors.put(46, tunnels); //"MindreVejtunnel"
		roadColors.put(48, tunnels); //"Stitunnel"
		roadColors.put(80, seaWays); //"Faergeforbindelser"
		roadColors.put(99, unknownRoads); //"StednavneEksaktBeliggendeUkendt"
	}
	
	
	/**
	 * Will load all the predefined road widths to a HashMap - only called in the constructor
	 */
	private void loadAllRoadWidths(){
		int largeRoads = 5;
		int mediumRoads = 4;
		int smallRoads = 3;
		int tinyRoads = 2;
		int tunnels = 4;
		int seaWays  = 5; 
		int walkingPaths = 2;
//		int bicyclePaths = 2;
		
		int unknownRoads = 4;
				
		roadWidths.put(0, unknownRoads); //"Unknown0"
		roadWidths.put(95, unknownRoads); //"Unknown95"
		roadWidths.put(1, largeRoads); //"Motorvej"
		roadWidths.put(2, mediumRoads); //"Motortrafikvej"
		roadWidths.put(3, mediumRoads); //"PrimearruteOver6m"
		roadWidths.put(4, mediumRoads); //"SekundearOver6m"
		roadWidths.put(5, smallRoads); //"Vej3til6m"
		roadWidths.put(6, smallRoads); //"AndenVej"
		roadWidths.put(8, walkingPaths); //"Sti"
		roadWidths.put(10, tinyRoads); //"Markvej"
		roadWidths.put(11, walkingPaths); //"Gaagader //"
		roadWidths.put(21, largeRoads); //"Proj.motorvej"
		roadWidths.put(22, mediumRoads); //"Proj.motortrafikvej"
		roadWidths.put(23, mediumRoads); //"Proj.primearvej"
		roadWidths.put(24, mediumRoads); //"Proj.sekundearvej"
		roadWidths.put(25, smallRoads); //"Proj.vej3til6m"
		roadWidths.put(26, smallRoads); //"Proj.vejUnder3m"
		roadWidths.put(28, walkingPaths); //"Proj.sti"
		roadWidths.put(31, largeRoads); //"Motorvejsafkoersel"
		roadWidths.put(32, mediumRoads); //"Motortrafikvejsafkoersel"
		roadWidths.put(33, mediumRoads); //"Primearvejsafkoersel"
		roadWidths.put(34, mediumRoads); //"Sekundearvejsafkoersel"
		roadWidths.put(35, smallRoads); //"AndenVejafkoersel"
		roadWidths.put(41, tunnels); //"Motorvejstunnel"
		roadWidths.put(42, tunnels); //"Motortrafikvejstunnel"
		roadWidths.put(43, tunnels); //"Primaerstunnel"
		roadWidths.put(44, tunnels); //"Sekundaervejstunnel"
		roadWidths.put(45, tunnels); //"AndenVejtunnel"
		roadWidths.put(46, tunnels); //"MindreVejtunnel"
		roadWidths.put(48, tunnels); //"Stitunnel"
		roadWidths.put(80, seaWays); //"Faergeforbindelser"
		
		roadWidths.put(99, unknownRoads); //"StednavneEksaktBeliggendeUkendt"
	}
	
	
	
	/**
	 * This is called every time the class will make a string or file
	 * Will find the highest and lowest x-coordinate and y-coordinate
	 * @param allRoads The roads to find coordinates from
	 */
	public void findMinAndMaxValue(Road[] allRoads){
		double tempX1;
		double tempY1;
		double tempX2;
		double tempY2;
		
		for(Road road : allRoads){
			tempX1 = road.x1;
			tempY1 = road.y1;
			tempX2 = road.x2;
			tempY2 = road.y2;
			
			if(minX > tempX1) minX=tempX1;
			if(maxX < tempX1) maxX=tempX1;
			if(minY > tempY1) minY=tempY1;
			if(maxY < tempY1) maxY=tempY1;
			
			if(minX > tempX2) minX=tempX2;
			if(maxX < tempX2) maxX=tempX2;
			if(minY > tempY2) minY=tempY2;
			if(maxY < tempY2) maxY=tempY2;
		}
		
		repositionX = minX/SCALE;
		repositionY = minY/SCALE;
		kraxWidth = maxX/SCALE - repositionX;
		kraxHeight = maxY/SCALE - repositionY;
//		System.out.println("MinX: " + minX + " (not affected by the scale!)");
//		System.out.println("MaxX: " + maxX + " (not affected by the scale!)");
//		System.out.println("MinY: " + minY + " (not affected by the scale!)");
//		System.out.println("MaxY: " + maxY + " (not affected by the scale!)");
	}
	
	/**
	 * This method is only for testing
	 * @return Road[] An array of roads
	 */
	public Road[] createRoadsForTesting(){
		Road[] roads = new Road[5];
			
		roads[0] = new Road(0, 0, 1000, 1000, 1, "First Road");
		roads[1] = new Road(1000, 1000, 2000, 0, 1, "Second Road Road");
		roads[2] = new Road(1000, 1000, 0, 2000, 1, "Third Road");
		roads[3] = new Road(1000, 1000, 2000, 2000, 1, "Fourth Road");
		roads[4] = new Road(2000, 2000, 4000, 2000, 1, "Fifth Road");
		
		return roads;
	}
	
	/**
	 * Method used for testing performance
	 * @param numberOfRoads The number of roads to be created
	 * @return Road[] The roads created
	 */
	public Road[] createRoadsForTesting(int numberOfRoads){
		Road[] roads = new Road[numberOfRoads];
		
		for(int index=0; index<numberOfRoads; index++){
			roads[index] = new Road(0+1, 0+1, 100+1, 100+1, 1, "Road number: 0" + index);
		}
		
		return roads;
	}
	
	/**
	 *	Will create a xml-string with a svg-element containing lines
	 * 
	 * @param roads All the roads to put into the xml-string
	 * @return String with the xml containing the svg-element
	 */
	public String createString(Road[] roads){
		findMinAndMaxValue(roads);
		String root = "root_element";
		String xmlString = "";
		
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document document = docBuilder.newDocument();
			
			Element rootElement = document.createElement(root);
			document.appendChild(rootElement);
		
			//The outer svg element 
			Element svgElement = document.createElement("svg");
			svgElement.setAttribute("xmlns", "http://www.w3.org/2000/svg");
			svgElement.setAttribute("version", "1.1");
			svgElement.setAttribute("width", kraxWidth +"");
			svgElement.setAttribute("height", kraxHeight +"");
			
			for(Road road : roads){
				Element line = document.createElement("line");

				line.setAttribute("x1", Double.toString((road.x1/SCALE) + repositionX)); // (X/SCALE) + (repositionX)
				line.setAttribute("y1", Double.toString((road.y1/SCALE)*(-1) + kraxHeight + repositionY)); // (Y/SCALE)*(-1) + kraxHeight + repositionY this will calculate the correct Y (have to be "turned around") in the correct scale
				line.setAttribute("x2", Double.toString((road.x2/SCALE) + repositionX)); // (X/SCALE) + (repositionX)
				line.setAttribute("y2", Double.toString((road.y2/SCALE)*(-1) + kraxHeight + repositionY)); // (Y/SCALE)*(-1) + kraxHeight + repositionY
				line.setAttribute("style", "stroke:RGB(" + roadColors.get(road.type).getRed() + "," + 
						roadColors.get(road.type).getGreen() + "," + roadColors.get(road.type).getBlue() +
						"); strokeWidth:" + roadWidths.get(road.type));
				svgElement.appendChild(line);
			}
			rootElement.appendChild(svgElement);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
		    Transformer transformer = transformerFactory.newTransformer();
		    DOMSource source = new DOMSource(document);
		    
		    //TODO May want to add a larger buffer by telling the constructor 
		    StringWriter stringWriter = new StringWriter();
		    StreamResult result = new StreamResult(stringWriter);
	      	transformer.transform(source, result);
		    
		    xmlString = stringWriter.toString();
		    
		    //TODO Create better error handling
		} catch (ParserConfigurationException e){
			System.out.println("ParserConfigurationException: " + e.getMessage());
		} catch (TransformerConfigurationException e){
			System.out.println("TransformerConfigurationException: " + e.getMessage());
		} catch (TransformerException e){
			System.out.println("TransformerException: " + e.getMessage());
		}
		return xmlString;
	}
	
	
	/*
	*//**
	 * Will compare the two nodes connected to an edge object and create a .xml file with the coordinates from the two Node objects.
	 * 
	 * @param krakEdgeGraph Will need to use the notes and edges created during the load() method in KrakEdgeGraph
	 * @param colorForRoad The color which will be used for the roads
	 * @param widthForRoad The width which will be used for the roads
	 */
	public void createFile(Road[] roads, String filename){
		findMinAndMaxValue(roads);
		String root = "root_element";
		
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document document = docBuilder.newDocument();
			
			Element rootElement = document.createElement(root);
			document.appendChild(rootElement);
		
			//The outer svg element 
			Element svgElement = document.createElement("svg");
			svgElement.setAttribute("xmlns", "http://www.w3.org/2000/svg");
			svgElement.setAttribute("version", "1.1");
			svgElement.setAttribute("width", kraxWidth +"");
			svgElement.setAttribute("height", kraxHeight +"");
			
			for(Road road : roads){
				Element line = document.createElement("line");

				line.setAttribute("x1", Double.toString((road.x1/SCALE) - repositionX)); // (X/SCALE) + (repositionX)
				line.setAttribute("y1", Double.toString((road.y1/SCALE)*(-1) + kraxHeight + repositionY)); // (Y/SCALE)*(-1) + kraxHeight + repositionY this will calculate the correct Y (have to be "turned around") in the correct scale
				line.setAttribute("x2", Double.toString((road.x2/SCALE) - repositionX)); // (X/SCALE) + (repositionX)
				line.setAttribute("y2", Double.toString((road.y2/SCALE)*(-1) + kraxHeight + repositionY)); // (Y/SCALE)*(-1) + kraxHeight + repositionY
				line.setAttribute("style", "stroke:RGB(" + roadColors.get(road.type).getRed() + "," + 
						roadColors.get(road.type).getGreen() + "," + roadColors.get(road.type).getBlue() +
						"); strokeWidth:" + roadWidths.get(road.type));
				svgElement.appendChild(line);
			}
			rootElement.appendChild(svgElement);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
		    Transformer transformer = transformerFactory.newTransformer();
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		    DOMSource source = new DOMSource(document);
		    
		    StreamResult result = new StreamResult(new File(filename));
		    
	      	transformer.transform(source, result);
		    
		    //TODO Create better error handling
		} catch (ParserConfigurationException e){
			System.out.println("ParserConfigurationException: " + e.getMessage());
		} catch (TransformerConfigurationException e){
			System.out.println("TransformerConfigurationException: " + e.getMessage());
		} catch (TransformerException e){
			System.out.println("TransformerException: " + e.getMessage());
		}
	}		
	
	/**
	 * Used by the createXML method.
	 * Will put all the road types (being groups in the root element) into the root element
	 * @param roadTypes All the different types of roads 
	 * @param rootElement The root element
	 */
/*	private void loadRoadTypesIntoRootElement(HashMap<Integer, Element> roadTypes, Element rootElement){
		for(Element element : roadTypes.values()){
			rootElement.appendChild(element);
		}
	}
*/	
	/**
	* Method for the creation of all the different road types according to the krak-file
	*/
/*	private HashMap<Integer, Element> createRoadTypes(Document document){
			HashMap<Integer, Element> roadTypes = new HashMap<Integer, Element>();
			
			roadTypes.put(0, document.createElement("Unknown0"));
			createRoadTypesHelper(0, roadTypes, document);
			roadTypes.put(95, document.createElement("Unknown95"));
			createRoadTypesHelper(95, roadTypes, document);
			roadTypes.put(1, document.createElement("Motorvej"));
			createRoadTypesHelper(1, roadTypes, document);
			roadTypes.put(2, document.createElement("Motortrafikvej"));
			createRoadTypesHelper(2, roadTypes, document);
			roadTypes.put(3, document.createElement("PrimearruteOver6m"));
			createRoadTypesHelper(3, roadTypes, document);
			roadTypes.put(4, document.createElement("SekundearOver6m"));
			createRoadTypesHelper(4, roadTypes, document);
			roadTypes.put(5, document.createElement("Vej3til6m"));
			createRoadTypesHelper(5, roadTypes, document);
			roadTypes.put(6, document.createElement("AndenVej"));
			createRoadTypesHelper(6, roadTypes, document);
			roadTypes.put(8, document.createElement("Sti"));
			createRoadTypesHelper(8, roadTypes, document);
			roadTypes.put(10, document.createElement("Markvej"));
			createRoadTypesHelper(10, roadTypes, document);
			roadTypes.put(11, document.createElement("Gaagader"));
			createRoadTypesHelper(11, roadTypes, document);
			roadTypes.put(21, document.createElement("Proj.motorvej"));
			createRoadTypesHelper(21, roadTypes, document);
			roadTypes.put(22, document.createElement("Proj.motortrafikvej"));
			createRoadTypesHelper(22, roadTypes, document);
			roadTypes.put(23, document.createElement("Proj.primearvej"));
			createRoadTypesHelper(23, roadTypes, document);
			roadTypes.put(24, document.createElement("Proj.sekundearvej"));
			createRoadTypesHelper(24, roadTypes, document);
			roadTypes.put(25, document.createElement("Proj.vej3til6m"));
			createRoadTypesHelper(25, roadTypes, document);
			roadTypes.put(26, document.createElement("Proj.velUnder3m"));
			createRoadTypesHelper(26, roadTypes, document);
			roadTypes.put(28, document.createElement("Proj.sti"));
			createRoadTypesHelper(28, roadTypes, document);
			roadTypes.put(31, document.createElement("Motorvejsafkoersel"));
			createRoadTypesHelper(31, roadTypes, document);
			roadTypes.put(32, document.createElement("Motortrafikvejsafkoersel"));
			createRoadTypesHelper(32, roadTypes, document);
			roadTypes.put(33, document.createElement("Primearvejsafkoersel"));
			createRoadTypesHelper(33, roadTypes, document);
			roadTypes.put(34, document.createElement("Sekundearvejsafkoersel"));
			createRoadTypesHelper(34, roadTypes, document);
			roadTypes.put(35, document.createElement("AndenVejafkoersel"));
			createRoadTypesHelper(35, roadTypes, document);
			roadTypes.put(41, document.createElement("Motorvejstunnel"));
			createRoadTypesHelper(41, roadTypes, document);
			roadTypes.put(42, document.createElement("Motortrafikvejstunnel"));
			createRoadTypesHelper(42, roadTypes, document);
			roadTypes.put(43, document.createElement("Primaerstunnel"));
			createRoadTypesHelper(43, roadTypes, document);
			roadTypes.put(44, document.createElement("Sekundaervejstunnel"));
			createRoadTypesHelper(44, roadTypes, document);
			roadTypes.put(45, document.createElement("AndenVejtunnel"));
			createRoadTypesHelper(45, roadTypes, document);
			roadTypes.put(46, document.createElement("MindreVejtunnel"));
			createRoadTypesHelper(46, roadTypes, document);
			roadTypes.put(48, document.createElement("Stitunnel"));
			createRoadTypesHelper(48, roadTypes, document);
			roadTypes.put(80, document.createElement("Faergeforbindelser"));
			createRoadTypesHelper(80, roadTypes, document);
			roadTypes.put(99, document.createElement("StednavneEksaktBeliggendeUkendt"));
			createRoadTypesHelper(99, roadTypes, document);
			
		return roadTypes;
	}
*/	
	/**
	 * Used to put every type of road into a group called "svg" (to make it drawable)
	 * but with the attributes: typ and name, describing the type of road and the
	 * danish name
	 * 
	 * @param key The key to the element in the HashMap of element (key == typ of road)
	 * @param roadTypes The HashMap containing all the elements
	 * @param document The document for creating the xml structure
	 */
/*	private void createRoadTypesHelper(int key, HashMap<Integer, Element> roadTypes, Document document){
		Element tempElement = document.createElement("svg");
		tempElement.setAttribute("typ", key + "");
		tempElement.setAttribute("name", roadTypes.get(key).getTagName());
		roadTypes.get(key).appendChild(tempElement);
	}
*/	
}