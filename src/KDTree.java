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
		new KDNode(nodes, 0);
	}
	
	public static void main(String[] args)
	{
		KDTree tree = new KDTree(2);
		ArrayList<Node> nodes = new ArrayList<Node>();
		double[] coords = {9 ,6};
		nodes.add(new Node(coords));
		double[] coords1 = {0 ,6};
		nodes.add(new Node(coords1));
		double[] coords2 = {7 ,6};
		nodes.add(new Node(coords2));
		double[] coords3 = {8 ,4};
		nodes.add(new Node(coords3));
		double[] coords4 = {4 ,9};
		nodes.add(new Node(coords4));
		double[] coords5 = {9 ,0};
		nodes.add(new Node(coords5));
		double[] coords6 = {10 ,3};
		nodes.add(new Node(coords6));
		double[] coords7 = {3 ,6};
		nodes.add(new Node(coords7));
		double[] coords8 = {7 ,1};
		nodes.add(new Node(coords8));
		
		tree.build(nodes);
		System.out.println(tree.root.toString());
		
		System.out.println("-----------");

		System.out.println(tree.root.getLeftChild());
		System.out.println(tree.root.getRightChild());
		
		System.out.println("-----------");
		
		System.out.println(tree.root.getLeftChild().getLeftChild());
		System.out.println(tree.root.getLeftChild().getRightChild());
		
		System.out.println(tree.root.getRightChild().getLeftChild());
		System.out.println(tree.root.getRightChild().getRightChild());
		
		System.out.println("-----------");
		
		System.out.println(tree.root.getLeftChild().getRightChild().getRightChild());
		
		System.out.println(tree.root.getRightChild().getLeftChild().getLeftChild());
		System.out.println(tree.root.getRightChild().getLeftChild().getRightChild().getRightChild());
		System.out.println(tree.root.getRightChild().getRightChild());
		
		

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
			KDNode kdn = expand(nodes, depth);
			node = kdn.getNode();
			root = kdn;
		}

		
		public String toString()
		{
			return "X= " + node.coords[0] + " Y= " + node.coords[1];
		}
		
		public KDNode getLeftChild()
		{
			return left;
		}
		
		public KDNode getRightChild()
		{
			return right;
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
				else if (n.coords[dimension] > pivot.coords[dimension]) above.add(n);
				else if (pivot != n) above.add(n);
			}
			int i = below.size();
			int j = nodes.size() - above.size();
			
			if (nth < i) return median(below, nth, depth);
			else if (nth >= j) return median(above, nth-j, depth);
			else return pivot;
			
		}
		
		public KDNode expand(ArrayList<Node> nodes, int depth)
		{
			if (nodes.size() > 2)
			{
				int dimension = depth%k;
				Node medianNode = median(nodes, nodes.size()/2, depth);
				
				KDNode result = new KDNode(medianNode);
				nodes.remove(medianNode);
				ArrayList<Node> rightNodes = new ArrayList<Node>();
				ArrayList<Node> leftNodes = new ArrayList<Node>();
				double relevantCoord = medianNode.coords[dimension];
				
				for(Node n: nodes)
				{
					if(n.coords[dimension] < relevantCoord)
					{
						leftNodes.add(n);
					}
					else if(n.coords[dimension] >= relevantCoord)
					{
						rightNodes.add(n);
					}
				}
				if (!leftNodes.isEmpty()) result.left = expand(leftNodes, depth+1);
				result.right = expand(rightNodes, depth+1);
				return result;
			}
			else if (nodes.size() == 2)
			{
				if (nodes.get(0).coords[depth%k] > nodes.get(1).coords[depth%k])
				{
					KDNode result = new KDNode(nodes.get(1));
					result.right = new KDNode(nodes.get(0));					
					return result;
					
				}
				else
				{
					KDNode result = new KDNode(nodes.get(0));
					result.right = new KDNode(nodes.get(1));
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