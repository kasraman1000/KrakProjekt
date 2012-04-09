import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class is for the whole XML methods: 
 * 	It can create a string or file from a Road[]
 * 	It can create a Road[] from a file or string
 * 
 * @author Group KRAX
 *
 */
public class XML{
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
			roads[index] = new Road(0+index, 0+index, 100+index, 100+index, 1, "Road number: " + index);
		}
		
		return roads;
	}
	
	/**
	 * Will create a xml-string with a svg-element containing lines
	 * 
	 * @param roads All the roads to put into the xml-string
	 * @return String with the xml containing the svg-element
	 * @throws ParserConfigurationException
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	public String createString(Road[] roads) throws ParserConfigurationException, 
													TransformerConfigurationException,
													TransformerException{
		Document document = convertRoadArrayToDocument(roads);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(document);
	    
	    //TODO May want to add a larger buffer by telling the constructor (fx. StringWriter(1024)) 
	    StringWriter stringWriter = new StringWriter();
	    StreamResult result = new StreamResult(stringWriter);
      	transformer.transform(source, result);
	    String xmlString = stringWriter.toString();
	    	    
	    return xmlString;
	}
	
	
	/**
	 * Will create a xml-string with a svg-element containing lines from the Road[] given
	 * 
	 * @param roads All the roads to put into the xml-file
	 * @param filename Name of the file going to be created. Remember to write // if saving in a certain folder
	 * @throws ParserConfigurationException
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	public void createFile(Road[] roads, String filename) throws ParserConfigurationException, 
														TransformerConfigurationException,
														TransformerException{
		
		Document document = convertRoadArrayToDocument(roads);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    DOMSource source = new DOMSource(document);
	    
	    StreamResult result = new StreamResult(new File(filename));
	    
      	transformer.transform(source, result);
	}		
	
	
	/**
	 * Will create a XML-file with groups of the road types
	 * 
	 * @param roads All the roads to put into the xml-file
	 * @param filename Name of the file going to be created. Remember to write // if saving in a certain folder
	 * @throws ParserConfigurationException
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	//TODO This method can be seperated just like the two other create-methods - if we are going with the group structure
	public void createFileWithGroups(Road[] roads, String filename) throws ParserConfigurationException, 
																			TransformerConfigurationException, 
																			TransformerException{
			String root = "root_element";
			HashMap<Integer, Element> roadTypes;
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document document = docBuilder.newDocument();
			
			//First the root
			Element rootElement = document.createElement(root);
			document.appendChild(rootElement);
		
			//Create all the different groups (determined by the road type)
			roadTypes = createRoadTypes(document);
			
			//The outer svg element 
			Element svgElement = document.createElement("svg");
			svgElement.setAttribute("xmlns", "http://www.w3.org/2000/svg");
			svgElement.setAttribute("version", "1.1");
//			svgElement.setAttribute("width", Controller.getMaxXScaled() +"");
//			svgElement.setAttribute("height", Controller.getMaxYScaled() +"");
			
			for(Road road : roads){
//				System.out.println("Inside road loop in createFileUnderTesting():");
//				System.out.println("roads.length: " + roads.length);
//				System.out.println("x1: " + road.x1);
//				System.out.println("y1: " + road.y1);
//				System.out.println("x2: " + road.x2);
//				System.out.println("y2: " + road.y2);
//				
				
				Element line = document.createElement("line");

				line.setAttribute("x1", road.getX1() + ""); 
				line.setAttribute("y1", road.getY1() + ""); 
				line.setAttribute("x2", road.getX2() + ""); 
				line.setAttribute("y2", road.getY2() + ""); 
				line.setAttribute("style", "stroke:RGB(" +	Controller.getRoadColor(road.getType()).getRed() + "," + 
															Controller.getRoadColor(road.getType()).getGreen() + "," + 
															Controller.getRoadColor(road.getType()).getBlue() + "); " + 
						"strokeWidth:" + Controller.getRoadWidth(road.getType()));
				line.setAttribute("roadType", road.getType() + "");
				line.setAttribute("roadName", road.getName() + "");
				
				//The next lines will add the road to the right group and increase the counter in the groups' attribute
				int roadCount = Integer.valueOf(roadTypes.get(road.getType()).getAttribute("amount")) + 1;
				roadTypes.get(road.getType()).appendChild(line);
				roadTypes.get(road.getType()).setAttribute("amount", roadCount +"");
				
			}
			
			//Load all the groups into the svg-element
			for(Element element : roadTypes.values()){
				svgElement.appendChild(element);
			}
			
			rootElement.appendChild(svgElement);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
		    Transformer transformer = transformerFactory.newTransformer();
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		    DOMSource source = new DOMSource(document);
		    
		    StreamResult result = new StreamResult(new File(filename));
		    
	      	transformer.transform(source, result);    
		}		
	
	
	/**
	 * Generates a file with the chosen name and path (as a part of the filename) from the string with the XML informations
	 * 
	 * @param fileName Name of the file (may include a path by using // for folder seperation)
	 * @param roadTypesToExtract An array of int's with the road types wanted from the file
	 * @return Road[] An array of the roads in the wanted road types, chosen in the parameter called RoadTypesToExtract
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public Road[] getRoadsFromFile(String fileName, int[] roadTypesToExtract) throws IOException, ParserConfigurationException, SAXException{
		File file = new File(fileName);
		Road[] foundRoads = null;
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	
		Document document = docBuilder.parse(file);
		
		//Will make the structure of the XML document 
		document.getDocumentElement().normalize();
		
		foundRoads = convertDocumentToRoadArray(document, roadTypesToExtract);
			
		return foundRoads;
	}
	
	
	/**
	 * Generates an array of roads from a XML-string
	 * 
	 * @param xmlString The string containing the XML informations
	 * @param roadTypesToExtract An array of int's with the road types wanted from the string
	 * @return Road[] An array of the roads in the wanted road types, chosen in the parameter called RoadTypesToExtract
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public Road[] getRoadsFromString(String xmlString, int[] roadTypesToExtract) throws IOException, ParserConfigurationException, SAXException{
		Road[] foundRoads = null;
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	
		Document document = docBuilder.parse(xmlString);
		
 
		document.getDocumentElement().normalize();
		
		foundRoads = convertDocumentToRoadArray(document, roadTypesToExtract);

		return foundRoads;
	}

	
	private Road[] convertDocumentToRoadArray(Document document, int[] roadTypesToExtract){
		//Must go through the xml to see how large the returning Road[] has to be
		int totalNumberOfRoads = 0;
		for(int roadType : roadTypesToExtract){
			NodeList nodeList = document.getElementsByTagName("type" + roadType);
			
			//The next loop will probably only run once for each nametag
			for(int index = 0; index < nodeList.getLength(); index++){
				Element roadGroup = (Element) nodeList.item(index);
				totalNumberOfRoads += Integer.valueOf(roadGroup.getAttribute("amount"));
			}
		}
		
		Road[] foundRoads = new Road[totalNumberOfRoads];
		
		//This loop is the actual extraction of the data
		//First a loop for all the groups
		int roadIndex = 0;
		for(int roadType : roadTypesToExtract){
			NodeList nodeList = document.getElementsByTagName("type" + roadType);
			
			//This loop will probably only run once for each nametag
			for(int index = 0; index < nodeList.getLength(); index++){
				Element roadGroup = (Element) nodeList.item(index);
		
				//Then for all the nodes
				NodeList nodes = roadGroup.getElementsByTagName("line");
				for(int nodeNumber = 0; nodeNumber < nodes.getLength(); nodeNumber++){
					Element roadElement = (Element) nodes.item(nodeNumber);
					
					double x1 = Double.valueOf(roadElement.getAttribute("x1"));
					double y1 = Double.valueOf(roadElement.getAttribute("y1"));
					double x2 = Double.valueOf(roadElement.getAttribute("x2"));
					double y2 = Double.valueOf(roadElement.getAttribute("y2"));
					String roadName = roadElement.getAttribute("roadName");
					int type = Integer.valueOf(roadElement.getAttribute("roadType"));
					
					foundRoads[roadIndex] = new Road(x1, y1, x2, y2, type, roadName);
					roadIndex++;
				}
			}
		}
		return foundRoads;
	}
	
	
	private Document convertRoadArrayToDocument(Road[] roads) throws ParserConfigurationException, TransformerConfigurationException{
		String root = "root_element";
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document document = docBuilder.newDocument();
		
//		Element rootElement = document.createElement(root);
//		document.appendChild(rootElement);
		//The outer svg element 
		Element svgElement = document.createElement("svg");
		svgElement.setAttribute("xmlns", "http://www.w3.org/2000/svg");
		svgElement.setAttribute("version", "1.1");
		document.appendChild(svgElement);
//		svgElement.setAttribute("width", Controller.getMaxXScaled() +"");
//		svgElement.setAttribute("height", Controller.getMaxYScaled() +"");
		Element gElement = document.createElement("g");
		svgElement.appendChild(gElement);
		
		for(Road road : roads){
			Element line = document.createElement("line");

			line.setAttribute("x1", road.getX1() + ""); 
			line.setAttribute("y1", road.getY1() + ""); 
			line.setAttribute("x2", road.getX2() + ""); 
			line.setAttribute("y2", road.getY2() + ""); 
			line.setAttribute("style", "stroke:RGB(" +	Controller.getRoadColor(road.getType()).getRed() + "," + 
														Controller.getRoadColor(road.getType()).getGreen() + "," + 
														Controller.getRoadColor(road.getType()).getBlue() + "); " + 
										"stroke-width:" + Controller.getRoadWidth(road.getType()));
//			line.setAttribute("roadType", road.getType() + "");
//			line.setAttribute("roadName", road.getName() + "");
			gElement.appendChild(line);
		}
//		rootElement.appendChild(svgElement);
			
	    return document;
	}
	
	/**
	* Method for the creation of all the different road types according to the krak-file
	*/
	private HashMap<Integer, Element> createRoadTypes(Document document){
			HashMap<Integer, Element> roadTypes = new HashMap<Integer, Element>();
	
			Element element = document.createElement("type0");
			element.setAttribute("description", "Unknown0");
			roadTypes.put(0, element);
		
			element = document.createElement("type95");
			element.setAttribute("description", "Unknown95");
			roadTypes.put(95, element);
		
			
			element = document.createElement("type1");
			element.setAttribute("description", "Motorvej");
			roadTypes.put(1, element);
		
			element = document.createElement("type2");
			element.setAttribute("description", "Motortrafikvej");
			roadTypes.put(2, element);
		
			element = document.createElement("type3");
			element.setAttribute("description", "PrimearruteOver6m");
			roadTypes.put(3, element);
			
			element = document.createElement("type4");
			element.setAttribute("description", "SekundearOver6m");
			roadTypes.put(4, element);
			
			element = document.createElement("type5");
			element.setAttribute("description", "Vej3til6m");
			roadTypes.put(5, element);
			
			element = document.createElement("type6");
			element.setAttribute("description", "AndenVej");
			roadTypes.put(6, element);
			
			element = document.createElement("type8");
			element.setAttribute("description", "Sti");
			roadTypes.put(8, element);
			
			element = document.createElement("type10");
			element.setAttribute("description", "Markvej");
			roadTypes.put(10, element);
			
			element = document.createElement("type11");
			element.setAttribute("description", "Gaagader");
			roadTypes.put(11, element);
			
			element = document.createElement("type21");
			element.setAttribute("description", "Proj.motorvej");
			roadTypes.put(21, element);
			
			element = document.createElement("type22");
			element.setAttribute("description", "Proj.motortrafikvej");
			roadTypes.put(22, element);
			
			element = document.createElement("type23");
			element.setAttribute("description", "Proj.primearvej");
			roadTypes.put(23, element);
			
			element = document.createElement("type24");
			element.setAttribute("description", "Proj.sekundearvej");
			roadTypes.put(24, element);
			
			element = document.createElement("type25");
			element.setAttribute("description", "Proj.vej3til6m");
			roadTypes.put(25, element);
			
			element = document.createElement("type26");
			element.setAttribute("description", "Proj.velUnder3m");
			roadTypes.put(26, element);
			
			element = document.createElement("type28");
			element.setAttribute("description", "Proj.sti");
			roadTypes.put(28, element);
			
			element = document.createElement("type31");
			element.setAttribute("description", "Motorvejsafkoersel");
			roadTypes.put(31, element);
			
			element = document.createElement("type32");
			element.setAttribute("description", "Motortrafikvejsafkoersel");
			roadTypes.put(32, element);
			
			element = document.createElement("type33");
			element.setAttribute("description", "Primearvejsafkoersel");
			roadTypes.put(33, element);
			
			element = document.createElement("type34");
			element.setAttribute("description", "Sekundearvejsafkoersel");
			roadTypes.put(34, element);
			
			element = document.createElement("type35");
			element.setAttribute("description", "AndenVejafkoersel");
			roadTypes.put(35, element);
			
			element = document.createElement("type41");
			element.setAttribute("description", "Motorvejstunnel");
			roadTypes.put(41, element);
			
			element = document.createElement("type42");
			element.setAttribute("description", "Motortrafikvejstunnel");
			roadTypes.put(42, element);
			
			element = document.createElement("type43");
			element.setAttribute("description", "Primaerstunnel");
			roadTypes.put(43, element);
			
			element = document.createElement("type44");
			element.setAttribute("description", "Sekundaervejstunnel");
			roadTypes.put(44, element);
			
			element = document.createElement("type45");
			element.setAttribute("description", "AndenVejtunnel");
			roadTypes.put(45, element);
			
			element = document.createElement("type46");
			element.setAttribute("description", "MindreVejtunnel");
			roadTypes.put(46, element);
			
			element = document.createElement("type48");
			element.setAttribute("description", "Stitunnel");
			roadTypes.put(48, element);
			
			element = document.createElement("type80");
			element.setAttribute("description", "Faergeforbindelser");
			roadTypes.put(80, element);
			
			element = document.createElement("type99");
			element.setAttribute("description", "StednavneEksaktBeliggendeUkendt");
			roadTypes.put(99, element);
			
			
			//To keep count of road in each group
			for(Element e : roadTypes.values()){
				e.setAttribute("amount", "0");
			}
	
			return roadTypes;
	}

}