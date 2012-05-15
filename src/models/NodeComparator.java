package models;
import java.util.Comparator;

/**
 * Helper class capable of comparing two nodes
 * by their coordinates
 * 
 * @author Group 1, B-SWU, 2012E
 *
 */

public class NodeComparator implements Comparator<Node> {
	int dimension;
	
	/**
	 * Constructor for NodeComparator
	 * @param depth Depth of the current nodes
	 * @param k Amount of dimensions in the KDTree
	 */
	public NodeComparator(int depth, int k)
	{
		dimension = depth%k;
	}
	
	/**
	 * Compare the two Nodes regarding their coordinates
	 */
	public int compare(Node n1, Node n2)
	{
		if(n1.getCoord(dimension) > n2.getCoord(dimension))
		{
			return 1;
		}
		if(n1.getCoord(dimension) < n2.getCoord(dimension))
		{
			return -1;
		}
		return 0;
	}
}
