import java.awt.Color;
import java.awt.Point;

/**
 * @author Yndal
 *
 */
public class Road {
	

	private Double x1;
	private Double y1;
	private Double x2;
	private Double y2;
	private Integer type;
	private String name;
	private int hashCode;
	private int priority;
	
	public Road(double x1, double y1, double x2, double y2, int type, String name){
		this.x1 = new Double(x1);
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = new Double(y2);
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
		else if(type == 6 || type == 10 || type == 99 || type == 0 || type == 95 || type == 26 || type == 34 || type == 35 || type == 46)
		{
			priority = 2;
		}
		else if(type == 4 || type == 5 || type == 80 || type == 31 || type == 32 || type == 33 || type == 24 || type == 25 || type == 44 || type == 45)
		{
			priority = 3;
		}
		else
		{
			priority = 4;
		}
	}
	
	public void adjustCoords(int scale, Point point)
	{
		x1 = new Double((x1.doubleValue()-point.x)/scale);
		y1 = (y1-point.y)/scale;
		x2 = (y1-point.x)/scale;	
		y2 = new Double((y2.doubleValue()-point.y)/scale);
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
	
	public int getPriority()
	{
		return priority;
	}

	
	@Override
	public int hashCode()
	{
		return hashCode;
	}

	
}
