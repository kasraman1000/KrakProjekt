package controllers;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import models.*;
import routing.*;
import views.*;

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
	
	
	public static void main(String[] args) {
		Controller.startServer();
	}
	

	static{
		double start = System.nanoTime();
		System.out.println("System startup - please wait...");
		kdTree = KDTree.getTree();
		try {
			Loader.load("TestNodes50000.txt","TestEdges50000.txt");
			kdTree.initialize(Loader.getNodesForKDTree());
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
	public static String getXmlString(Region region){//, double bufferPercent){
		String s = getRoadAndRoute("", "", false, 0.7);
//		Road[] roads = RoadSelector.searchRange(region);//, bufferPercent);
//		String s = "";
//		RoadStatus.setScale(RoadSelector.getLastZoomLevel());
//		try {
//			s = xml.createString(roads, null, region, StatusCode.ALL_WORKING);
//		} catch (TransformerConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TransformerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//	
		return s;
	}
	
	/**
	 * Not yet done...
	 * 
	 * @param start
	 * @param target
	 * @param isLengthWeighted
	 */
	public static String getRoadAndRoute(String fromAdress, String toAdress, boolean isLengthWeighted, double bufferPercent){
		
		
		//ITU
		PathPreface pathPrefaceFrom = new PathPreface(new KrakEdge(441762-1, 442122-1, "Roed Langgaards Vej", 199.345, 1.375, new double[]{0,0}, new double[]{1000,1000}, 2300, 2300, 0, 20, 1, 21), 
													new KrakEdge(442122-1, 441762-1, "Roed Langgaards Vej", 199.345,  1.375, new double[]{1000,1000}, new double[]{0,0}, 2300, 2300, 0, 20, 1, 21), 
														2);
		//An Edge in Skagen
		PathPreface pathPrefaceTo = new PathPreface(new KrakEdge(21194-1, 21199-1, "Kratvej", 8.73, 0.12, new double[]{5000,5000}, new double[]{10000,10000}, 9900, 9900, 0, 20, 1, 21), 
													new KrakEdge(21199-1, 21194-1, "Kratvej", 8.73, 0.12, new double[]{10000,10000}, new double[]{5000,5000}, 9900, 9900, 0, 20, 1, 21), 
													2);
		
		
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
		
		Region region = new Region(Road.getOrigo()[0], Road.getOrigo()[1], Road.getTop()[0], Road.getTop()[1]);		
		Road[] roads = RoadSelector.searchRange(region);//, bufferPercent);

		String xmlString = "";
		
		try {
			xmlString = xml.createString(roads, route, region, StatusCode.ALL_WORKING);
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
