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
	private int lastZoomLevel = 5;
	
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
		double[] a = {0, 0};
		double[] b = {500000, 500000};
		double[] c = {50000, 210000};
		double[] d = {59000, 220000};

		for(int i = 0; i < 1; i++)
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
			e.printStackTrace();
		}
			
	}
	*/
	public int getLastZoomLevel()
	{
		return lastZoomLevel;
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
	
	/**
	 * Returns the zoom level, which determines the filtering of the roads
	 * @param p1 A point in the rectangle that bounds the viewport
	 * @param p2 Another point in the rectangle that bounds the viewport
	 * @return The lowest priority that should be displayed
	 */
	private int zoomLevel(double[] p1, double[] p2)
	{
		if(p2[0]-p1[0] < 20000)
			return 1;
		if(p2[0]-p1[0] < 40000)
			return 2;
		if(p2[0]-p1[0] < 80000)
			return 3;
		if(p2[0]-p1[0] < 200000)
			return 4;

			return 5;
	}
	
	/**
	 * Returns true or false dependent on whether the road should be displayed or not according to the priority
	 * @param zoomLevel The priority that the road should be equals or greater than to be displayed
	 * @param road	The road which should be filtered
	 * @return whether the road has high enough priority to be displayed
	 */
	private boolean filterRoad(int zoomLevel, Road road)
	{
		if(road.getPriority() < zoomLevel)
			return false;
		else
			return true;
	}
	/**
	 * Returns all roads in a rectangle bound by a region filtered by priority.
	 * @param region The region which binds the viewport
	 * @return All roads within the rectangle, which are relevant to display
	 */
	public Road[] searchRange(Region region)
	{
		//If coordinates are of wrong input, correct them
		region.adjust();
		double[] p1 = region.getLeftPoint();
		double[] p2 = region.getRightPoint();
		//Choosing filter dependent on the width of the viewport
		int zoom = zoomLevel(p1, p2);
		lastZoomLevel = zoom;
		System.out.println("zoom level " + zoom);
		System.out.println("Searching region: x1: " + p1[0] + " y1: " + p1[1] + " x2: " + p2[0] + " y2: " + p2[1]);
		//Creating a HashSet to make sure that no road are contained twice.
		HashSet<Road> roads = new HashSet<Road>(1000);
		ArrayList<Node> nodes= new ArrayList<Node>();
		tree.searchRange(root, nodes, 0, origo, top, p1, p2);
		//Checking for priority and making sure that no road is added twice
		for(Node n : nodes)
		{
			for(Road r : n.getRoads())
			{
				if(filterRoad(zoom, r))
				roads.add(r);
			}
		}
		Road[] result = roads.toArray(new Road[0]);
		System.out.println("Size: " + result.length);
		return result;
	}
	
	/**
	 * Returns all roads in a rectangle bound by two points filtered by priority.
	 * @param p1 x and y coordinates for one of the points
	 * @param p2 x and y coordinates for the other point
	 * @return All roads within the rectangle, which are relevant to display
	 */
	public Road[] searchRange(double[] p1, double[] p2)
	{
		return searchRange(new Region(p1[0], p1[1], p2[0], p2[1]));
	}
	
	/**
	 * Returns all roads in a rectangle bound by two points filtered by priority.
	 * @param p1 x and y coordinates for one of the points
	 * @param p2 x and y coordinates for the other point
	 * @return All roads within the rectangle, which are relevant to display
	 */
	public Road[] searchRange(Point p1, Point p2)
	{
		return searchRange(new Region(p1.x, p1.y, p2.x, p2.y));
	}
	
	/**
	 * Returns true if the two regions overlap or false if they do not.
	 * @param h1 x and y coordinates for one of the points of the first region
	 * @param h2 x and y coordinates for the other point of the first region
	 * @param r1 x and y coordinates for one of the points of the second region
	 * @param r2 x and y coordinates for the other point of the second region
	 * @return true if the regions intersect, false if not
	 */
	private boolean intersecting(double[] h1, double[] h2, double[] r1, double[] r2)
	{
		for(int i = 0; i < k; i++)
		{
			if(r1[i] >= h2[i] || r2[i] <= h1[i]) return false;
		}
		return true;
	}
	
	/**
	 * Returns true if the first region is fully contained in the second.
	 * @param h1 x and y coordinates for one of the points of the first region
	 * @param h2 x and y coordinates for the other point of the first region
	 * @param r1 x and y coordinates for one of the points of the second region
	 * @param r2 x and y coordinates for the other point of the second region
	 * @return true if the first region is fully contained in the second, false if not.
	 */
	private boolean fullyContained(double[] h1, double[] h2, double[] r1, double[] r2)
	{
		for(int i = 0; i < k; i++)
		{
			if(h1[i] < r1[i] || h2[i] > r2[i]) return false;
		}
		return true;
	}
	
	/**
	 * Returns true if the node is contained in the region.
	 * @param kdn The KDNode to check whether it is contained
	 * @param r1 x and y coordinates for one of the points of the region
	 * @param r2 x and y coordinates for the other point of the region
	 * @return true if the KDNode is contained in the region, false if not.
	 */
	private boolean nodeContained(KDNode kdn, double[] r1, double[] r2)
	{
		for(int i=0; i < k; i++)
		{
			if(kdn.getNode().coords[i] < r1[i] || kdn.getNode().coords[i] > r2[i]) return false;
		}
		return true;
	}
	
	/**
	 * Fills the nodes with the sub tree of the node.
	 * @param kdn The KDNode, which's subtree we want to add to nodes
	 * @param nodes The collection that should be filled with nodes
	 * @param depth The recursion depth
	 */
	private void fillWithSubTree(KDNode kdn, ArrayList<Node> nodes, int depth)
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

	private double[] changePoint(KDNode kdn, int depth, double[] r)
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
		origo = Road.getOrigo();
		top = Road.getTop();
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
