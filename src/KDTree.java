import java.util.Arrays;

public class KDTree
{
	//Dimensions
	private int k;

	public KDTree(int k, Node[] nodes)
	{
		this.k = k;
		build(nodes);
	}
	
	private void build(Node[] nodes)
	{
		
	}
	
	private void sort(Node[] nodes)
	{
		Arrays.sort(nodes);
	}
	
	
	
	private class KDNode
	{
		KDNode right, left;
		Node node;
		
	}
}