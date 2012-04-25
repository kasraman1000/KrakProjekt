package controllers;

import java.io.IOException;
import java.util.Iterator;

import models.*;
import views.*;
import routing.*;

/**
 * 
 */

/**
 * @author Yndal
 *
 */
public class Controller {
	private static XML xml;
	private static KDTree kdTree;
	private static JSConnector jsConnector;
	
	
	public static void main(String[] args) {
		Controller controller = new Controller();
		
//		HashMap<Integer, HashMap<String,>>
	}
	
	/**
	 * Will start up the Krak Server
	 */
	public Controller(){
		double start = System.nanoTime();
		System.out.println("System startup - please wait...");
		kdTree = KDTree.getTree();
		try {
			Loader.load("kdv_node_unload.txt","kdv_unload.txt");
//			Loader.load("TestNodes.txt", "TestEdges.txt");
			kdTree.initialize(Loader.getNodesForKDTree());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xml = new XML();
		double end = System.nanoTime();
		System.out.println("System up running... (In " + (end-start)/1000000000 + " seconds)");
		jsConnector = new JSConnector(this);
	}
	 

	
	/**
	 * Will fetch all the roads in the Region specified 
	 * 
	 * @param region The area to get the roads from
	 * @return XML String containing all the roads in the Region
	 */
	public static String getXmlString(Region region){
		Road[] roads = RoadSelector.searchRange(region);
		String s = "";
		RoadStatus.setScale(RoadSelector.getLastZoomLevel());
		try {
//			xml.createFile(roads, "C:\\Users\\Mark\\Desktop\\TestingOfXml.xml");
			s = xml.createString(roads);
		} catch (Exception e) {
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
	 */
	public static String getRoadAndRoute(String fromAdress, String toAdress, boolean isLengthWeighted){
		//Parse address and return node-id's as "int start" and "int target" (OBS: Add the OTHER id in the Edge to the route: Because of the housenumber-calculations
		DijkstraSP dij = new DijkstraSP(Loader.getGraph());
		Iterator<DirectedEdge> routeEdges = dij.findRoute(start, target, isLengthWeighted);
	
		Road[] routeAndRoads = EdgesToRoadsConverter.convertEdgesToRoads(routeEdges);
		
		return xml.createString(routeAndRoads);
	}

}
