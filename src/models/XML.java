package models;
import java.awt.Color;
import java.io.File;
import java.io.StringWriter;

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
 * This class is for the whole XML methods: 
 * 	It can create a string or file from a Road[]
 * 	It can create a Road[] from a file or string
 * 
 * @author Group 1
 *
 */
public class XML{
	private final String ROOT_ELEMENT_NAME;
	private final String SVG_ELEMENT_NAME;
	private final String MAP_ELEMENT_NAME;
	private final String ROUTE_ELEMENT_NAME;
	private final String VIEWPORT_ELEMENT_NAME;
	private final String STATUSCODE_ELEMENT_NAME;
	
	public XML(){
		ROOT_ELEMENT_NAME = "root";
		SVG_ELEMENT_NAME = "svg";
		MAP_ELEMENT_NAME = "map";
		ROUTE_ELEMENT_NAME =  "route";
		VIEWPORT_ELEMENT_NAME =  "viewPort";
		STATUSCODE_ELEMENT_NAME = "statusCode";
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
			roads[index] = new Road(0+index, 0+index, 100+index, 100+index, 1, "Road number: " + index);
		}
		
		return roads;
	}
	
	public String createString(Road[] roads, Road[] route, Region region, StatusCode statusCode) throws ParserConfigurationException, 
																									TransformerConfigurationException, 
																									TransformerException{
		//Only for debugging - uncomment to debug
//		createFile(roads, route, region, statusCode, "C:\\Users\\Yndal\\Desktop\\xmlTest.xml");
		
		Document document = createDocument(roads, route, region, statusCode);
		
		//Transform the Document into a XML String and return the String
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(document);
	    
	    // May want to add a larger buffer by telling the constructor (fx. StringWriter(1024)) 
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
	 * @throws ParserConfigurationException If unable to create the new Document required to create the File
	 * @throws TransformerConfigurationException If unable to create a new Transformer
	 * @throws TransformerException If unable to transform the Document into a File
	 */
	public void createFile(Road[] roads, Road[] route, Region region, StatusCode statusCode, String filename) throws ParserConfigurationException,
														TransformerConfigurationException,
														TransformerException{
		
		Document document = createDocument(roads, route, region, statusCode);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    DOMSource source = new DOMSource(document);
	    
	    StreamResult result = new StreamResult(new File(filename));
	    
	  	transformer.transform(source, result);
	}


	/**
	 * Will create a xml-string with a svg-element containing lines
	 * 
	 * @param roads All the roads to put into the xml-string
	 * @return String with the xml containing the svg-element
	 * @throws ParserConfigurationException If unable to create the new Document required to create the XML String
	 * @throws TransformerConfigurationException If unable to create a new Transformer
	 * @throws TransformerException If unable to transform the Document into a XML String
	 */
	private Document createDocument(Road[] roads, Road[] route, Region region, StatusCode statusCode) throws ParserConfigurationException{
		
		//Create the Document
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document document = docBuilder.newDocument();
		
		//Create the root Element
		Element rootElement = document.createElement(ROOT_ELEMENT_NAME);
		document.appendChild(rootElement);
		
		//The map Element with roads
		Element mapElement = document.createElement(MAP_ELEMENT_NAME);
		Element svgMapElement = createSvgElement(document);
		rootElement.appendChild(mapElement);
		mapElement.appendChild(svgMapElement);
		addRoadsToElement(roads, svgMapElement);
		
		//The route Element
		if(!(route==null || route.length == 0)){
			Element routeElement = document.createElement(ROUTE_ELEMENT_NAME);
			Element svgRouteElement = createSvgElement(document);
			rootElement.appendChild(routeElement);
			routeElement.appendChild(svgRouteElement);
			addRoadsToElement(route, svgRouteElement);
		}
		
		
		//Add the StatusCode Element to the RootElement
		Element statusCodeElement = document.createElement(STATUSCODE_ELEMENT_NAME);
		statusCodeElement.setAttribute("code", statusCode.getCodeNumber() +"");
		rootElement.appendChild(statusCodeElement);
		
		//Add the ViewPortElement to the RootElement
		Element viewPortElement = document.createElement(VIEWPORT_ELEMENT_NAME);
		addViewPortData(region, viewPortElement);
		rootElement.appendChild(viewPortElement);
		
	    return document;
	}
	

	private Element createSvgElement(Document document){
		Element svgElement = document.createElement(SVG_ELEMENT_NAME);
		svgElement.setAttribute("xmlns", "http://www.w3.org/2000/svg");
		svgElement.setAttribute("version", "1.1");
		svgElement.setAttribute("width", "450000");
		svgElement.setAttribute("height", "350000");
		
		return svgElement;
	}
	
	
	private void addRoadsToElement(Road[] roads, Element element){
		for(Road r : roads){
			Color color;
			//TODO For debugging
			if(r == null){
				System.out.println("A road was null!!");
				continue;
			}
			
			Element line = element.getOwnerDocument().createElement("line");
			color = r.getColor();
			line.setAttribute("x1", r.getX1() + ""); 
			line.setAttribute("y1", r.getY1() + ""); 
			line.setAttribute("x2", r.getX2() + ""); 
			line.setAttribute("y2", r.getY2() + ""); 
			line.setAttribute("vector-effect", "non-scaling-stroke");
			line.setAttribute("stroke-width", RoadStatus.getRoadWidth(r.getType())+"");
			line.setAttribute("style", "stroke:RGB(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "); ");
			element.appendChild(line);
		}
		
	}
	
	private void addViewPortData(Region region, Element viewPortElement){
		viewPortElement.setAttribute("x1",region.getLeftPoint()[0] +"");
		viewPortElement.setAttribute("y1",region.getLeftPoint()[1] +"");
		viewPortElement.setAttribute("x2",region.getRightPoint()[0] +"");
		viewPortElement.setAttribute("y2",region.getRightPoint()[1] +"");
	}
}
