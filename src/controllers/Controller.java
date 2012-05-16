package controllers;

import errorHandling.*;
import models.*;
import views.*;

/**
 * The main controller responsible for the flow of the program
 * 
 * @author Group 1, B-SWU, 2012E
 */
public class Controller {
	public static void main(String[] args) {
		Controller.startServer();
	}

	static{
		double start = System.nanoTime();
		System.out.println("System startup - please wait...");
		try {
			/*
			Loader.load("zealand_node.txt","zealand_edge.txt", "zip_codes.txt");
			/*/
			Loader.load("kdv_node_unload.txt","kdv_unload.txt", "zip_codes.txt");
			//*/
			//			Loader.load("src\\kdv_node_unload.txt","src\\kdv_unload.txt", "zip_codes.txt");

			RoadSelector.initialize(Loader.getNodesForKDTree());
			routing.EdgeParser.build(Loader.getEdgesForTranslator());
		} catch (ServerStartupException e) {
			ErrorHandler.handleServerStartupException(e);
		}
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
	 * @param bufferPercent How much to add to each side as a buffer (0.7 == add 70%)
	 * @return XML String containing all the roads in the Region
	 */
	public static String getXmlString(Region region, double bufferPercent){
		Road[] roads = RoadSelector.search(region, bufferPercent);
		String s = "";
		try {
			XML xml = new XML();
			s = xml.createString(roads, null, region, StatusCode.ALL_WORKING);
		} catch (ServerRuntimeException e){
			ErrorHandler.handleServerRuntimeException(e);
		}

		return s;
	}


	/**
	 * Will create a XML String containing all the information of the map and route to be showed
	 * (plus an extra area for buffer, defined as a parameter). 
	 * Will also contain informations of what area of the map that will be showed (the viewport)
	 * 
	 * @param fromAddress The address to set as the start
	 * @param toAddress The address to set as the target
	 * @param isLengthWeighted Define to get a route weighted by length or travel time
	 * @param bufferPercent Set how large an area to add at each side (fx. 0.70 == add 70% to each side)
	 * @return String of the format XML containing the informations of the map data (roads) and the route
	 */
	public static String getRoadAndRoute(String fromAddress, String toAddress, boolean isLengthWeighted, double bufferPercent) {
		RouteFinder routeFinder = new RouteFinder(Loader.getGraph());
		XML xml = new XML();
		
		Road[] route = null;
		try{
			route = routeFinder.getRoute(fromAddress, toAddress, isLengthWeighted);
		} catch(ClientInputException e){
			try{
				return xml.createErrorString(ErrorHandler.handleClientInputException(e));
			}
			catch(ServerRuntimeException e2){
				ErrorHandler.handleServerRuntimeException(e2);
			}
		}

		Region newRegion = new Region(Road.getOrigo()[0], Road.getOrigo()[1], Road.getTop()[0], Road.getTop()[1]);	
		Road[] roads = RoadSelector.search(newRegion, bufferPercent);

		String xmlString = "";

		try {
			xmlString = xml.createString(roads, route, newRegion, StatusCode.ALL_WORKING);
		} catch (ServerRuntimeException e){
			ErrorHandler.handleServerRuntimeException(e);
		}

		return xmlString;
	}
}