import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

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
	private static XML xml;
	private static KDTree kdTree;
	private static JSConnector jsConnector;
	
	
	public static void main(String[] args) {
		Controller controller = new Controller();
	}
	
	
	public Controller(){
		System.out.println("System startup - please wait...");
		kdTree = KDTree.getTree();
		try {
/*
			kdTree.initialize("..\\kdv_node_unload.txt",
					"..\\kdv_unload.txt");
		*/			
			kdTree.initialize("kdv_node_unload.txt","kdv_unload.txt");
			System.out.println("PATH READ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataHelper = new DataHelper(kdTree);
		xml = new XML();
		System.out.println("System up running...");
		jsConnector = new JSConnector(this);
	}
	
	
	
	public String getXmlString(Region region){
		Road[] roads = dataHelper.cleanUpRoads(kdTree.searchRange(region));
		String s = "";
		try {
			xml.createFile(roads, "C:\\Users\\Yndal\\Desktop\\TestingOfXml.xml");
			s = xml.createString(roads);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	
//	public Road[] getRoadsFromString(String xmlString, int[] roadTypesToExtract){
//		//Pre check
////TODO	if(xmlString.length() == 0) throw SomeKindOfException
//		//If roadTypes are left empty - all roads will be returned (from type 0-99)
//		if(roadTypesToExtract.length == 0){
//			final int HIGHEST_ROAD_TYPE = 99;
//			roadTypesToExtract = new int[HIGHEST_ROAD_TYPE+1];
//			for(int roadTypeIndex=0; roadTypeIndex<=HIGHEST_ROAD_TYPE; roadTypeIndex++){
//				roadTypesToExtract[roadTypeIndex] = roadTypeIndex;
//			}
//		}
//				
//		
//		Road[] foundRoads = new Road[0];
//		try {
//			foundRoads = xml.getRoadsFromString(xmlString, roadTypesToExtract);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
////TODO	if(foundRoads.length == 0){
////			doSomethingClever
////		} else {
//		return foundRoads;
//		
//	}
	
	
	public static Color getRoadColor(int roadType){
		return dataHelper.roadColors.get(roadType);
	}
	
	public static int getRoadWidth(int roadType){
		return dataHelper.roadWidths.get(roadType);
	}

	
	public static double getMaxXOriginal(){
		return KDTree.getTree().top[0];
	}
	
	public static double getMaxYOriginal(){
		return KDTree.getTree().top[1];
	}
	
	
	
	private String getErrorString(int errorCode){
		return null;
	}
	
	
	/**
	 * Inner class DataHelper for a lot of calculation 
	 */
	
	private class DataHelper {
		private KDTree kdTree;
		
		//Is to find the height and width of the data set
		//And then saved for later use
		private double maxX = 0; //Is 892638.21114
		private double minX = 900000; //Is 442254.35659
		private double maxY = 0; //Is 6402050.98297
		private double minY = 6500000; //Is 6049914.43018

		private HashMap<Integer, Color> roadColors = new HashMap<Integer, Color>();
		private HashMap<Integer, Integer> roadWidths = new HashMap<Integer, Integer>();
	
		
		private DataHelper(KDTree kdTree){
			this.kdTree = kdTree;
	//		roadsOriginal = cleanUpRoads(allRoadsUnscaled,1);
			loadRoadColors();
			loadRoadWidths();
		}

		

		/**
		 * Will use the original coordinates and set new ones in order to get DK in the upper left corner
		 * May only be used on roads directly from the KDTree (or else DK will be turned upside down)
		 * @param roads
		 * @return
		 */
		public Road[] cleanUpRoads(Road[] roads){
			System.out.println("\nBefore:");
			findMinAndMaxValue(roads);
			Road[] roadsToReturn = new Road[roads.length];
		
			for(int index=0; index<roads.length; index++){
				roadsToReturn[index] = new Road(roads[index].getX1() - minX,
						roads[index].getY1()*(-1) + maxY, 
						roads[index].getX2() - minX,
						roads[index].getY2()*(-1) + maxY,
						roads[index].getType(), 
						roads[index].getName());
			}
			System.out.println("\nAfter:");
			findMinAndMaxValue(roadsToReturn);
			return roadsToReturn;
		}
		
		
		private void findMinAndMaxValue(Road[] allRoads){
			for(Road road : allRoads){
				if(minX > road.getX1()) minX=road.getX1();
				if(maxX < road.getX1()) maxX=road.getX1();
				if(minY > road.getY1()) minY=road.getY1();
				if(maxY < road.getY1()) maxY=road.getY1();
				
				if(minX > road.getX2()) minX=road.getX2();
				if(maxX < road.getX2()) maxX=road.getX2();
				if(minY > road.getY2()) minY=road.getY2();
				if(maxY < road.getY2()) maxY=road.getY2();
			}
			
			System.out.println("MinX: " + minX);
			System.out.println("MaxX: " + maxX);
			System.out.println("MinY: " + minY);
			System.out.println("MaxY: " + maxY);
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
			int largeRoads = 600;
			int mediumRoads = 300;
			int smallRoads = 200;
			int tinyRoads = 100;
			int tunnels = 200;

			int seaWays  = 400; 
			int walkingPaths = 500;

//			int bicyclePaths = 1;
			
			int unknownRoads = 100;
					
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
