import java.awt.Color;

/**
 * @author Yndal
 *
 */
public class Road {
	
	private final double x1;
	private final double y1;
	private final double x2;
	private final double y2;
	private final int type;
	private final String name;
	
	
	public Road(double x1, double y1, double x2, double y2, int type, String name){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.type = type;
		this.name = name;
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
		return x1;
	}


	/**
	 * @return the y1
	 */
	public double getY1() {
		return y1;
	}


	/**
	 * @return the x2
	 */
	public double getX2() {
		return x2;
	}


	/**
	 * @return the y2
	 */
	public double getY2() {
		return y2;
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

	
}
