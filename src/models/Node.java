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
}
