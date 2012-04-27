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
	 * @throws ParserConfigurationException If unable to create the new Document required to create the XML String
	 * @throws TransformerConfigurationException If unable to create a new Transformer
	 * @throws TransformerException If unable to transform the Document into a XML String
	 */
	public String createString(Road[] roads, Road[] route, String errorCode, Region region) throws ParserConfigurationException, 
													TransformerConfigurationException,
													TransformerException{
		//Only for debugging
//		createFile(roads, "C:\\Users\\Yndal\\Desktop\\xmlTest.xml");
		
		Document document = createNewDocument();
		
		//The route should be the last to be added
		addRoadsToDocument(roads, document);
		if(route!=null) addRouteToDocument(route, document);
		
		addStatusCode(errorCode, document);
		addViewPortData(region, document);
	
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
	public void createFile(Road[] roads, Road[] route, String errorCode, Region region, String filename) throws ParserConfigurationException,
														TransformerConfigurationException,
														TransformerException{
		Document document = createNewDocument();
		
		addRoadsToDocument(roads, document);
		if(route!=null) addRouteToDocument(route, document);
		addStatusCode(errorCode, document);
		addViewPortData(region, document);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    DOMSource source = new DOMSource(document);
	    
	    StreamResult result = new StreamResult(new File(filename));
	    
      	transformer.transform(source, result);
	}		
	
	private Document createNewDocument() throws ParserConfigurationException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		return docBuilder.newDocument();
		
	}
	
	
	
	/**
	 * Will turn all roads in the Road[] supplied into a Document
	 * 
	 * @param roads The roads to be added
	 * @return The Document containing the roads
	 * @throws ParserConfigurationException If unable to create a new Document
	 */
	private Document addRoadsToDocument(Road[] roads, Document document){
		
		//The outer svg element 
		Element svgElement = document.createElement("svg");
		svgElement.setAttribute("xmlns", "http://www.w3.org/2000/svg");
		svgElement.setAttribute("version", "1.1");
		document.appendChild(svgElement);
		Element roadElement = document.createElement("roads");
		svgElement.appendChild(roadElement);
		
		Color color;
		for(Road road : roads){
			
			//TODO For debugging
			if(road == null){
				System.out.println("A road was null!!");
				continue;
			}
			
			
			Element line = document.createElement("line");
			
			color = road.getColor();
			line.setAttribute("x1", road.getX1() + ""); 
			line.setAttribute("y1", road.getY1() + ""); 
			line.setAttribute("x2", road.getX2() + ""); 
			line.setAttribute("y2", road.getY2() + ""); 
			line.setAttribute("style", "stroke:RGB(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "); " + 
										"stroke-width:" + RoadStatus.getRoadWidth(road.getType()));
			roadElement.appendChild(line);
		}
				
	    return document;
	}
	
	private void addRouteToDocument(Road[] route, Document document){
		Element routeElement = document.createElement("route");
		
		Color color;
		for(Road road : route){
			
			//TODO For debugging
			if(road == null){
				System.out.println("A road in the route was null!!");
				continue;
			}
			
			
			Element line = document.createElement("line");
			
			color = road.getColor();
			line.setAttribute("x1", road.getX1() + ""); 
			line.setAttribute("y1", road.getY1() + ""); 
			line.setAttribute("x2", road.getX2() + ""); 
			line.setAttribute("y2", road.getY2() + ""); 
			line.setAttribute("style", "stroke:RGB(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "); " + 
										"stroke-width:" + RoadStatus.getRoadWidth(road.getType()));
			routeElement.appendChild(line);
		}
		
		
	}
	
	private void addStatusCode(String errorCodeDescription, Document document){
		Element errorCodeElement = document.createElement("ErrorCode");
		
		errorCodeElement.setAttribute("description", errorCodeDescription);
		
		document.appendChild(errorCodeElement);
	}
	
	private void addViewPortData(Region region, Document document){
		Element viewPortElement = document.createElement("viewPort");
		
		viewPortElement.setAttribute("x1",region.getLeftPoint()[0] +"");
		viewPortElement.setAttribute("y1",region.getLeftPoint()[1] +"");
		viewPortElement.setAttribute("x2",region.getRightPoint()[0] +"");
		viewPortElement.setAttribute("y2",region.getRightPoint()[1] +"");
		
		document.appendChild(viewPortElement);
		
	
	}
}