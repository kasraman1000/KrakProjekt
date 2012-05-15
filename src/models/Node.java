package models;
import java.util.HashSet;

/**
 * Node that represents an end of a road object
 * 
 * @author Group 1, B-SWU, 2012E
 * 
 */
public class Node {
	public final double[] coords;
	private HashSet<Road> roads;
	
	public Node(double[] coords) {
		this.coords = coords;
		roads = new HashSet<Road>();
	}
		
	/**
	 * Add the Road r to this Node
	 * @param r Road to be added
	 */
	public void addRoad(Road r) {
		roads.add(r);
	}
	
	/**
	 * Returns all roads connected to this node
	 * @return a HashSet of all associated roads
	 */
	public HashSet<Road> getRoads() {
		return roads;
	}
	
	/**
	 * Returns a single of the specified coordinates of the node
	 * @param index Which coordinate to return
	 * @return the specified coordinate
	 */
	public double getCoord(int index)
	{
		return coords[index];
	}

	/**
	 * Returns the whole coordinate array 
	 * (all coordinated of the node)
	 * @return a double-array of coordinates
	 */
	public double[] getCoords()
	{
		return coords;
	}
	
	@Override
	public String toString()
	{
		return "x=" + coords[0] + " y=" + coords[1];
	}
}