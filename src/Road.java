/**
 * @author Yndal
 *
 */
public class Road {
	
	public final Double x1;
	public final double y1;
	public final double x2;
	public final double y2;
	public final Integer type;
	public final String name;
	
	
	public Road(double x1, double y1, double x2, double y2, int type, String name){
		this.x1 = new Double(x1);
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.type = new Integer(type);
		this.name = name;
	}


	@Override
	public String toString() {
		return "Road [x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2
				+ ", type=" + type + ", name=" + name + "]";
	}
	
	@Override
	public int hashCode()
	{
		return type.hashCode()+x1.hashCode();
	}
	
}
