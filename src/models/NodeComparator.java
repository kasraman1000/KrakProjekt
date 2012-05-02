package models;
import java.util.Comparator;



public class NodeComparator implements Comparator {
	int dimension;
	
	public NodeComparator(int depth, int k)
	{
		dimension = depth%k;
	}
	
	public int compare(Object o1, Object o2)
	{
		Node n1 = (Node) o1;
		Node n2 = (Node) o2;
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
