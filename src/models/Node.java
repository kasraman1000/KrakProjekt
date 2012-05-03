package models;
import java.util.HashSet;

public class Node {
	private double[] coords;
	private HashSet<Road> roads;
	
	public Node(double[] coords) {
		this.coords = coords;
		roads = new HashSet<Road>();
	}
		
	public void addRoad(Road r) {
		roads.add(r);
	}
	
	public HashSet<Road> getRoads() {
		return roads;
	}
	
	public double getCoord(int index)
	{
		return coords[index];
	}
	
	public double[] getCoords()
	{
		return coords;
	}
	
	@Override
	public boolean equals(Object o)
	{
		/*
		if(o.getClass() == Node.class)
		{
			Node compareNode = (Node) o;
			if (compareNode.getCoord(0) == coords[0] && compareNode.getCoord(1) == coords[1])
			{
				return true;
			}
		}

			return false;
			 */
		
		return o == this;
	}
	
	@Override
	public String toString()
	{
		return "x=" + coords[0] + " y=" + coords[1];
	}
}
