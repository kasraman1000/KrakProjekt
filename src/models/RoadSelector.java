package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Class responsible for picking out roads to show
 * 
 * @author Group 1, B-SWU, 2012E
 * 
 */
public class RoadSelector {
	//Set the maximum amount of roads to be showed at once (beside a route)
	private static final int MAX_ROADS = 20000;
	
	private static KDTree kdTree;

	
	/**
	 * Returns all roads in a rectangle bound by a region filtered by priority.
	 * @param region The region which binds the viewport
	 * @param bufferPercent How much to add at each side of the region (fx. 0.7 == 70%)
	 * @return All roads within the rectangle, which are relevant to display 
	 */
	public static Road[] search(Region region, double bufferPercent) 
	{
		//Create a new region that is a copy to prevent addBuffer from making changes to the object.
		Region copyRegion = new Region(region.getLeftPoint()[0], region.getLeftPoint()[1], region.getRightPoint()[0], region.getRightPoint()[1]);
		copyRegion.addBuffer(bufferPercent);

		//Choosing filter dependent on the width of the viewport
		ArrayList<Node> nodes = kdTree.searchRange(copyRegion);
		
		HashSet<Road> roads = new HashSet<Road>(100000, 0.5f);
		for (Node n : nodes) {
			for(Road road : n.getRoads()) {
				roads.add(road);
			}
		}
		ArrayList<Road> result = filter(roads, MAX_ROADS);
	
		return result.toArray(new Road[0]);
	}
	
	/**
	 * Returns all roads in a rectangle bound by two points filtered by priority.
	 * @param nodes The nodes to put into the KDTree
	 */
	public static void initialize(ArrayList<Node> nodes)
	{
		kdTree = new KDTree(2);
		kdTree.initialize(nodes);
	}

	/**
	 * Add up till the maximum amount of roads (depending on their priority)
	 * and return them
	 * 
	 * @param roads The roads to be sorted through
	 * @param max The maximum amount of roads
	 * @return The roads as an ArrayList of Roads
	 */
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

		} while (!((result.size() + nextLevelRoads) > max) && level > 1);
		return result;
	}
}