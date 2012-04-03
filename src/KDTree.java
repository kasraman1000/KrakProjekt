import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class KDTree
{
	//Dimensions
	private int k;
	private KDNode root;
	double[] origo;
	double[] top;
	private static KDTree tree = new KDTree(2);
	private static Random r = new Random();
	
	/*
	public static void main(String[] args)
	{
		
		
		double[][] testCoords = new double[1000][2];
		double[] kdnNode = {1, 0};
		KDNode kdn = KDTree.getTree().new KDNode(new Node(kdnNode));
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(int i = 0; i < 1000; i++)
		{
			double[] d1 = {i, 0};
			testCoords[i] = d1;
		}
		for(int i = 0; i < 1000; i++)
		{
			nodes.add(new Node(testCoords[i]));
		}



		for(int i = 0; i < 10; i ++)
		{
			Node test = kdn.medianTest(nodes, 0, 0);
			System.out.println(test.coords[0]);
		}
		
		
		try{
			System.out.println("Building...");
			long time = System.currentTimeMillis();
		KDTree.getTree().initialize("C:\\Users\\Mark\\Documents\\UR\\Førsteårs Projekt\\krak-data\\kdv_node_unload.txt", "C:\\Users\\Mark\\Documents\\UR\\Førsteårs Projekt\\krak-data\\kdv_unload.txt");
		System.out.println("Millis to build: " + (System.currentTimeMillis()-time));
		double[] a = {600000, 6050000};
		double[] b = {700000, 6100000};
		double[] c = {600000, 6050000};
		double[] d = {800000, 6300000};

		for(int i = 0; i < 6; i++)
		{
			time = System.currentTimeMillis();
			Road[] roads = KDTree.getTree().searchRange(new Region(tree.origo[0], tree.origo[1] , tree.top[0], tree.top[1]));
			System.out.println("Millies to search and add roads: " + (System.currentTimeMillis()-time));
			System.out.println("Number of roads found: " + roads.length);
			time = System.currentTimeMillis();
			Road[] roadi = KDTree.getTree().searchRange(new Region(a[0], a[1], b[0], b[1]));
			System.out.println("Millies to search and add roads: " + (System.currentTimeMillis()-time));
			System.out.println("Number of roads found: " + roadi.length);
			time = System.currentTimeMillis();
			Road[] roadu = KDTree.getTree().searchRange(new Region(c[0], c[1], d[0], d[1]));
			System.out.println("Millies to search and add roads: " + (System.currentTimeMillis()-time));
			System.out.println("Number of roads found: " + roadu.length);
			System.out.println("-------------------------------------------------");
		}
		
		}catch(IOException e)
		{
			System.out.println("error");
		}
			
	}
*/
	
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
	
	private int zoomLevel(double[] p1, double[] p2)
	{
		if(p2[0]-p1[0] < 1000)
			return 4;
		if(p2[0]-p1[0] < 10000)
			return 3;
		if(p2[0]-p1[0] < 100000)
			return 2;

			return 1;
	}
	
	private boolean filterRoad(int zoomLevel, Road road)
	{
		
		if(zoomLevel < 4)
		{
			if(road.getType() == 11 || road.getType() == 8 || road.getType() == 48 || road.getType() == 28)
				return false;
		}
		if( zoomLevel < 3)
		{
			if(road.getType() == 6 || road.getType() == 10 || road.getType() == 99 || road.getType() == 0 || road.getType() == 95 || road.getType() == 26 || road.getType() == 34 || road.getType() == 35 || road.getType() == 46)
				return false;
		}
		if( zoomLevel < 2)
		{
			if(road.getType() == 4 || road.getType() == 5 || road.getType() == 80 || road.getType() == 31 || road.getType() == 32 || road.getType() == 33 || road.getType() == 24 || road.getType() == 25 || road.getType() == 44 || road.getType() == 45)
				return false;
		}
		return true;
	}
	
	public Road[] searchRange(Region region)
	{
		double[] p1 = region.getLeftPoint();
		double[] p2 = region.getRightPoint();
		int zoom = zoomLevel(p1, p2);
		HashSet<Road> roads = new HashSet<Road>(1000);
		ArrayList<Node> nodes= new ArrayList<Node>();
		long time = System.currentTimeMillis();
		tree.searchRange(root, nodes, 0, origo, top, p1, p2);
		System.out.println("Millies to search: " + (System.currentTimeMillis()-time));
		time = System.currentTimeMillis();
		for(Node n : nodes)
		{
			for(Road r : n.getRoads())
			{
				if(filterRoad(zoom, r))
				roads.add(r);
			}
		}
		System.out.println("Millies to add roads: " + (System.currentTimeMillis()-time));
		time = System.currentTimeMillis();
		Road[] result = roads.toArray(new Road[0]);
		System.out.println("Millies to convert: " + (System.currentTimeMillis()-time));
		return result;
	}
	
	
	public Road[] searchRange(double[] p1, double[] p2)
	{
		return searchRange(new Region(p1[0], p1[1], p2[0], p2[1]));
	}
	
	public Road[] searchRange(Point p1, Point p2)
	{
		return searchRange(new Region(p1.x, p1.y, p2.x, p2.y));
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
		
		/**
		 * Returns the median node of a small collection. The nodes in the collection is chosen randomly from the nodes ArrayList. 
		 * @param nodes Collection to retrieve median from.
		 * @param nth	Not in use.
		 * @param depth	Recursion level of the function calling this function.
		 * @return	median node of a small sample collection.
		 */
		public Node median(ArrayList<Node> nodes, int nth, int depth)
		{
			int size = (int) (10*(Math.log10(nodes.size())+1));
			Node[] randomNodes = new Node[size];
			for(int i = 0; i < size; i++)
			{
				 randomNodes[i] = nodes.get(Math.abs(r.nextInt())%nodes.size());
			}
			NodeComparator nc = new NodeComparator(depth, k);
			Arrays.sort(randomNodes, 0, randomNodes.length-1, nc);
			return randomNodes[size/2];
		}
		
		private Node medianTest(ArrayList<Node> nodes, int nth, int depth) {
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
		/**
		 * 
		 * @param nodes The nodes that the KDTree/subtree is build from
		 * @param depth	Recursion depth of the function call.
		 * @return	The KDNode that contains the median node for the nodes collection.
		 */
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
				if (!rightNodes.isEmpty()) result.right = expand(rightNodes, depth+1);
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
