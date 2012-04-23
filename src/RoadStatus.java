import java.awt.Color;
import java.util.HashMap;

/**
 * 
 */

/**
 * @author Yndal
 *
 */
public class RoadStatus {
	private static HashMap<Integer, Color> roadColors = new HashMap<Integer, Color>();
	private static HashMap<Integer, Integer> roadWidths = new HashMap<Integer, Integer>();
	private static double scale;
	
	
	private RoadStatus(){
		loadRoadColors();
		loadRoadWidths();
		scale = 1;
	}
	
	/**
	 * Depending on the zoomlevel, the width of the roads will be chanced.
	 * It is not necessary to set the zoomlevel, but is most preferable!
	 * 
	 * @param zoomLevel An int from 1-5 where 5 is closest to the object
	 */
	public static void setZoomlevel(int zoomLevel)
	{
		if      (zoomLevel <= 1) {scale = 0.05;}
		else if (zoomLevel == 2) {scale = 0.1;}
		else if (zoomLevel == 3) {scale = 0.2;}
		else if (zoomLevel == 4) {scale = 0.5;}
		else 					 {scale = 1;}
	}
	
	public static void setScale(double scale)
	{
		RoadStatus.scale = scale;
	}

	
	
	
	/**
	 * Will load all the predefined road colors to a HashMap - only called in the constructor
	 */
	private static void loadRoadColors(){
		Color largeRoads = Color.red;
		Color mediumRoads = new Color(0.9607f, 0.7215f, 0.0f); //Color mediumRoads = Color.yellow;
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
	private static void loadRoadWidths(){
		int largeRoads = 800;
		int mediumRoads = 250;
		int smallRoads = 130;
		int tinyRoads = 130;
		int tunnels = 130;

		int seaWays  = 130; 
		int walkingPaths = 130;

//		int bicyclePaths = 130;
		
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
	
	public static Color getRoadColor(int roadType){
		return roadColors.get(roadType);
	}
	
	/**
	 * Get the width of the road depending on the type of the road and what zoomlevel there has been set.
	 * The zoomlevel can be set in setZoomlevel();
	 * @param roadType The predefined types by Krak
	 * @return A fitting width of the road to the current zoomlevel
	 */
	public static double getRoadWidth(int roadType){
		return roadWidths.get(roadType)*scale;
	}
}
