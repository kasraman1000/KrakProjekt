package controllers;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import models.*;
import routing.*;
import views.*;

/**
 * The main controller responsible for program flow
 */
public class Controller {
	private static XML xml;
	
	public static void main(String[] args) {
		Controller.startServer();
	}
	

	static{
		double start = System.nanoTime();
		System.out.println("System startup - please wait...");
		try {
			Loader.load("kdv_node_unload.txt","kdv_unload.txt", "zip_codes.txt");
			RoadSelector.initialize(Loader.getNodesForKDTree());
			EdgeParser.build(Loader.getEdgesForTranslator());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xml = new XML();
		double end = System.nanoTime();
		System.out.println("System up running... (In " + (end-start)/1e9 + " seconds)");
	}
	
	/**
	 * Will start up the Krak Server
	 * 
	 */
	public static void startServer(){
		new JSConnector();
	}
	 

	
	/**
	 * Will fetch all the roads in the Region specified 
	 * 
	 * @param region The area to get the roads from
	 * @return XML String containing all the roads in the Region
	 */
	public static String getXmlString(Region region, double bufferPercent){
		System.out.println("Controller.getXmlString() - the first region: " + region);
		//Region newRegion = new Region(region.getLeftPoint()[0], region.getLeftPoint()[1], region.getRightPoint()[0], region.getRightPoint()[1]);
		Road[] roads = RoadSelector.search(region, bufferPercent);
		String s = "";
//		Region newRegion = new Region(Road.getOrigo()[0], Road.getOrigo()[1], Road.getTop()[0], Road.getTop()[1]);
		try {
			System.out.println("Controller.getXmlString() - " + region);
			s = xml.createString(roads, null, region, StatusCode.ALL_WORKING);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return s;
	}
	
	/**
	 * Not yet done...
	 * 
	 * @param start
	 * @param target
	 * @param isLengthWeighted
	 * @throws Exception 
	 */
	public static String getRoadAndRoute(String fromAddress, String toAddress, boolean isLengthWeighted, double bufferPercent) {
		
//		fromAddress = "Annasvej 14, 4600 Køge";
//		toAddress = "Farstrupvej 5, 4600 Køge";

		//Get fromPreface
		System.out.println("Controller.getRoadAndRoute() - fromAddress is " + fromAddress);
		System.out.println("Controller.getRoadAndRoute() - toAddress is " + toAddress);
		String[] fromAddressArray = AddressParser.parseAddress(fromAddress);
		PathPreface pathPrefaceFrom = null;
		try {
			pathPrefaceFrom = EdgeParser.findPreface(fromAddressArray);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(pathPrefaceFrom == null) System.err.println("Controller.getRoadAndRoute() - pathPrefaceFrom == null at line 109!!!!");
		
		
		//Get toPreface
		System.out.println("Controller.getRoadAndRoute() - toAddress is " + toAddress);
		String[] toAddressArray = AddressParser.parseAddress(toAddress);
		PathPreface pathPrefaceTo = null;
		try {
			pathPrefaceTo = EdgeParser.findPreface(toAddressArray);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(pathPrefaceTo == null) System.err.println("Controller.getRoadAndRoute() - pathPrefaceTo == null at line 122!!!!");
		
		
		//ITU
		int firstNodeId = pathPrefaceFrom.getEdge1().from(); //EdgesAndRoadsConverter.getNearestNodeId(pathPrefaceFrom);
		
		//An Edge in Skagen
		int lastNodeId = pathPrefaceTo.getEdge1().to(); //EdgesAndRoadsConverter.getNearestNodeId(pathPrefaceTo);
		
		
		//Load the graph into Dijkstra and find the path
		DijkstraSP dij = new DijkstraSP(Loader.getGraph());
		Stack<KrakEdge> routeEdges = dij.findRoute(firstNodeId,  lastNodeId, isLengthWeighted);

		//Convert from stack to []
		KrakEdge[] routeEdgesArray = EdgesAndRoadsConverter.convertRouteStackToArray(routeEdges);
		
		//Correct start and end of [] and do the house number thing 
		Road[] route = EdgesAndRoadsConverter.checkStartAndTargetOfDijkstra(routeEdgesArray, pathPrefaceFrom, pathPrefaceTo);
		
		Region newRegion = new Region(Road.getOrigo()[0], Road.getOrigo()[1], Road.getTop()[0], Road.getTop()[1]);	
		System.out.println("Controller.getRoadAndRoute() - newRegion: " + newRegion);
		
		Road[] roads = RoadSelector.search(newRegion, bufferPercent);

		String xmlString = "";
		
		try {
			System.out.println("Controller.getRoadAndRoute() - " + newRegion);
			xmlString = xml.createString(roads, route, newRegion, StatusCode.ALL_WORKING);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlString;
	}

}
