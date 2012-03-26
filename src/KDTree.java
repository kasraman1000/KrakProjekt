import java.io.IOException;
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
	
	public boolean intersecting(double[] h1, double[] h2, double[] r1, double[] r2)
	{
		for(int i = 0; i < k; i++)
		{
			if(r1[i] > h2[i] || r2[i] < h1[i]) return false;
		}
		return true;
	}
	
	public boolean fullyContained(double[] h1, double[] h2, double[] r1, double[] r2)
	{
		for(int i = 0; i < k; i++)
		{
			if(h1[i] < r1[i] || h2[i] > r2[i]) return false;
		}
		return true;
	}
	
	public boolean nodeContained(KDNode kdn, double[] r1, double[] r2)
	{
		for(int i=0; i < k; i++)
		{
			if(kdn.getNode().coords[i] < r1[i] || kdn.getNode().coords[i] > r2[i]) return false;
		}
		return true;
	}
	
	
	public void fillWithSubTree(KDNode kdn, ArrayList<Node> nodes)
	{
		if(kdn.left != null)
		{
			nodes.add(kdn.left.getNode());
			nodes.add(kdn.right.getNode());
			fillWithSubTree(kdn.left, nodes);
			fillWithSubTree(kdn.right, nodes);
		}
		else if (kdn.right != null)
		{
			nodes.add(kdn.right.getNode());
			fillWithSubTree(kdn.right, nodes);
		}
	}
	
	public double[] changePoint(KDNode kdn, int depth, double[] r)
	{
		r[depth%k] = kdn.getNode().coords[depth%k];
		return r;
	}

	public void searchRange(KDNode kdn, ArrayList<Node> nodes, int depth, double[] r1, double[] r2)
	{
		if(kdn.isLeaf())
		{
			if(nodeContained(kdn, r1, r2)) {nodes.add(kdn.getNode());}
		}
		else
		{
			if (fullyContained(changePoint(kdn, depth, r1), r2, r1, r2))
			{
				fillWithSubTree(kdn, nodes);
			}
			else if (intersecting(changePoint(kdn, depth, r1), r2, r1, r2))
			{
				searchRange(kdn.left, nodes, depth, changePoint(kdn, depth, r1), r2);
			}
			
			
		}

		
		
	}
	
	
	public static void main(String[] args)
	{
		KDTree tree = new KDTree(2);

		try {
			long time1 = System.currentTimeMillis();
			ArrayList<Node> nodes = KrakLoader.load("C:\\Users\\Mark\\Documents\\UR\\Førsteårs Projekt\\krak-data\\kdv_node_unload.txt", "C:\\Users\\Mark\\Documents\\UR\\Førsteårs Projekt\\krak-data\\kdv_unload.txt");
			System.out.println(System.currentTimeMillis()-time1);
			System.out.println("Building tree...");
			long time2 = System.currentTimeMillis();
			tree.build(nodes);
			System.out.println(System.currentTimeMillis()-time2);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		ArrayList<Node> nodes = new ArrayList<Node>();
		double[] coords = {1 ,1};
		nodes.add(new Node(coords));
		double[] coords1 = {7 ,9};
		nodes.add(new Node(coords1));
		double[] coords2 = {2 ,8};
		nodes.add(new Node(coords2));
		double[] coords3 = {10 ,10};
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
		*/
		

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
		
		public boolean isLeaf()
		{
			if(right == null) return true;
			else return false;
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