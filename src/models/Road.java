package models;

import java.awt.Color;

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
	private static double[] top;
	private static double[] origo;
	
	public Road(double x1, double y1, double x2, double y2, int type, String name){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.type = new Integer(type);
		this.name = name;
		hashCode = this.x1.hashCode()*(this.y2.hashCode()*31)+this.type.hashCode();
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
		return RoadStatus.getRoadColor(type);
	}
	
	public double getWidth(){
		return RoadStatus.getRoadWidth(type);
	}
	
	@Override
	public int hashCode()
	{
		return hashCode;
	}
}
