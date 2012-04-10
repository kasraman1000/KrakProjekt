import java.awt.Color;
import java.util.HashMap;

/**
 * 
 */

/**
 * @author Yndal
 *
 */
public class DataHelper {
	//Is to find the height and width of the data set
	//And then saved for later use
	private double maxX = 0; //Is 892638.21114
	private double minX = 900000; //Is 442254.35659
	private double maxY = 0; //Is 6402050.98297
	private double minY = 6500000; //Is 6049914.43018

	private static HashMap<Integer, Color> roadColors = new HashMap<Integer, Color>();
	private static HashMap<Integer, Integer> roadWidths = new HashMap<Integer, Integer>();

	
	public DataHelper(){
//		roadsOriginal = cleanUpRoads(allRoadsUnscaled,1);
		loadRoadColors();
		loadRoadWidths();
	}

	

//	/**
//	 * Will use the original coordinates and set new ones in order to get DK in the upper left corner
//	 * May only be used on roads directly from the KDTree (or else DK will be turned upside down)
//	 * @param roads
//	 * @return
//	 */
//	public Road[] cleanAndReposition(Road[] roads){
//		Road[] roadsToReturn = new Road[roads.length];
//		for(int index=0; index<roads.length; index++){
//			roadsToReturn[index] = new Road(roads[index].getX1() - minX,
//					roads[index].getY1()*(-1) + maxY, 
//					roads[index].getX2() - minX,
//					roads[index].getY2()*(-1) + maxY,
//					roads[index].getType(), 
//					roads[index].getName());
//		}
//		System.out.println("\nAfter:");
//		findMinAndMaxValue(roadsToReturn);
//		return roadsToReturn;
//	}
	
	
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
	
	public static Color getRoadColor(int roadType){
		return roadColors.get(roadType);
	}
	
	public static int getRoadWidth(int roadType){
		return roadWidths.get(roadType);
	}


	/**
	 * Will load all the predefined road colors to a HashMap - only called in the constructor
	 */
	private void loadRoadColors(){
		Color largeRoads = Color.red;
		Color mediumRoads = Color.black; //Color mediumRoads = Color.yellow;
		Color smallRoads = Color.black;
		Color tinyRoads = Color.pink;
		Color tunnels = Color.orange;
		Color seaWays  = Color.blue;
		Color walkingPaths = Color.green;
//		Color bicyclePaths = Color.gray;
		
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
		int largeRoads = 300;
		int mediumRoads = 100;
		int smallRoads = 5;
		int tinyRoads = 1;
		int tunnels = 100;

		int seaWays  = 100; 
		int walkingPaths = 1;

//		int bicyclePaths = 1;
		
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
