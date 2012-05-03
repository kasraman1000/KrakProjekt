package models;

/**
 * A square region comprised of two points in 2d space
 * The two points are in opposite corners of a square shape
 * (2 x 2 doubleArrays)
 */
public class Region {
	private final double ratio;
	private double[] p1 = new double[2];
	private double[] p2 = new double[2];
	
	public Region(double x1,double y1,double x2,double y2)
	{
		ratio = 16/9; //To fit a screen
		p1[0] = x1;
		p1[1] = y1;
		p2[0] = x2;
		p2[1] = y2;
		adjust();
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
		
		//Make the right ratio
		double height = p2[1] - p1[1];
		double width = p2[0] - p1[0];
		
		if(((width/16)*9) < height){
			width = (height/9)*16;
			double centerWidth = p1[0] + ((p2[0] - p1[0])/2);
			p1[0] = centerWidth - (width/2);
			p2[0] = centerWidth + (width/2);
		} else if(((height/9)*16) < width){
			height = (width/16)*9;
			double centerHeight = p1[1] + ((p2[1] - p1[1])/2);
			p1[1] = centerHeight - (height/2);
			p2[1] = centerHeight + (height/2);
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
		double xBuffer = (p2[0] - p1[0])*percent;
		double yBuffer = (p2[1] - p1[1])*percent;
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
	
	@Override
	public String toString(){
		return "Region - x1:" + p1[0] + ", y1:" + p1[1] + ", x2:" + p2[0] + ", y2:" + p2[1];
	}
}
