import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * The Class responsible for determining what roads to show
 * @author Kasra
 */

public class RoadSelector {

	private static KDTree kdTree;

	public RoadSelector() {
		kdTree = KDTree.getTree();
		try {		
			kdTree.initialize("kdv_node_unload.txt","kdv_unload.txt");
			System.out.println("PATH READ");
		} catch (IOException e) {
			System.out.println("Failed to initialize kdTree");
			e.printStackTrace();
		}
	}

	/**
	 * Searches the KDTree and filters the result
	 * @param region The region to search for roads
	 * @return The roads to show
	 */
	public Road[] search(Region region) {

		//If coordinates are of wrong input, correct them
		region.adjust();
		double[] p1 = region.getLeftPoint();
		double[] p2 = region.getRightPoint();

		//Choosing filter dependent on the width of the viewport
		int zoom = zoomLevel(p1, p2);
		RoadStatus.setZoomlevel(zoom);

		System.out.println("zoom level " + zoom);
		System.out.println("Searching region: x1: " + p1[0] + " y1: " + p1[1] + " x2: " + p2[0] + " y2: " + p2[1]);


		HashSet<Road> searchResult = kdTree.searchRange(region);
		ArrayList<Road> roads = new ArrayList<Road>();

		for (Road r : searchResult) {
			if(filterRoad(zoom, r))
				roads.add(r);
		}
		
		Road[] result = roads.toArray(new Road[0]);

		System.out.println("Number of roads: " + result.length);
		return result;
	}
	
	/**
	 * Returns the zoom level, which determines the filtering of the roads
	 * @param p1 A point in the rectangle that bounds the viewport
	 * @param p2 Another point in the rectangle that bounds the viewport
	 * @return The lowest priority that should be displayed
	 */
	private int zoomLevel(double[] p1, double[] p2)
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
	private boolean filterRoad(int zoomLevel, Road road)
	{
		if(road.getPriority() < zoomLevel)
			return false;
		else
			return true;
	}
}
