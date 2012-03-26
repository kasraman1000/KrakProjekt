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
		double[] coords = {1 ,1};
		nodes.add(new Node(coords));
		double[] coords1 = {0.5 ,1.5};
		nodes.add(new Node(coords1));
		double[] coords2 = {4 ,4};
		nodes.add(new Node(coords2));
		double[] coords3 = {3 ,1};
		nodes.add(new Node(coords3));
		double[] coords4 = {2 ,3};
		nodes.add(new Node(coords4));
		double[] coords5 = {4 ,0.5};
		nodes.add(new Node(coords5));
		
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
			//System.out.println("Nodes.size(): " + nodes.size());
			//System.out.println("At depth: " + depth);
			//for (Node n : nodes) {
				//System.out.print(n.coords[0] + ", " + n.coords[1] + "\t");
			//}
			//System.out.println();
			
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
				result.left = expand(leftNodes, depth+1);
				result.right = expand(rightNodes, depth+1);
				return result;
			}
			else if (nodes.size() == 2)
			{
				if (nodes.get(0).coords[depth%k] > nodes.get(1).coords[depth%k])
				{
					KDNode result = new KDNode(nodes.get(1));
					result.left = new KDNode(nodes.get(0));					
					return result;
					
				}
				else
				{
					KDNode result = new KDNode(nodes.get(0));
					result.left = new KDNode(nodes.get(1));
					return result;
				}

			}
			else
			{
				return new KDNode(nodes.get(0));
			}
		}
		
	}
	
	/*
	private Node medianSlow(ArrayList<Node> nodes, int depth)
	{
			
	}
	
	
    private void quickSort(ArrayList<Node> a, int lo, int hi, int d) { 
        if (hi <= lo) return;
        int j = partition(a, lo, hi, d);
        quickSort(a, lo, j-1, d);
        quickSort(a, j+1, hi, d);
    }
    
    private int partition(ArrayList<Node> a, int lo, int hi, int d) {
        int i = lo;
        int j = hi + 1;
        Node v = a.get(lo);
        while (true) { 

            // find item on lo to swap
            while (less(a.get(i++), v, d))
                if (i == hi) break;

            // find item on hi to swap
            while (less(v, a.get(j--), d))
                if (j == lo) break;      // redundant since a[lo] acts as sentinel

            // check if pointers cross
            if (i >= j) break;

            exch(a, i, j);
        }

        // put v = a[j] into position
        exch(a, lo, j);

        // with a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }
    
    private boolean less(Node v, Node w, int d) {
        return v.coords[d%k] < w.coords[d%k];
    }
    
    private void exch(ArrayList<Node> a, int i, int j) {
        Node swap = a.get(i);
        a.get(i) = a.get(j);
        a[j] = swap;
    }
    
    */
}