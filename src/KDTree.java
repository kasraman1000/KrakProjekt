import java.util.ArrayList;
import java.util.Arrays;

public class KDTree
{
	//Dimensions
	private int k;
	private KDNode root;

	public KDTree(int k)
	{
		this.k = k;
	}
	
	public void build(ArrayList<Node> nodes)
	{
		root = new KDNode(nodes, 0);
	}
	
	//Nested class
	private class KDNode
	{
		private KDNode right, left;
		private Node node;
		
		private KDNode(Node n)
		{
			node = n;
		}
		
		private KDNode(ArrayList<Node> nodes, int depth)
		{
			node = expand(nodes, depth).getNode();
		}
		
		public Node getNode()
		{
			return node;
		}
		
		private Node median(ArrayList<Node> nodes, int depth) {
			int dimension = depth % k;
			int median = nodes.size() / 2;
			ArrayList<Node> below = new ArrayList<Node>();
			ArrayList<Node> above = new ArrayList<Node>();
			Node pivot = nodes.get(0);
			nodes.remove(0);
			for (Node n : nodes) {
				if (n.coords[dimension] < pivot.coords[dimension]) below.add(n);
				else above.add(n);
			}
			int i = below.size();
			int j = nodes.size() - above.size();
			
			
			
			if (median < i) return median(below,depth);
			else if (median >= j) return median(above, 	median-j);
			else return pivot;
			
		}
		
		public KDNode expand(ArrayList<Node> nodes, int depth)
		{
			if (nodes.size() > 2)
			{
				int dimension = depth%k;
				Node medianNode = median(nodes, depth);
				KDNode result = new KDNode(medianNode);
				nodes.remove(medianNode);
				ArrayList<Node> rightNodes = new ArrayList<Node>();
				ArrayList<Node> leftNodes = new ArrayList<Node>();
				double relevantCoord = medianNode.getCoord(dimension);
				
				for(Node n: nodes)
				{
					if(n.getCoord(dimension) < relevantCoord)
					{
						leftNodes.add(n);
					}
					else if(n.getCoord(dimension) > relevantCoord)
					{
						rightNodes.add(n);
					}
				}
				result.left = expand(leftNodes, depth+1);
				result.right = expand(rightNodes, depth+1);
				return result;
			}
			else if (nodes.size() == 2)
			{
				if (nodes.get(0).getCoord(depth%k) > nodes.get(1).getCoord(depth%k))
				{
					KDNode result = new KDNode(nodes.get(0));
					result.left = new KDNode(nodes.get(1));
					return result;
					
				}
				else
				{
					KDNode result = new KDNode(nodes.get(1));
					result.left = new KDNode(nodes.get(0));
					return result;
				}

			}
			else
			{
				return new KDNode(nodes.get(0));
			}
		}
		
	}
}