
package models;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class KDTree
{
	//Dimensions
	private int k;
	//The root node in the tree structure
	private KDNode root;
	//The smallest coordinates of the map.
	double[] origo;
	//The biggest coordinates of the map.
	double[] top;
	private static Random r = new Random();
	
	public KDTree(int k)
	{
		this.k = k;
	}
	
	public KDNode getRoot()
	{
		return root;
	}

	public void build(ArrayList<Node> nodes)
	{
		new KDNode(nodes);
	}
	
	/**
	 * Returns true if the two regions overlap or false if they do not.
	 * @param h1 x and y coordinates for one of the points of the first region
	 * @param h2 x and y coordinates for the other point of the first region
	 * @param r1 x and y coordinates for one of the points of the second region
	 * @param r2 x and y coordinates for the other point of the second region
	 * @return true if the regions intersect, false if not
	 */
	public boolean intersecting(double[] h1, double[] h2, double[] r1, double[] r2)
	{
		for(int i = 0; i < k; i++)
		{
			if(r1[i] > h2[i] || r2[i] < h1[i]) return false;
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
	public boolean fullyContained(double[] h1, double[] h2, double[] r1, double[] r2)
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
			if(kdn.getNode().getCoord(i) <= r1[i] || kdn.getNode().getCoord(i) >= r2[i]) return false;
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
			double[] result = {kdn.getNode().getCoord(0), r[1]};
			return result;
		}
		else
		{
			double[] result = {r[0] ,kdn.getNode().getCoord(1)};
			return result;
		}
	}
	
	/**
	 * Returns a collection with nodes in the region.
	 * @param region The region to search.
	 * @return	A collection with nodes contained in the region.
	 */
	public ArrayList<Node> searchRange(Region region)
	{
		//If coordinates are of wrong input, correct them
		double[] p1 = region.getLeftPoint();
		double[] p2 = region.getRightPoint();
		//Creating a HashSet to make sure that no road are contained twice.
		ArrayList<Node> nodes = new ArrayList<Node>();
		searchRange(root, nodes, 0, origo, top, p1, p2);

		return nodes;
	}
	
	/**
	 * Fills a collection with nodes from the tree in the given region.
	 * @param kdn The node to define regions.
	 * @param nodes	The collection to hold the result, which is built recursively.
	 * @param depth	The recursion depth of the method call.
	 * @param cr1	The smallest coordinate set of the region, which the call is investigating.
	 * @param cr2	The biggest coordinate set of the region, which the call is investigating.
	 * @param r1	The smallest coordinate of the search region.
	 * @param r2	The biggest coordinate of the search region.
	 */
	public void searchRange(KDNode kdn, ArrayList<Node> nodes, int depth, double[] cr1, double[] cr2, double[] r1, double[] r2)
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
	
	public void initialize(ArrayList<Node> nodes)
	{	
		build(nodes);
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
		
		/**
		 * Constructor to build the kd-tree. Expand is called recursively until the tree is created.
		 * @param nodes The nodes that the kd-tree is build from.
		 */
		private KDNode(ArrayList<Node> nodes)
		{
			KDNode kdn = expand(nodes, 0);
			node = kdn.getNode();
			root = kdn;
		}


		public String toString()
		{
			return "X= " + node.getCoord(0) + " Y= " + node.getCoord(1);
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
		private Node median(ArrayList<Node> nodes, int nth, int depth)
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
		/**
		 * Returns the nth element from a collection. This method is not used in the program.
		 * @param nodes Collection to retrieve the element from.
		 * @param nth	The index which the returned element should have.
		 * @param depth	Recursion level of the function calling this function.
		 * @return	nth element from the collection.
		 */
		private Node medianComplex(ArrayList<Node> nodes, int nth, int depth) {
			int dimension = depth % k;
			ArrayList<Node> below = new ArrayList<Node>();
			ArrayList<Node> above = new ArrayList<Node>();
			Node pivot;
			if(nodes.size() > 150)
			{
				pivot = nodes.get(100);
			}
			else
			{
				pivot = nodes.get(0);
			}
			
			
			for (Node n : nodes) {
				if (n.getCoord(dimension) < pivot.getCoord(dimension)) below.add(n);
				else if (n.getCoord(dimension) > pivot.getCoord(dimension)) above.add(n);
				else if (pivot != n) above.add(n);
			}
			int i = below.size();
			int j = nodes.size() - above.size();

			if (nth < i) return median(below, nth, depth);
			else if (nth >= j) return median(above, nth-j, depth);
			else return pivot;

		}
		/**
		 * Returns the child node from a collection of nodes and recursively builds the KDTree. Note that expand does remove elements from the collection it is build from.
		 * @param nodes The nodes that the KDTree/subtree is build from
		 * @param depth	Recursion depth of the function call.
		 * @return	The KDNode that contains the median node for the nodes collection.
		 */
		public KDNode expand(ArrayList<Node> nodes, int depth)
		{

			if (nodes.size() > 1)
			{
				int dimension = depth%k;
				Node medianNode = median(nodes, nodes.size()/2, depth);
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
					else if(n.getCoord(dimension) >= relevantCoord)
					{
						rightNodes.add(n);
					}
				}
				if (!leftNodes.isEmpty()) result.left = expand(leftNodes, depth+1);
				if (!rightNodes.isEmpty()) result.right = expand(rightNodes, depth+1);
				return result;
			}
			else
			{
				return new KDNode(nodes.get(0));
			}
		}
	}
}
