/**
 * 
 */
package routing;




//Below is the syntax highlighted version of DirectedEdge.java from § Algorithms.   Here is the Javadoc.


/*************************************************************************
 *  Compilation:  javac DirectedEdge.java
 *  Execution:    java DirectedEdge
 *
 *  Immutable weighted directed edge.
 *
 *************************************************************************/

/**
 *  The <tt>DirectedEdge</tt> class represents a weighted edge in an directed graph.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */

public class DirectedEdge { 
	private final int from;
    private final int to;
    private final double length;
    private final double time;
    private final double x1;
    private final double y1;
    private final double x2;
    private final double y2;
    
   /**
     * Create a directed edge from v to w with given weight.
     */
    public DirectedEdge(int from, int to, double length, double time, double x1, double y1, double x2, double y2) {
        this.from = from;
        this.to = to;
        this.length = length;
        this.time = time;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

   /**
     * Return the vertex where this edge begins.
     */
    public int from() {
        return from;
    }

   /**
     * Return the vertex where this edge ends.
     */
    public int to() {
        return to;
    }

   /**
     * Return the weight of this edge.
     */
    public double length() { return length; }

   /**
     * Return a string representation of this edge.
     */
    public String toString() {
        return from + "->" + to + ", " + length + " m and " + time + " minutes";
    }
}
