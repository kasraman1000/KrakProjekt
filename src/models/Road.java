package models;

import java.awt.Color;
import java.util.HashMap;

/**
 * The Road class, connecting nodes together
 */
public class Road {
	
	
	private Double x1; //Road start
	private Double y1; //Road start
	private Double x2; //Road end
	private Double y2; //Road end
	private Integer type;
	private String name;
	private int hashCode;
	private int priority;
	private static final HashMap<Integer, Color> roadColors = new HashMap<Integer, Color>();
	private static final HashMap<Integer, Integer> roadWidths = new HashMap<Integer, Integer>();
	private static double[] top;
	private static double[] origo;
	private static final int routeType;
	
	static{
		loadRoadColors();
		loadRoadWidths();
		routeType = 50;
	}
	
	public Road(double x1, double y1, double x2, double y2, int type, String name){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.type = new Integer(type);
		this.name = name;
		hashCode = this.x1.hashCode()*(this.y2.hashCode()*31)+this.type.hashCode();
		createHashCode();
		setPriority();
	}
	
	
	public void setPriority()
	{
		if(type == 11 || type == 8 || type == 48 || type == 28)
		{
			priority = 1;
		}
		else if(type == 6 || type == 10  || type == 99 || type == 0 || type == 95 || type == 26 || type == 34 || type == 35 || type == 46)
		{
			priority = 2;
		}
		else if(type == 5)
		{
			priority = 3;
		}
		else if(type == 4   || type == 31 || type == 80 || type == 32 || type == 33 || type == 24 || type == 25 || type == 44 || type == 45)
		{
			priority = 4;
		}
		else
		{
			priority = 5;
		}
	}
	
	@Override
	public String toString() {
		return "Road [x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2
				+ ", type=" + type + ", name=" + name + "]";
	}

	/**
	 * @return the x1
	 */
	public double getX1() {
		return x1.doubleValue();
	}


	/**
	 * @return the y1
	 */
	public double getY1() {
		return y1.doubleValue();
	}


	/**
	 * @return the x2
	 */
	public double getX2() {
		return x2.doubleValue();
	}


	/**
	 * @return the y2
	 */
	public double getY2() {
		return y2.doubleValue();
	}


	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	public static double[] getTop()
	{
		return top;
	}
	
	public static void setTop(double[] top)
	{
		Road.top = top;
	}
	
	public static double[] getOrigo()
	{
		return origo;
	}
	
	public static void setOrigo(double[] origo)
	{
		Road.origo = origo;
	}
	
	public int getPriority()
	{
		return priority;
	}
	
	public Color getColor(){
		return roadColors.get(type);
	}
	
	public double getWidth(){
		return roadWidths.get(type);
	}
	
	public static int getRouteType(){
		return routeType;
	}
	
	@Override
	public int hashCode()
	{
		return hashCode;
	}


	public int createHashCode() {
		final int prime = 31;
		hashCode = 1;
		hashCode = prime * hashCode + hashCode;
		hashCode = prime * hashCode + ((name == null) ? 0 : name.hashCode());
		hashCode = prime * hashCode + ((type == null) ? 0 : type.hashCode());
		hashCode = prime * hashCode + ((x1 == null) ? 0 : x1.hashCode());
		hashCode = prime * hashCode + ((x2 == null) ? 0 : x2.hashCode());
		hashCode = prime * hashCode + ((y1 == null) ? 0 : y1.hashCode());
		hashCode = prime * hashCode + ((y2 == null) ? 0 : y2.hashCode());
		return hashCode;
	}
	
	


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return false;
	}
	
	/**
	 * Will load all the predefined road widths to a HashMap - only called in the "constructor"
	 */
	private static void loadRoadWidths(){
		/*
		//old scaling values
		int largeRoads = 400;
		int mediumRoads = 250;
		int smallRoads = 130;
		int tinyRoads = 130;
		int tunnels = 130;
		int seaWays  = 130; 
		int walkingPaths = 130;
		int routes = 1400;
		int unknownRoads = 130;
		*/
		int largeRoads = 4;
		int mediumRoads = 3;
		int smallRoads = 2;
		int tinyRoads = 2;
		int tunnels = 2;
		int seaWays  = 2; 
		int walkingPaths = 2;
		int routes = 5;
		int unknownRoads = 2;
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
		roadWidths.put(50, routes); //Our own type for the searched route
		roadWidths.put(80, seaWays); //"Faergeforbindelser"
		roadWidths.put(99, unknownRoads); //"StednavneEksaktBeliggendeUkendt"
	}
	
	/**
	 * Will load all the predefined road colors to a HashMap - only called in the "constructor"
	 */
	private static void loadRoadColors(){
		Color largeRoads = Color.red;
		Color mediumRoads = new Color(0.9607f, 0.7215f, 0.0f); //Color mediumRoads = Color.yellow;
		Color smallRoads = Color.black;
		Color tinyRoads = Color.pink;
		Color tunnels = Color.orange;
		Color seaWays  = Color.blue;
		Color walkingPaths = Color.green;
		Color routes = Color.yellow;
		
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
		roadColors.put(50, routes); //Our own type for the searched route
		roadColors.put(80, seaWays); //"Faergeforbindelser"
		roadColors.put(99, unknownRoads); //"StednavneEksaktBeliggendeUkendt"
	}
}
