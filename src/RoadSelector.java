import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;


public class RoadSelector {


	private static final int MAX_ROADS = 20000;
	private static KDTree kdTree = KDTree.getTree();

	/**
	 * Returns all roads in a rectangle bound by a region filtered by priority.
	 * @param region The region which binds the viewport
	 * @return All roads within the rectangle, which are relevant to display
	 */

	public static Road[] search(Region region) 
	{
		double time;
		double[] p1 = region.getLeftPoint();
		double[] p2 = region.getRightPoint();
		//Choosing filter dependent on the width of the viewport
		int zoom = zoomLevel(p1, p2);
		RoadStatus.setScale(zoom);

		System.out.println("zoom level " + zoom);
		System.out.println("Searching region: x1: " + p1[0] + " y1: " + p1[1] + " x2: " + p2[0] + " y2: " + p2[1]);
		
		time = System.nanoTime();
		ArrayList<Node> nodes = kdTree.searchRange(region);
		System.out.println("Time to KDTree search: " + (System.nanoTime()-time)/1000000000);

		HashSet<Road> roads = new HashSet<Road>(1000000);
		time = System.nanoTime();
		for (Node n : nodes) {
			for(Road r : n.getRoads()) {
				roads.add(r);
			}
		}
		System.out.println("Time to HashSet: " + (System.nanoTime()-time)/1000000000);
		
		time = System.nanoTime();
		ArrayList<Road> result = filter(roads, MAX_ROADS, zoom);
		System.out.println("Time to filter by zoom: " + (System.nanoTime()-time)/1000000000);

		System.out.println("Number of roads: " + result.size());
		return result.toArray(new Road[0]);
	}
	
	/**
	 * Returns all roads in a rectangle bound by two points filtered by priority.
	 * @param p1 x and y coordinates for one of the points
	 * @param p2 x and y coordinates for the other point
	 * @return All roads within the rectangle, which are relevant to display
	 */
	private static ArrayList<Road> filter(Collection<Road> roads, int max, int zoom) 
	{
		ArrayList<Road> result = new ArrayList<Road>();
		if(zoom < 5)
		{
			int count = 0;
			for(Road r : roads) {
				if(!(r.getPriority() < zoom)) {
					count++;
				}

			}
			System.out.println("int count = " + count);
			if(count > max) {
				System.out.println("Increasing zoom from " + zoom + " to " + (zoom+1));
				zoom++;
			}
		}
		
		for(Road r : roads) {
			if(!(r.getPriority() < zoom)) {
				result.add(r);
			}
		}
		
		return result;
		
	}
	private static ArrayList<Road> filter(Collection<Road> roads, int max)
	{
		ArrayList<Road> result = new ArrayList<Road>();
		int nextLevelRoads = 0;
		int level = 5;

		do {
			nextLevelRoads = 0;
			for (Road r : roads) {
				if (r.getPriority() == level) 
					result.add(r);
				else if (r.getPriority() == level-1) 
					nextLevelRoads++;
			}
			level--;

		//	System.out.println("Roads totalat current level: " + result.size());
		//	System.out.println("Roads at next level (" + level + "): " + nextLevelRoads);

		} while (!((result.size() + nextLevelRoads) > max) && level > 1);

		return result;
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