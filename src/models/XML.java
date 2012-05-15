package models;

import java.awt.Color;
import java.io.File;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import errorHandling.*;

/**
 * This class is for the making of and XML String or file
 *
 * @author Group 1, B-SWU, 2012E
 * 
 */
public class XML{
	private final String ROOT_ELEMENT_NAME;
	private final String SVG_ELEMENT_NAME;
	private final String MAP_ELEMENT_NAME;
	private final String ROUTE_ELEMENT_NAME;
	private final String VIEWPORT_ELEMENT_NAME;
	private final String STATUSCODE_ELEMENT_NAME;
	
	/**
	 * The Constructor - sets what the groups in the XML will be named
	 */
	public XML(){
		ROOT_ELEMENT_NAME = "root";
		SVG_ELEMENT_NAME = "svg";
		MAP_ELEMENT_NAME = "map";
		ROUTE_ELEMENT_NAME =  "route";
		VIEWPORT_ELEMENT_NAME =  "viewPort";
		STATUSCODE_ELEMENT_NAME = "statusCode";
	}
	
	
	/**
	 * This method is only for testing (makes 5 roads)
	 * 
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
	 * 
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
	 * Will create a XML String
	 * 
	 * @param roads All the roads to put into the XML-string
	 * @return String with the XML containing the svg-element
	 *  
	 * @param roads All the roads to be added in the XML
	 * @param route The route the be added in the XML
	 * @param region What region the viewer shall show
	 * @param statusCode Tells if anything went wrong
	 * @return The String containing the XML
	 * @throws ServerRuntimeException If the creating of the XML went wrong
	 */
	public String createString(Road[] roads, Road[] route, Region region, StatusCode statusCode) 
																		throws ServerRuntimeException{
		Document document = createDocument(roads, route, region, statusCode);
		
		String xmlString = "";
		
		try{
			//Transform the Document into a XML String and return the String
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
		    Transformer transformer = transformerFactory.newTransformer();
		    DOMSource source = new DOMSource(document);
		    
		    // May want to add a larger buffer by telling the constructor (fx. StringWriter(1024)) 
		    StringWriter stringWriter = new StringWriter();
		    StreamResult result = new StreamResult(stringWriter);
	      	transformer.transform(source, result);
		    xmlString = stringWriter.toString();
		} catch(TransformerException e){
			throw new XMLTransformerException(e);
		}
	    
	    return xmlString;
	}
	
	/**
	 * Will create a file containing the XML (useful for debugging)
	 * 
	 * @param roads All the roads to be added in the XML
	 * @param route The route the be added in the XML
	 * @param region What region the viewer shall show
	 * @param statusCode Tells if anything went wrong
	 * @param filename The name of the file to be created (may also specify the path)
	 * @throws ServerRuntimeException If the creating of the XML went wrong
	 */
	public void createFile(Road[] roads, Road[] route, Region region, StatusCode statusCode, String filename) 
																					throws ServerRuntimeException{
		Document document = createDocument(roads, route, region, statusCode);
		
		try{			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
		    Transformer transformer = transformerFactory.newTransformer();
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		    DOMSource source = new DOMSource(document);
		    
		    StreamResult result = new StreamResult(new File(filename));
		    
	      	transformer.transform(source, result);
		}catch(TransformerException e){
			throw new XMLTransformerException(e);
		}
	}

	public String createErrorString(StatusCode statusCode) throws ServerRuntimeException{
		//Create the Document
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		Document document = null;
		
		try{
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			document = docBuilder.newDocument();	
		} catch(ParserConfigurationException e){
			throw new XMLDocumentException(e);
		}
		
		//Create the root Element
		Element rootElement = document.createElement(ROOT_ELEMENT_NAME);
		document.appendChild(rootElement);

		//Add the StatusCode Element to the RootElement
		Element statusCodeElement = document.createElement(STATUSCODE_ELEMENT_NAME);
		statusCodeElement.setAttribute("code", statusCode.getCodeNumber() +"");
		rootElement.appendChild(statusCodeElement);
		
		String xmlString = "";
		
		try{
			//Transform the Document into a XML String and return the String
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
		    Transformer transformer = transformerFactory.newTransformer();
		    DOMSource source = new DOMSource(document);
		    
		    // May want to add a larger buffer by telling the constructor (fx. StringWriter(1024)) 
		    StringWriter stringWriter = new StringWriter();
		    StreamResult result = new StreamResult(stringWriter);
	      	transformer.transform(source, result);
		    xmlString = stringWriter.toString();
		} catch(TransformerException e){
			throw new XMLTransformerException(e);
		}
	    
	    return xmlString;
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
	
	/**
	 * Creates the document to contain the whole XML
	 * @param roads The roads to be in the SVG element
	 * @param route The route to be in the SVg element
	 * @param region The region of the map to be shown
	 * @param statusCode Sets the status to be shown for the client (if needed)
	 * @return The document - ready to be transformed
	 * @throws XMLDocumentException If anything went wrong
	 */
	private Document createDocument(Road[] roads, Road[] route, Region region, StatusCode statusCode) throws XMLDocumentException{
		//Create the Document
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		Document document = null;
		
		try{
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			document = docBuilder.newDocument();	
		} catch(ParserConfigurationException e){
			throw new XMLDocumentException(e);
		}
		
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
		addViewPort(region, viewPortElement);
		rootElement.appendChild(viewPortElement);
		
	    return document;
	}
	
	/**
	 * Creates the SVG element with the correct attributes
	 * 
	 * @param document The document to create the svg element from
	 * @return The svg element
	 */
	private Element createSvgElement(Document document){
		Element svgElement = document.createElement(SVG_ELEMENT_NAME);
		svgElement.setAttribute("xmlns", "http://www.w3.org/2000/svg");
		svgElement.setAttribute("version", "1.1");
		svgElement.setAttribute("width", "450000");
		svgElement.setAttribute("height", "350000");
		
		return svgElement;
	}
	
	/**
	 * Will add the roads supplied to the element supplied
	 * 
	 * @param roads The roads to be added
	 * @param element The element the roads shall be added to
	 */
	private void addRoadsToElement(Road[] roads, Element element){
		for(Road r : roads){
			Element line = element.getOwnerDocument().createElement("line");
			Color color = r.getColor();
			line.setAttribute("x1", r.getX1() + ""); 
			line.setAttribute("y1", r.getY1() + ""); 
			line.setAttribute("x2", r.getX2() + ""); 
			line.setAttribute("y2", r.getY2() + ""); 
			line.setAttribute("vector-effect", "non-scaling-stroke");
			line.setAttribute("stroke-width", r.getWidth()+"");
			line.setAttribute("style", "stroke:RGB(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "); ");
			element.appendChild(line);
		}
	}
	
	/**
	 * Adds the viewPortElement to the document
	 * 
	 * @param region The region the client shall show
	 * @param viewPortElement The element representing the viewPort
	 */
	private void addViewPort(Region region, Element viewPortElement){
		viewPortElement.setAttribute("x1",region.getLeftPoint()[0] +"");
		viewPortElement.setAttribute("y1",region.getLeftPoint()[1] +"");
		viewPortElement.setAttribute("x2",region.getRightPoint()[0] +"");
		viewPortElement.setAttribute("y2",region.getRightPoint()[1] +"");
	}
}
