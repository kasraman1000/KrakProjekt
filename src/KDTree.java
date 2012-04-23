
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

	private KDTree(int k)
	{
		this.k = k;
	}
	
	public static KDTree getTree()
	{
		return tree;
	}
	
	public KDNode getRoot()
	{
		return root;
	}

	public void build(ArrayList<Node> nodes)
	{
		new KDNode(nodes, 0);
		
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
		KrakLoader.load(nodePath, roadPath);
		ArrayList<Node> nodes = KrakLoader.getNodesForKDTree();
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
		private KDNode expand(ArrayList<Node> nodes, int depth)
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
