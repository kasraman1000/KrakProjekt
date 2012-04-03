
public class Region {
	private double[] p1;
	private double[] p2;
	
	public Region(double x1,double y1,double x2,double y2)
	{
		x1 = p1[0];
		y1 = p1[1];
		x2 = p2[0];
		y2 = p2[1];
	}

	public double[] getLeftPoint()
	{
		return p1;
	}
	
	public double[] getRightPoint()
	{
		return p2;
	}
}
