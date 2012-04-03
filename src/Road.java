import java.awt.Color;

/**
 * @author Yndal
 *
 */
public class Road {
	
	public final double x1;
	public final double y1;
	public final double x2;
	public final double y2;
	public final int type;
	public final String name;
	
	
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

}
