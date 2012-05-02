package models;
public class Region {
	private double[] p1 = new double[2];
	private double[] p2 = new double[2];
	
	public Region(double x1,double y1,double x2,double y2)
	{
		p1[0] = x1;
		p1[1] = y1;
		p2[0] = x2;
		p2[1] = y2;
	}
	
	public void adjust()
	{
		if(p1[0] > p2[0])
		{
			double swap = p1[0];
			p1[0] = p2[0];
			p2[0] = swap;
		}
		if(p1[1] > p2[1])
		{
			double swap = p1[1];
			p1[1] = p2[1];
			p2[1] = swap;
		}
	}
	
	public void addBuffer(double percent)
	{
		adjust();
		double xBuffer = (p2[0] - p1[0])*percent;
		double yBuffer = (p2[1] - p1[1])*percent;
		p1[0] = p1[0] - xBuffer;
		p2[0] = p2[0] + xBuffer;
		p1[1] = p1[1] - yBuffer;
		p2[1] = p2[1] + yBuffer;
		
		
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
