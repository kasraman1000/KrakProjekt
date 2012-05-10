package controllers;

import errorHandling.*;
import models.*;
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
			//*
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
		Road[] roads = RoadSelector.search(region, bufferPercent);
		String s = "";
		try {
			s = xml.createString(roads, null, region, StatusCode.ALL_WORKING);
		} catch (ServerRuntimeException e){
			ErrorHandler.handleServerRuntimeException(e);
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
		double startTime = System.nanoTime();

		RouteFinder routeFinder = new RouteFinder(Loader.getGraph());
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

		double endTime = System.nanoTime();
		System.out.println("Controller.getRoadAndRoute() - Time taken to get route and roads: " + (endTime-startTime)/1e9 + " seconds");

		return xmlString;
	}

}
