import java.awt.Color;
import java.util.HashMap;

/**
 * 
 */

/**
 * 
 * A lot of these methods are copied from the XML class - this class may only be used if we go for the Java GUI solution
 * 
 * @author Yndal
 *
 */
public class DataHelper {
	static DataHelper instance;
	
	private final Road[] roadsOriginal;
	
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
	
	
	public DataHelper(){
		
		
		//TODO Fill in next line
		//KDTree kdTree = KDTree.getTree.......
		roadsOriginal = cleanUpRoads(allRoads,1);
		loadRoadColors();
		loadRoadWidths();
	}
	
	public static DataHelper getInstance(){
		if(instance == null) instance = new DataHelper();
		return instance;
		
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
		int largeRoads = 5;
		int mediumRoads = 4;
		int smallRoads = 3;
		int tinyRoads = 2;
		int tunnels = 4;
		int seaWays  = 5; 
		int walkingPaths = 2;
//		int bicyclePaths = 2;
		
		int unknownRoads = 4;
				
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
	
	/**
	 * Method currently as a part of the Java GUI test...
	 * Will use the original coordinates and set new ones in order to get DK in the upper left corner
	 * @param roads
	 * @return
	 */
	public Road[] cleanUpRoads(Road[] roads, int scale){
		scaleCurrent = scale;
		Road[] roadsToReturn = new Road[roads.length];
		findMinAndMaxValue(roads);
		
		
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
		double tempX1;
		double tempY1;
		double tempX2;
		double tempY2;
		
		for(Road road : allRoads){
			tempX1 = road.x1;
			tempY1 = road.y1;
			tempX2 = road.x2;
			tempY2 = road.y2;
			
			if(minXOriginal > tempX1) minXOriginal=tempX1;
			if(maxXOriginal < tempX1) maxXOriginal=tempX1;
			if(minYOriginal > tempY1) minYOriginal=tempY1;
			if(maxYOriginal < tempY1) maxYOriginal=tempY1;
			
			if(minXOriginal > tempX2) minXOriginal=tempX2;
			if(maxXOriginal < tempX2) maxXOriginal=tempX2;
			if(minYOriginal > tempY2) minYOriginal=tempY2;
			if(maxYOriginal < tempY2) maxYOriginal=tempY2;
		}
		
		repositionXCurrent = minXOriginal/scaleCurrent;
		repositionYCurrent = minYOriginal/scaleCurrent;
		

		maxXCurrent = maxXOriginal/scaleCurrent - repositionXCurrent;
		maxYCurrent = maxYOriginal/scaleCurrent - repositionYCurrent;
		
//		System.out.println("MinX: " + minXCurrent + " (not affected by the scale!)");
//		System.out.println("MaxX: " + maxXCurrent + " (not affected by the scale!)");
//		System.out.println("MinY: " + minYCurrent + " (not affected by the scale!)");
//		System.out.println("MaxY: " + maxYCurrent + " (not affected by the scale!)");
	}




	/**
	 * @return the maxXOriginal
	 */
	public double getMaxXOriginal() {
		return maxXOriginal;
	}



	/**
	 * @return the minXOriginal
	 */
	public double getMinXOriginal() {
		return minXOriginal;
	}



	/**
	 * @return the maxYOriginal
	 */
	public double getMaxYOriginal() {
		return maxYOriginal;
	}



	/**
	 * @return the minYOriginal
	 */
	public double getMinYOriginal() {
		return minYOriginal;
	}



	/**
	 * @return the maxXCurrent
	 */
	public double getMaxXCurrent() {
		return maxXCurrent;
	}



	/**
	 * @return the minXCurrent
	 */
	public double getMinXCurrent() {
		return minXCurrent;
	}



	/**
	 * @return the maxYCurrent
	 */
	public double getMaxYCurrent() {
		return maxYCurrent;
	}



	/**
	 * @return the minYCurrent
	 */
	public double getMinYCurrent() {
		return minYCurrent;
	}



	/**
	 * @return the repositionXOriginal
	 */
	public double getRepositionXOriginal() {
		return repositionXOriginal;
	}



	/**
	 * @return the repositionYOriginal
	 */
	public double getRepositionYOriginal() {
		return repositionYOriginal;
	}



	/**
	 * @return the repositionXCurrent
	 */
	public double getRepositionXCurrent() {
		return repositionXCurrent;
	}



	/**
	 * @return the repositionYCurrent
	 */
	public double getRepositionYCurrent() {
		return repositionYCurrent;
	}



	/**
	 * @return the scaleCurrent
	 */
	public double getScaleCurrent() {
		return scaleCurrent;
	}



	/**
	 * @return the roadColor
	 */
	public Color getRoadColor(int roadType) {
		return roadColors.get(roadType);
	}



	/**
	 * @return the roadWidth
	 */
	public int getRoadWidth(int roadType) {
		return roadWidths.get(roadType);
	}
	
}
