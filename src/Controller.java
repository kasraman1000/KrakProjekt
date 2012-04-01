import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

/**
 * 
 */

/**
 * @author Yndal
 *
 */
public class Controller {
	private static DataHelper dataHelper;
	private XML xml;
	
	
	public Controller(){
		
	}
	
	
	//TODO Must be changed when JS is up running!!!
	
	public void launchKrax(){
		Road[] roadsOriginal;
		Road[] roadsCurrent = null;
		int scaleStart = 500;
		System.out.println("Launching...");
		try {
			KDTree kdTree = KDTree.getTree();
			kdTree.initialize("C:\\Users\\Yndal\\Desktop\\Dropbox\\1. Śrsprojekt - gruppe 1\\krak-data\\kdv_node_unload.txt",
					"C:\\Users\\Yndal\\Desktop\\Dropbox\\1. Śrsprojekt - gruppe 1\\krak-data\\kdv_unload.txt");
			Road[] allRoads = kdTree.searchRange(kdTree.origo, kdTree.top);
			dataHelper = new DataHelper(allRoads);

			
			roadsOriginal = dataHelper.cleanUpRoads(allRoads, 1);
			roadsCurrent = dataHelper.cleanUpRoads(allRoads, scaleStart);
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		View view = new View();
		view.createFrame();
		view.drawRoads(roadsCurrent);
		
		
		
		System.out.println("Launch loaded... Enjoy!");
		//Load all data
		//Find original min and max in DataHelper (partly to storage the information)
		//Define starting scale
		//Create instance of JSConnector
	}
	
	
	
	public static void newZoomLevel(Point centerOfCurrentView, boolean zoomIn){
//		DataHelper.cleanupRoads(roadsOriginal, isZoomedIn);
		System.out.println("Zoom level changed");
	}
	
	
	public void createXmlFile(Road[] roads, String fileName){
//TODO	if(roads.length == 0) throw new SomeKindOfException;
//TODO	Maybe do a check of the fileName??		
		try {
			xml.createFile(roads, fileName);
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
	}
	
	public String createXmlString(Road[] roads){
//		if(roads.length == 0) throw new SomeKindOfException;
		String returnString = null;
		try {
			returnString = xml.createString(roads);
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
//TODO	if (returnString == null) doSomethingClever
		return returnString;
	}
	
	public Road[] getRoadsFromFile(String fileName, int[] roadTypesToExtract){
		if(roadTypesToExtract.length == 0){
			final int HIGHEST_ROAD_TYPE = 99;
			roadTypesToExtract = new int[HIGHEST_ROAD_TYPE+1];
			for(int roadTypeIndex=0; roadTypeIndex<=HIGHEST_ROAD_TYPE; roadTypeIndex++){
				roadTypesToExtract[roadTypeIndex] = roadTypeIndex;
			}
		}
		return getRoadsFromFile(fileName, roadTypesToExtract);
	}
	
	
	public Road[] getRoadsFromString(String xmlString, int[] roadTypesToExtract){
		//Pre check
//TODO	if(xmlString.length() == 0) throw SomeKindOfException
		//If roadTypes are left empty - all roads will be returned (from type 0-99)
		if(roadTypesToExtract.length == 0){
			final int HIGHEST_ROAD_TYPE = 99;
			roadTypesToExtract = new int[HIGHEST_ROAD_TYPE+1];
			for(int roadTypeIndex=0; roadTypeIndex<=HIGHEST_ROAD_TYPE; roadTypeIndex++){
				roadTypesToExtract[roadTypeIndex] = roadTypeIndex;
			}
		}
				
		
		Road[] foundRoads = new Road[0];
		try {
			foundRoads = xml.getRoadsFromString(xmlString, roadTypesToExtract);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//TODO	if(foundRoads.length == 0){
//			doSomethingClever
//		} else {
		return foundRoads;
		
	}
	
	
	
	
	
	
	public static Color getRoadColor(int roadType){
		return dataHelper.roadColors.get(roadType);
	}
	
	public static int getRoadWidth(int roadType){
		return dataHelper.roadWidths.get(roadType);
	}
	
	public static double getMaxXCurrent(){
		return dataHelper.maxXCurrent;
	}
	
	public static double getMaxYCurrent(){
		return dataHelper.maxYCurrent;
	}
	
	public static double getMaxXOriginal(){
		return KDTree.getTree().top[0];
	}
	
	public static double getMaxYOriginal(){
		return KDTree.getTree().top[1];
	}
	
	
	
	
	
	
	/**
	 * Inner class DataHelper for a lot of calculation 
	 */
	
	private class DataHelper {
		//TODO Clean up all of these fields!!
		private Road[] roadsOriginal;
		private Road[] roadsCurrent;
		
		//Is to find the height and width of the data set
		//And then saved for later use
		private double maxXOriginal = 0; //Is 892638.21114
		private double minXOriginal = 900000; //Is 442254.35659
		private double maxYOriginal = 0; //Is 6402050.98297
		private double minYOriginal = 6500000; //Is 6049914.43018
		
		private double maxXCurrent;
		private double minXCurrent;
		private double maxYCurrent;
		private double minYCurrent;
		
		private double repositionXOriginal;
		private double repositionYOriginal;
		private double repositionXCurrent;
		private double repositionYCurrent;
		
		private double scaleCurrent;

		private HashMap<Integer, Color> roadColors = new HashMap<Integer, Color>();
		private HashMap<Integer, Integer> roadWidths = new HashMap<Integer, Integer>();
	
		
		private DataHelper(Road[] allRoadsUnscaled){
			roadsOriginal = cleanUpRoads(allRoadsUnscaled,1);
			loadRoadColors();
			loadRoadWidths();
		}
		
		
		/**
		 * Will use the original coordinates and set new ones in order to get DK in the upper left corner
		 * May only be used on roads directly from the KDTree (or else DK will be turned upside down)
		 * @param roads
		 * @return
		 */
		public Road[] cleanUpRoads(Road[] roads, int scale){
			scaleCurrent = scale;
			findMinAndMaxValue(roads);
			Road[] roadsToReturn = new Road[roads.length];
		
			for(int index=0; index<roads.length; index++){
				roadsToReturn[index] = new Road((roads[index].x1/scale) - repositionXCurrent, // (X/scale) + (repositionXCurrent)
						(roads[index].y1/scale)*(-1) + maxYCurrent + repositionYCurrent, // (Y/scale)*(-1) + maxYCurrent + repositionYCurrent this will calculate the correct Y (have to be "turned around") in the correct scale
						(roads[index].x2/scale) - repositionXCurrent, // (X/scale) + (repositionXCurrent)
						(roads[index].y2/scale)*(-1) + maxYCurrent + repositionYCurrent, // (Y/scale)*(-1) + maxYCurrent + repositionYCurrent
						roads[index].type, //Type
						roads[index].name); //Name of the road
			}
			return roadsToReturn;
		}
		
		
		private void findMinAndMaxValue(Road[] allRoads){
			
//			System.out.println("ReposX: " + repositionXCurrent);
//			System.out.println("ReposY: " + repositionYCurrent);

			for(Road road : allRoads){
				if(minXOriginal > road.x1) minXOriginal=road.x1;
				if(maxXOriginal < road.x1) maxXOriginal=road.x1;
				if(minYOriginal > road.y1) minYOriginal=road.y1;
				if(maxYOriginal < road.y1) maxYOriginal=road.y1;
				
				if(minXOriginal > road.x2) minXOriginal=road.x2;
				if(maxXOriginal < road.x2) maxXOriginal=road.x2;
				if(minYOriginal > road.y2) minYOriginal=road.y2;
				if(maxYOriginal < road.y2) maxYOriginal=road.y2;
			}
			
			repositionXCurrent = minXOriginal/scaleCurrent;
			repositionYCurrent = minYOriginal/scaleCurrent;
			

			maxXCurrent = maxXOriginal/scaleCurrent - repositionXCurrent;
			maxYCurrent = maxYOriginal/scaleCurrent - repositionYCurrent;
			
//			System.out.println("MinX: " + minXCurrent);
//			System.out.println("MaxX: " + maxXCurrent);
//			System.out.println("MinY: " + minYCurrent);
//			System.out.println("MaxY: " + maxYCurrent);
//			System.out.println("ReposX: " + repositionXCurrent);
//			System.out.println("ReposY: " + repositionYCurrent);
		}


		/**
		 * Will load all the predefined road colors to a HashMap - only called in the constructor
		 */
		private void loadRoadColors(){
			Color largeRoads = Color.red;
			Color mediumRoads = Color.yellow;
			Color smallRoads = Color.black;
			Color tinyRoads = Color.pink;
			Color tunnels = Color.orange;
			Color seaWays  = Color.blue;
			Color walkingPaths = Color.green;
//			Color bicyclePaths = Color.gray;
			
			Color unknownRoads = Color.cyan;
			
			roadColors.put(0, unknownRoads); //"Unknown0"
			roadColors.put(95, unknownRoads); //"Unknown95"
			
			roadColors.put(1, largeRoads); //"Motorvej"
			roadColors.put(2, mediumRoads); //"Motortrafikvej"
			roadColors.put(3, mediumRoads); //"PrimearruteOver6m"
			roadColors.put(4, mediumRoads); //"SekundearOver6m"
			roadColors.put(5, smallRoads); //"Vej3til6m"
			roadColors.put(6, smallRoads); //"AndenVej"
			roadColors.put(8, walkingPaths); //"Sti"
			roadColors.put(10, tinyRoads); //"Markvej"
			roadColors.put(11, walkingPaths); //"Gaagader //"
			roadColors.put(21, largeRoads); //"Proj.motorvej"
			roadColors.put(22, mediumRoads); //"Proj.motortrafikvej"
			roadColors.put(23, mediumRoads); //"Proj.primearvej"
			roadColors.put(24, mediumRoads); //"Proj.sekundearvej"
			roadColors.put(25, smallRoads); //"Proj.vej3til6m"
			roadColors.put(26, smallRoads); //"Proj.vejUnder3m"
			roadColors.put(28, walkingPaths); //"Proj.sti"
			roadColors.put(31, largeRoads); //"Motorvejsafkoersel"
			roadColors.put(32, mediumRoads); //"Motortrafikvejsafkoersel"
			roadColors.put(33, mediumRoads); //"Primearvejsafkoersel"
			roadColors.put(34, mediumRoads); //"Sekundearvejsafkoersel"
			roadColors.put(35, smallRoads); //"AndenVejafkoersel"
			roadColors.put(41, tunnels); //"Motorvejstunnel"
			roadColors.put(42, tunnels); //"Motortrafikvejstunnel"
			roadColors.put(43, tunnels); //"Primaerstunnel"
			roadColors.put(44, tunnels); //"Sekundaervejstunnel"
			roadColors.put(45, tunnels); //"AndenVejtunnel"
			roadColors.put(46, tunnels); //"MindreVejtunnel"
			roadColors.put(48, tunnels); //"Stitunnel"
			roadColors.put(80, seaWays); //"Faergeforbindelser"
			roadColors.put(99, unknownRoads); //"StednavneEksaktBeliggendeUkendt"
		}


		/**
		 * Will load all the predefined road widths to a HashMap - only called in the constructor
		 */
		private void loadRoadWidths(){
			int largeRoads = 3;
			int mediumRoads = 2;
			int smallRoads = 2;
			int tinyRoads = 1;
			int tunnels = 2;
			int seaWays  = 3; 
			int walkingPaths = 1;
//			int bicyclePaths = 1;
			
			int unknownRoads = 1;
					
			roadWidths.put(0, unknownRoads); //"Unknown0"
			roadWidths.put(95, unknownRoads); //"Unknown95"
			
			roadWidths.put(1, largeRoads); //"Motorvej"
			roadWidths.put(2, mediumRoads); //"Motortrafikvej"
			roadWidths.put(3, mediumRoads); //"PrimearruteOver6m"
			roadWidths.put(4, mediumRoads); //"SekundearOver6m"
			roadWidths.put(5, smallRoads); //"Vej3til6m"
			roadWidths.put(6, smallRoads); //"AndenVej"
			roadWidths.put(8, walkingPaths); //"Sti"
			roadWidths.put(10, tinyRoads); //"Markvej"
			roadWidths.put(11, walkingPaths); //"Gaagader //"
			roadWidths.put(21, largeRoads); //"Proj.motorvej"
			roadWidths.put(22, mediumRoads); //"Proj.motortrafikvej"
			roadWidths.put(23, mediumRoads); //"Proj.primearvej"
			roadWidths.put(24, mediumRoads); //"Proj.sekundearvej"
			roadWidths.put(25, smallRoads); //"Proj.vej3til6m"
			roadWidths.put(26, smallRoads); //"Proj.vejUnder3m"
			roadWidths.put(28, walkingPaths); //"Proj.sti"
			roadWidths.put(31, largeRoads); //"Motorvejsafkoersel"
			roadWidths.put(32, mediumRoads); //"Motortrafikvejsafkoersel"
			roadWidths.put(33, mediumRoads); //"Primearvejsafkoersel"
			roadWidths.put(34, mediumRoads); //"Sekundearvejsafkoersel"
			roadWidths.put(35, smallRoads); //"AndenVejafkoersel"
			roadWidths.put(41, tunnels); //"Motorvejstunnel"
			roadWidths.put(42, tunnels); //"Motortrafikvejstunnel"
			roadWidths.put(43, tunnels); //"Primaerstunnel"
			roadWidths.put(44, tunnels); //"Sekundaervejstunnel"
			roadWidths.put(45, tunnels); //"AndenVejtunnel"
			roadWidths.put(46, tunnels); //"MindreVejtunnel"
			roadWidths.put(48, tunnels); //"Stitunnel"
			roadWidths.put(80, seaWays); //"Faergeforbindelser"
			
			roadWidths.put(99, unknownRoads); //"StednavneEksaktBeliggendeUkendt"
		}
	}
}
