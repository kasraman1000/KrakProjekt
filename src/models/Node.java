package models;
import java.util.HashSet;

/**
 * Node, points loaded in from kdv_node_unload.txt
 * these points are connected to eachother with 
 * road objects (from kdv_unload.txt)
 */
public class Node {
	public final double[] coords;
	private HashSet<Road> roads;
	
	public Node(double[] coords) {
		this.coords = coords;
		roads = new HashSet<Road>();
	}
		
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