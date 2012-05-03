package models;

/**
 * A square region comprised of two points in 2d space
 * The two points are in opposite corners of a square shape
 * (2 x 2 doubleArrays)
 */
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
	
	/**
	 * Sets the higher coordinates to point 2,
	 * and lower coordinates to point 1, ensuring consistency
	 */
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
	
	/**
	 * Scales up the 'square' by a specified percentage,
	 * increasing the square size.
	 * @param percent The % to scale up
	 */
	public void addBuffer(double percent)
	{
		adjust();
		double xBuffer = (p1[0] - p2[0])*percent;
		double yBuffer = (p1[1] - p2[1])*percent;
		p1[0] = p1[0] - xBuffer;
		p2[0] = p2[0] + xBuffer;
		p1[1] = p1[1] - yBuffer;
		p2[1] = p2[1] + yBuffer;
		
		
	}

	/**
	 * @return the lower point of the two
	 */
	public double[] getLeftPoint()
	{
		return p1;
	}
	
	/**
	 * @return the higher point of the two
	 */
	public double[] getRightPoint()
	{
		return p2;
	}
}
