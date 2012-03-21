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
	
	public static void main(String[] args)
	{
		KDTree tree = new KDTree(2);
		ArrayList<Node> nodes = new ArrayList<Node>();
		double[] coords = {1 ,2};
		nodes.add(new Node(coords));
		double[] coords1 = {5 ,8};
		nodes.add(new Node(coords1));
		double[] coords2 = {0 ,9};
		nodes.add(new Node(coords2));
		double[] coords3 = {8 ,0};
		nodes.add(new Node(coords3));
		double[] coords4 = {2 ,2};
		nodes.add(new Node(coords4));
		double[] coords5 = {4 ,3};
		nodes.add(new Node(coords5));
		double[] coords6 = {4 ,9};
		nodes.add(new Node(coords6));
		double[] coords7 = {7 ,7};
		nodes.add(new Node(coords7));
		double[] coords8 = {0 ,5};
		nodes.add(new Node(coords8));
		
		tree.build(nodes);
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
		
		private Node median(ArrayList<Node> nodes, int nth, int depth) {
			int dimension = depth % k;
			ArrayList<Node> below = new ArrayList<Node>();
			ArrayList<Node> above = new ArrayList<Node>();
			Node pivot = nodes.get(0);
			for (Node n : nodes) {
				if (n.coords[dimension] < pivot.coords[dimension]) below.add(n);
				else if (n.coords[dimension] < pivot.coords[dimension]) above.add(n);
			}
			int i = below.size();
			int j = nodes.size() - above.size();
			
			
			
			if (nth < i) return median(below, nth, depth);
			else if (nth >= j) return median(above, nth-j, depth);
			else return pivot;
			
		}
		
		public KDNode expand(ArrayList<Node> nodes, int depth)
		{
			System.out.println("Nodes.size(): " + nodes.size());
			System.out.println("At depth: " + depth);
			for (Node n : nodes) {
				System.out.print(n.coords[0] + ", " + n.coords[1] + "\t");
			}
			System.out.println();
			
			if (nodes.size() > 2)
			{
				int dimension = depth%k;
				Node medianNode = median(nodes, nodes.size()/2, depth);
				System.out.println("Median:" + medianNode.coords[0] + ", " + medianNode.coords[1] + "\t");
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