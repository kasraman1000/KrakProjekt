/**
 * 
 */
package routing;


/*************************************************************************
 * THIS CLASS HAS BEEN COPIED FROM THE BOOK S&W USED IN
 * THE "BADS" COURSE! THERE HAS ONLY BEEN MINOR MODIFICATIONS
 * IN THIS CLASS
 * 
 *  Compilation:  javac EdgeWeightedDigraph.java
 *  Execution:    java EdgeWeightedDigraph V E
 *  Dependencies: Bag.java DirectedEdge.java
 *
 *  An edge-weighted digraph, implemented using adjacency lists.
 *
 *************************************************************************/

/**
 *  The <tt>EdgeWeightedDigraph</tt> class represents an directed graph of vertices
 *  named 0 through V-1, where each edge has a real-valued weight.
 *  It supports the following operations: add an edge to the graph,
 *  iterate over all of edges leaving a vertex.
 *  Parallel edges and self-loops are permitted.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */



public class KrakEdgeWeightedDigraph {
    private final int V; //Nodes
    private int E; //Edges
    private Bag<KrakEdge>[] adj;
    
    /**
     * Create an empty edge-weighted digraph with V vertices.
     */
    @SuppressWarnings("unchecked")
	public KrakEdgeWeightedDigraph(int V) {
        if (V < 0) throw new RuntimeException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<KrakEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<KrakEdge>();
    }

    /**
     * Copy constructor.
     */
    public KrakEdgeWeightedDigraph(KrakEdgeWeightedDigraph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<KrakEdge> reverse = new Stack<KrakEdge>();
            for (KrakEdge e : G.adj[v]) {
                reverse.push(e);
            }
            for (KrakEdge e : reverse) {
                adj[v].add(e);
            }
        }
    }

   /**
     * Return the number of vertices in this digraph.
     */
    public int V() {
        return V;
    }

   /**
     * Return the number of edges in this digraph.
     */
    public int E() {
        return E;
    }


   /**
     * Add the edge e to this digraph.
     */
    public void addEdge(KrakEdge e) {
        int v = e.from();
        adj[v].add(e);
        E++;
    }


   /**
     * Return the edges leaving vertex v as an Iterable.
     * To iterate over the edges leaving vertex v, use foreach notation:
     * <tt>for (DirectedEdge e : graph.adj(v))</tt>.
     */
    public Iterable<KrakEdge> adj(int v) {
        return adj[v];
    }

   /**
     * Return all edges in this graph as an Iterable.
     * To iterate over the edges, use foreach notation:
     * <tt>for (DirectedEdge e : graph.edges())</tt>.
     */
    public Iterable<KrakEdge> edges() {
        Bag<KrakEdge> list = new Bag<KrakEdge>();
        for (int v = 0; v < V; v++) {
            for (KrakEdge e : adj(v)) {
                list.add(e);
            }
        }
        return list;
    } 

   /**
     * Return number of edges leaving v.
     */
    public int outdegree(int v) {
        return adj[v].size();
    }

   /**
     * Return a string representation of this graph.
     */
    public String toString() {
        String NEWLINE = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (KrakEdge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
}
