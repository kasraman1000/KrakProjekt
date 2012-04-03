import java.awt.Color;

/**
 * @author Yndal
 *
 */
public class Road {
	

	private final Double x1;
	private final double y1;
	private final double x2;
	private final Double y2;
	private final Integer type;
	private final String name;
	private final int hashCode;
	
	public Road(double x1, double y1, double x2, double y2, int type, String name){
		this.x1 = new Double(x1);
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = new Double(y2);
		this.type = new Integer(type);
		this.name = name;
		hashCode = this.x1.hashCode()*(this.y2.hashCode()*31)+this.type.hashCode();
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

	
	@Override
	public int hashCode()
	{
		return hashCode;
	}

	
}
