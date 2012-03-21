import java.awt.Color;
import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
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
//	final BigDecimal SCALE = new BigDecimal(1000);
//	final BigDecimal KRAX_HEIGHT = new BigDecimal(352136).divide(SCALE); //6402050.98297-6049914.43018 ~ 352136
//	final BigDecimal KRAX_WIDTH = new BigDecimal(450384).divide(SCALE); //892638.21114-442254.35659 ~ 450384
	
	final double SCALE = 1;
	double kraxHeight = 352136/SCALE; //6402050.98297-6049914.43018 ~ 352136
	double kraxWidth = 450384/SCALE; //892638.21114-442254.35659 ~ 450384
	
	//Might be used later to add other shapes than just lines
	//String[] elements = new String[]{"line", "line","line","line"}; 
	
	//Is to find the height and width of the data set
	double maxX = 0; //Is 892638.21114
	double minX = 900000; //Is 442254.35659
	double maxY = 0; //Is 6402050.98297
	double minY = 6500000; //Is 6049914.43018
	
	//To reposition the out so it is moved to the right spot (for some reason it's not showed at the place)
//	final BigDecimal REPOSITION_X = new BigDecimal(-442254.356590).divide(SCALE);
//	final BigDecimal REPOSITION_Y = new BigDecimal(6049914.43018).divide(SCALE);
	
	double repositionX;
	double repositionY;
	
	HashMap<Integer, Color> roadColors = new HashMap<Integer, Color>();
	HashMap<Integer, Integer> roadWidths = new HashMap<Integer, Integer>();

	
	
	public XML(){
		loadAllRoadColors();
		loadAllRoadWidths();
	}
	
	private void loadAllRoadColors(){
		//TODO Define actual colors
		Color largeRoads = Color.black;
		Color mediumRoads = Color.black;
		Color smallRoads = Color.black;
		Color tunnels = Color.yellow;
		Color seaWays  = Color.blue;
		Color walkingPaths = Color.green;
		Color bicycle = Color.orange;
		
		//Just a temp color
		Color roads = Color.gray;
		
		roadColors.put(0, roads); //"Unknown0"
		roadColors.put(95, roads); //"Unknown95"
		roadColors.put(1, roads); //"Motorvej"
		roadColors.put(2, roads); //"Motortrafikvej"
		roadColors.put(3, roads); //"PrimearruteOver6m"
		roadColors.put(4, roads); //"SekundearOver6m"
		roadColors.put(5, roads); //"Vej3til6m"
		roadColors.put(6, roads); //"AndenVej"
		roadColors.put(8, paths); //"Sti"
		roadColors.put(10, roads); //"Markvej"
		roadColors.put(11, paths); //"Gaagader //"
		roadColors.put(21, roads); //"Proj.motorvej"
		roadColors.put(22, roads); //"Proj.motortrafikvej"
		roadColors.put(23, roads); //"Proj.primearvej"
		roadColors.put(24, roads); //"Proj.sekundearvej"
		roadColors.put(25, roads); //"Proj.vej3til6m"
		roadColors.put(26, roads); //"Proj.vejUnder3m"
		roadColors.put(28, roads); //"Proj.sti"
		roadColors.put(31, roads); //"Motorvejsafkoersel"
		roadColors.put(32, roads); //"Motortrafikvejsafkoersel"
		roadColors.put(33, roads); //"Primearvejsafkoersel"
		roadColors.put(34, roads); //"Sekundearvejsafkoersel"
		roadColors.put(35, roads); //"AndenVejafkoersel"
		roadColors.put(41, tunnels); //"Motorvejstunnel"
		roadColors.put(42, tunnels); //"Motortrafikvejstunnel"
		roadColors.put(43, tunnels); //"Primaerstunnel"
		roadColors.put(44, tunnels); //"Sekundaervejstunnel"
		roadColors.put(45, tunnels); //"AndenVejtunnel"
		roadColors.put(46, tunnels); //"MindreVejtunnel"
		roadColors.put(48, tunnels); //"Stitunnel"
		roadColors.put(80, seaWays); //"Faergeforbindelser"
		roadColors.put(99, roads); //"StednavneEksaktBeliggendeUkendt"
	}
	
	private void loadAllRoadWidths(){
		//TODO Define actual widths
		int roads = 3;
		int tunnels = 3;
		int seaWays  = 3; 
		int paths = 1;
		int bicycle = 1; 
		
		roadWidths.put(0, roads); //"Unknown0"
		roadWidths.put(95, roads); //"Unknown95"
		roadWidths.put(1, roads); //"Motorvej"
		roadWidths.put(2, roads); //"Motortrafikvej"
		roadWidths.put(3, roads); //"PrimearruteOver6m"
		roadWidths.put(4, roads); //"SekundearOver6m"
		roadWidths.put(5, roads); //"Vej3til6m"
		roadWidths.put(6, roads); //"AndenVej"
		roadWidths.put(8, paths); //"Sti"
		roadWidths.put(10, roads); //"Markvej"
		roadWidths.put(11, paths); //"Gaagader //"
		roadWidths.put(21, roads); //"Proj.motorvej"
		roadWidths.put(22, roads); //"Proj.motortrafikvej"
		roadWidths.put(23, roads); //"Proj.primearvej"
		roadWidths.put(24, roads); //"Proj.sekundearvej"
		roadWidths.put(25, roads); //"Proj.vej3til6m"
		roadWidths.put(26, roads); //"Proj.vejUnder3m"
		roadWidths.put(28, roads); //"Proj.sti"
		roadWidths.put(31, roads); //"Motorvejsafkoersel"
		roadWidths.put(32, roads); //"Motortrafikvejsafkoersel"
		roadWidths.put(33, roads); //"Primearvejsafkoersel"
		roadWidths.put(34, roads); //"Sekundearvejsafkoersel"
		roadWidths.put(35, roads); //"AndenVejafkoersel"
		roadWidths.put(41, tunnels); //"Motorvejstunnel"
		roadWidths.put(42, tunnels); //"Motortrafikvejstunnel"
		roadWidths.put(43, tunnels); //"Primaerstunnel"
		roadWidths.put(44, tunnels); //"Sekundaervejstunnel"
		roadWidths.put(45, tunnels); //"AndenVejtunnel"
		roadWidths.put(46, tunnels); //"MindreVejtunnel"
		roadWidths.put(48, tunnels); //"Stitunnel"
		roadWidths.put(80, seaWays); //"Faergeforbindelser"
		roadWidths.put(99, roads); //"StednavneEksaktBeliggendeUkendt"
	}
	
	
	
	/**
	 * 
	 * @param allRoads
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
//		System.out.println("MinX: " + minX + " (not effected by the scale!)");
//		System.out.println("MaxX: " + maxX + " (not effected by the scale!)");
//		System.out.println("MinY: " + minY + " (not effected by the scale!)");
//		System.out.println("MaxY: " + maxY + " (not effected by the scale!)");
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
//			svgElement.setAttribute("width", kraxWidth +"");
//			svgElement.setAttribute("height", kraxHeight +"");
			
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
	
/**	private Color getRoadColor(int roadType){
		//TODO May want to make these definitions fields because of system speed
		//TODO Maybe make it a HashMap (or even better: An enum, which has a runtime as the primitive types)
		//TODO Define actual colors
		Color roads = Color.black;
		Color tunnels = Color.yellow;
		Color seaWays  = Color.blue;
		Color paths = Color.green;
		Color bicycle = Color.orange;
		Color defaultColor = Color.gray;
		
		switch (roadType){
			case 0: return roads; //"Unknown0"0
			case 95: return roads; //"Unknown95"0
			case 1: return roads; //"Motorvej"1
			case 2: return roads; //"Motortrafikvej"1
			case 3: return roads; //"PrimearruteOver6m"2
			case 4: return roads; //"SekundearOver6m"2
			case 5: return roads; //"Vej3til6m"3
			case 6: return roads; //"AndenVej"3
			case 8: return paths; //"Sti"4
			case 10: return roads; //"Markvej"5
			case 11: return paths; //"Gaagader //"6
			case 21: return roads; //"Proj.motorvej"
			case 22: return roads; //"Proj.motortrafikvej"
			case 23: return roads; //"Proj.primearvej"
			case 24: return roads; //"Proj.sekundearvej"
			case 25: return roads; //"Proj.vej3til6m"
			case 26: return roads; //"Proj.vejUnder3m"
			case 28: return roads; //"Proj.sti"
			case 31: return roads; //"Motorvejsafkoersel"1
			case 32: return roads; //"Motortrafikvejsafkoersel"1
			case 33: return roads; //"Primearvejsafkoersel"2
			case 34: return roads; //"Sekundearvejsafkoersel"2
			case 35: return roads; //"AndenVejafkoersel"3
			case 41: return tunnels; //"Motorvejstunnel"1
			case 42: return tunnels; //"Motortrafikvejstunnel"1
			case 43: return tunnels; //"Primaerstunnel"2
			case 44: return tunnels; //"Sekundaervejstunnel"2
			case 45: return tunnels; //"AndenVejtunnel"3
			case 46: return tunnels; //"MindreVejtunnel"3
			case 48: return tunnels; //"Stitunnel"4
			case 80: return seaWays; //"Faergeforbindelser //"7
			case 99: return roads; //"StednavneEksaktBeliggendeUkendt"0
			default: return defaultColor; //8
		}
	}
*/	
	
	/**
	 * Will compare the two nodes connected to an edge object and create a .xml file with the coordinates from the two Node objects.
	 * The class has a feature implemented (set DEBUGGING to "true") for calculating the max and min value of the X and Y-coordinates. 
	 * 
	 * @param krakEdgeGraph Will need to use the notes and edges created during the load() method in KrakEdgeGraph
	 * @param colorForRoad The color which will be used for the roads
	 * @param widthForRoad The width which will be used for the roads
	 */
	public void createXML(KrakEdgeGraph krakEdgeGraph, Color colorForRoad, int widthForRoad, String nameForOutputFile) throws Exception{
		//findMinAndMaxValue(roads);
		Bag<Edge> edges = krakEdgeGraph.edges();
		Iterator<Edge> edgesIt = edges.iterator();
		HashMap<Integer, Node> nodes = krakEdgeGraph.getNotesUntouched();
		int roadWidth = widthForRoad;
		
		String roadColor = "RGB(" + colorForRoad.getRed() + "," + colorForRoad.getGreen() + "," + colorForRoad.getBlue() + ")";
		String root = "root_element";
		
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document document = docBuilder.newDocument();
			HashMap<Integer, Element> roadTypes = createRoadTypes(document);
			
			Element rootElement = document.createElement(root);
			document.appendChild(rootElement);
		
			//The outer svg element - actually not needed, but is used for defining the height and width 
	/**		Element svgElement = document.createElement("svg");
			svgElement.setAttribute("xmlns", "http://www.w3.org/2000/svg");
			svgElement.setAttribute("version", "1.1");
			svgElement.setAttribute("width", KRAX_WIDTH +"");
			svgElement.setAttribute("height",KRAX_HEIGHT +"");
		*/
			
		
			int id1;
			int id2;
			Edge tempEdge;
						
			while(edgesIt.hasNext()){
				tempEdge =  (Edge) edgesIt.next();
				id1 = tempEdge.either();
				id2 = tempEdge.other(id1);
				
				Element line = document.createElement("line"); //Could use elements[index] if different shapes are going to be added
				
				
				line.setAttribute("x1", "" + nodes.get(id1).getX().doubleValue()/SCALE + repositionX); // (X/SCALE) + (REPOSITION_X)
				line.setAttribute("y1", "" + (nodes.get(id1).getY().doubleValue()/SCALE)*(-1) + kraxHeight + repositionY); // (Y/SCALE)*(-1) + KRAX_HEIGHT + REPOSITION_Y this will calculate the correct Y (have to be "turned around") in the correct scale
				line.setAttribute("x2", "" + nodes.get(id2).getX().doubleValue()/SCALE + repositionX); // (X/SCALE) + (REPOSITION_X)
				line.setAttribute("y2", "" + (nodes.get(id2).getY().doubleValue()/SCALE)*(-1) + kraxHeight + repositionY); // (Y/SCALE)*(-1) + KRAX_HEIGHT + REPOSITION_Y
				line.setAttribute("style", "stroke:" + roadColor + "; strokeWidth:" + roadWidth);
				
				roadTypes.get(tempEdge.getRoadType()).getElementsByTagName("svg").item(0).appendChild(line);
				
			}
			loadRoadTypesIntoRootElement(roadTypes, rootElement);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
		    Transformer transformer = transformerFactory.newTransformer();
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		    DOMSource source = new DOMSource(document);
		    
		    System.out.println("Creating file...");
		    
		    StreamResult result = new StreamResult(new File(nameForOutputFile));
	    		    
		    //The line below is used for getting the result to the console :o)
//		    StreamResult result =  new StreamResult(System.out);
	
		    transformer.transform(source, result);
		    
		    System.out.println("File created!");
		   
		    //TODO Create better error handling
		} catch (ParserConfigurationException e){
			System.out.println("ParserConfigurationException: " + e.getMessage());
		} catch (TransformerConfigurationException e){
			System.out.println("TransformerConfigurationException: " + e.getMessage());
		} catch (TransformerException e){
			System.out.println("TransformerException: " + e.getMessage());
		}
	}		
	
	private void loadRoadTypesIntoRootElement(HashMap<Integer, Element> roadTypes, Element rootElement){
		for(Element element : roadTypes.values()){
			rootElement.appendChild(element);
		}
	}
	
	/**
	* Method for the creation of all the different road types according to the krak-file
	*/
	private HashMap<Integer, Element> createRoadTypes(Document document){
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
	
	/**
	 * Used to put every type of road into a group called "svg" (to make it drawable)
	 * but with the attributes: typ and name, describing the type of road and the
	 * danish name
	 * 
	 * @param key The key to the element in the HashMap of element (key == typ of road)
	 * @param roadTypes The HashMap containing all the elements
	 * @param document The document for creating the xml structure
	 */
	private void createRoadTypesHelper(int key, HashMap<Integer, Element> roadTypes, Document document){
		Element tempElement = document.createElement("svg");
		tempElement.setAttribute("typ", key + "");
		tempElement.setAttribute("name", roadTypes.get(key).getTagName());
		roadTypes.get(key).appendChild(tempElement);

	}
}