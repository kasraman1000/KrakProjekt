package models;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;



public class RoadSelector {

	private static int lastZoomLevel = 5;
	private static KDTree tree = KDTree.getTree();
	
	
	/**
	 * Returns all roads in a rectangle bound by a region filtered by priority.
	 * @param region The region which binds the viewport
	 * @return All roads within the rectangle, which are relevant to display
	 */
	public static Road[] searchRange(Region region)
	{
		//If coordinates are of wrong input, correct them
		region.adjust();
		double[] p1 = region.getLeftPoint();
		double[] p2 = region.getRightPoint();
		//Choosing filter dependent on the width of the viewport
		int zoom = zoomLevel(p1, p2);
		lastZoomLevel = zoom;
		System.out.println("zoom level " + zoom);
		System.out.println("Searching region: x1: " + p1[0] + " y1: " + p1[1] + " x2: " + p2[0] + " y2: " + p2[1]);
		//Creating a HashSet to make sure that no road are contained twice.
		HashSet<Road> roads = new HashSet<Road>(1000);
		ArrayList<Node> nodes= new ArrayList<Node>();
		tree.searchRange(tree.getRoot(), nodes, 0, Road.getOrigo(), Road.getTop(), p1, p2);
		//Checking for priority and making sure that no road is added twice
		for(Node n : nodes)
		{
			for(Road r : n.getRoads())
			{
				if(filterRoad(zoom, r))
				roads.add(r);
			}
		}
		Road[] result = roads.toArray(new Road[0]);
		System.out.println("Size: " + result.length);
		return result;
	}
	
	/**
	 * Returns all roads in a rectangle bound by two points filtered by priority.
	 * @param p1 x and y coordinates for one of the points
	 * @param p2 x and y coordinates for the other point
	 * @return All roads within the rectangle, which are relevant to display
	 */
	public static Road[] searchRange(double[] p1, double[] p2)
	{
		return searchRange(new Region(p1[0], p1[1], p2[0], p2[1]));
	}
	
	/**
	 * Returns the zoom level, which determines the filtering of the roads
	 * @param p1 A point in the rectangle that bounds the viewport
	 * @param p2 Another point in the rectangle that bounds the viewport
	 * @return The lowest priority that should be displayed
	 */
	private static int zoomLevel(double[] p1, double[] p2)
	{
		if(p2[0]-p1[0] < 20000)
			return 1;
		if(p2[0]-p1[0] < 40000)
			return 2;
		if(p2[0]-p1[0] < 80000)
			return 3;
		if(p2[0]-p1[0] < 200000)
			return 4;

			return 5;
	}
	
	public static int getLastZoomLevel()
	{
		return lastZoomLevel;
	}
	
	/**
	 * Returns true or false dependent on whether the road should be displayed or not according to the priority
	 * @param zoomLevel The priority that the road should be equals or greater than to be displayed
	 * @param road	The road which should be filtered
	 * @return whether the road has high enough priority to be displayed
	 */
	private static boolean filterRoad(int zoomLevel, Road road)
	{
		if(road.getPriority() < zoomLevel)
			return false;
		else
			return true;
	}
	
}
