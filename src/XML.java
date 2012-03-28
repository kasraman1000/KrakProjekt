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
 * This class is for the creation of the xml file or string.
 * It will generate the output from all of the roads, which is given as parameters.
 * 
 * @author Group KRAX
 *
 */
public class XML{
//	double scale;
//	double kraxHeight;
//	double kraxWidth;
	
//	//Is to find the height and width of the data set
//	double maxX = 0; //Is 892638.21114
//	double minX = 900000; //Is 442254.35659
//	double maxY = 0; //Is 6402050.98297
//	double minY = 6500000; //Is 6049914.43018
	
//	//To reposition the output so it is moved to the right spot
//	double repositionX;
//	double repositionY;
	
//	HashMap<Integer, Color> roadColors = new HashMap<Integer, Color>();
//	HashMap<Integer, Integer> roadWidths = new HashMap<Integer, Integer>();

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
	 */
	public XML(){
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
	//	DataHelper dataHelper = DataHelper.getInstance();
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
			svgElement.setAttribute("width", Controller.getMaxXCurrent() +"");
			svgElement.setAttribute("height", Controller.getMaxYCurrent() +"");
			
			Color tempColor;
			for(Road road : roads){
				Element line = document.createElement("line");

				line.setAttribute("x1", road.x1 + ""); 
				line.setAttribute("y1", road.y1 + ""); 
				line.setAttribute("x2", road.x2 + ""); 
				line.setAttribute("y2", road.y2 + ""); 
				line.setAttribute("style", "stroke:RGB(" +	Controller.getRoadColor(road.type).getRed() + "," + 
															Controller.getRoadColor(road.type).getGreen() + "," + 
															Controller.getRoadColor(road.type).getBlue() + "); " + 
											"strokeWidth:" + Controller.getRoadWidth(road.type));
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
			svgElement.setAttribute("width", Controller.getMaxXCurrent() +"");
			svgElement.setAttribute("height", Controller.getMaxYCurrent() +"");
			
			for(Road road : roads){
				Element line = document.createElement("line");

				line.setAttribute("x1", road.x1 + ""); 
				line.setAttribute("y1", road.y1 + ""); 
				line.setAttribute("x2", road.x2 + ""); 
				line.setAttribute("y2", road.y2 + ""); 
				line.setAttribute("style", "stroke:RGB(" +	Controller.getRoadColor(road.type).getRed() + "," + 
															Controller.getRoadColor(road.type).getGreen() + "," + 
															Controller.getRoadColor(road.type).getBlue() + "); " + 
						"strokeWidth:" + Controller.getRoadWidth(road.type));
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
}