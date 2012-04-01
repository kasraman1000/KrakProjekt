import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class KDTree
{
	//Dimensions
	private int k;
	private KDNode root;
	double[] origo;
	double[] top;
	private static KDTree tree = new KDTree(2);
	
	public static void main(String[] args)
	{
		try{
			System.out.println("Building...");
			long time = System.currentTimeMillis();
		KDTree.getTree().initialize("C:\\Users\\Mark\\Documents\\UR\\F�rste�rs Projekt\\krak-data\\kdv_node_unload.txt", "C:\\Users\\Mark\\Documents\\UR\\F�rste�rs Projekt\\krak-data\\kdv_unload.txt");
		System.out.println("Millis to build: " + (System.currentTimeMillis()-time));
		
		double[] a = {600000, 6050000};
		double[] b = {700000, 6100000};
		double[] c = {600000, 6050000};
		double[] d = {800000, 6300000};
		time = System.currentTimeMillis();
		Road[] roads = KDTree.getTree().searchRange(tree.origo, tree.top);
		System.out.println("Millies to search and add roads: " + (System.currentTimeMillis()-time));
		time = System.currentTimeMillis();
		Road[] roadi = KDTree.getTree().searchRange(a, b);
		System.out.println("Millies to search and add roads: " + (System.currentTimeMillis()-time));
		time = System.currentTimeMillis();
		Road[] roadu = KDTree.getTree().searchRange(c, d);
		System.out.println("Millies to search and add roads: " + (System.currentTimeMillis()-time));
		
		}catch(IOException e)
		{
			System.out.println("error");
		}
	}
	
	private KDTree(int k)
	{
		this.k = k;
	}
	
	public static KDTree getTree()
	{
		return tree;
	}

	public void build(ArrayList<Node> nodes)
	{
		new KDNode(nodes, 0);
		
	}
	
	public Road[] searchRange(double[] p1, double[] p2)
	{
		HashSet<Road> roads = new HashSet<Road>();
		ArrayList<Node> nodes= new ArrayList<Node>();
		long time = System.currentTimeMillis();
		tree.searchRange(root, nodes, 0, origo, top, p1, p2);
		System.out.println("Millies to search: " + (System.currentTimeMillis()-time));
		time = System.currentTimeMillis();
		for(Node n : nodes)
		{
			for(Road r : n.getRoads())
			{
				roads.add(r);
			}
		}
		System.out.println("Millies to add roads: " + (System.currentTimeMillis()-time));
		time = System.currentTimeMillis();
		Road[] result = roads.toArray(new Road[0]);
		System.out.println("Millies to convert: " + (System.currentTimeMillis()-time));
		return result;
	}
	
	public boolean intersecting(double[] h1, double[] h2, double[] r1, double[] r2)
	{
		for(int i = 0; i < k; i++)
		{
			if(r1[i] >= h2[i] || r2[i] <= h1[i]) return false;
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
	
	
	public void fillWithSubTree(KDNode kdn, ArrayList<Node> nodes, int depth)
	{
		if(kdn.right != null)
		{
			nodes.add(kdn.right.getNode());
			fillWithSubTree(kdn.right, nodes, depth+1);
		}
		if (kdn.left != null)
		{
			nodes.add(kdn.left.getNode());
			fillWithSubTree(kdn.left, nodes, depth+1);
		}
	}

	public double[] changePoint(KDNode kdn, int depth, double[] r)
	{
		if(depth%k==0)
		{ 
			double[] result = {kdn.getNode().coords[0], r[1]};
			return result;
		}
		else
		{
			double[] result = {r[0] ,kdn.getNode().coords[1]};
			return result;
		}
	}

	private void searchRange(KDNode kdn, ArrayList<Node> nodes, int depth, double[] cr1, double[] cr2, double[] r1, double[] r2)
	{
		if(nodeContained(kdn, r1, r2)) {nodes.add(kdn.getNode());}
		
		if(kdn.left != null)
		{
			if (fullyContained(cr1, changePoint(kdn, depth, cr2), r1, r2))
			{
				nodes.add(kdn.left.getNode());
				fillWithSubTree(kdn.left, nodes, depth);
			}
			else if (intersecting(cr1, changePoint(kdn, depth, cr2), r1, r2))
			{
				searchRange(kdn.left, nodes, depth+1, cr1, changePoint(kdn, depth, cr2), r1, r2);
			}
			if (kdn.right != null)
			{
				if (fullyContained(changePoint(kdn, depth, cr1), cr2, r1, r2))
				{
					nodes.add(kdn.right.getNode());
					fillWithSubTree(kdn.right, nodes, depth+2);
				}
				else if (intersecting(changePoint(kdn, depth, cr1), cr2, r1, r2))
				{
					searchRange(kdn.right, nodes, depth+1, changePoint(kdn, depth, cr1), cr2, r1, r2);
				}
			}
		}

	}
	
	public void initialize(String nodePath, String roadPath) throws IOException
	{
		ArrayList<Node> nodes = KrakLoader.load(nodePath, roadPath);
		tree.build(nodes);
		origo = findSmallest(nodes);
		top = findLargest(nodes);
	}
	
	public double[] findLargest(ArrayList<Node> nodes)
	{
		double[] x = {0, 0};
		for(Node n : nodes)
		{
			if(n.coords[0] > x[0]) x[0] = n.coords[0];
			if(n.coords[1] > x[1]) x[1] = n.coords[1];
		}
		return x;
		
	}
	
	public double[] findSmallest(ArrayList<Node> nodes)
	{
		double[] x = {0, 0};
		for(Node n : nodes)
		{
			if(n.coords[0] < x[0]) x[0] = n.coords[0];
			if(n.coords[1] < x[1]) x[1] = n.coords[1];
		}
		return x;
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
			Node pivot;
			if(depth < 4 && nodes.size() > 150)
			{
				pivot = nodes.get(100);
			}
			else
			{
				pivot = nodes.get(0);
			}
			
			
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
