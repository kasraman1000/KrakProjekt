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

public class DirectedEdgeKrak { 
	private final int from;
    private final int to;
    private final String name;
    private final double length;
    private final double time;
    private final double[] fromPoint;
    private final double[] toPoint;
    private static boolean isLengthWeighted;
    
   /**
     * Create a directed edge from v to w with given weight.
     */
    public DirectedEdgeKrak(int from, int to, String name, double length, double time, double[] fromPoint, double[] toPoint){
        this.from = from;
        this.to = to;
        this.name = name;
        this.length = length;
        this.time = time;
        this.fromPoint = fromPoint;
        this.toPoint = toPoint;
        isLengthWeighted = true;
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
     * Return the name of this edge (part of a road)
     * 
     * @return String containing the name of this edge
     */
    public String getName(){
    	return name;
    }

   /**
     * Return the weight (time or length) of this edge.
     * May be set using the setWeight()
     * 
     * @return a double of either the length or weight of this edge
     */
    public double weight() { 
    	if(isLengthWeighted) return length;
    	return time;
    	}
    
    /**
     * Return the time of this edge
     */
    public double time(){ return time; }

    /**
     * Tells whether the length or traveltime of the edges are used as weight
     * 
     * @return true if the edges (all of them) if the length of the edges are weighted 
     */
    public static boolean isLengthWeighted(){
    	return isLengthWeighted;
    }
    
    /**
     * Sets the edges to use their length or travel time to be used as weight
     * 
     * @param lengthWeighted
     */
    public static void setWeight(boolean lengthWeighted){
    	isLengthWeighted = lengthWeighted;
    }
    
    /**
     * Get the coordinates of the point the edge starts.
     * 
     * @return x-coord is [0], y-coord is [1]
     */
    public double[] getFromPoint(){
    	return fromPoint;
    }
    
    /**
     * Get the coordinates of the point the edge ends.
     * 
     * @return x-coord is [0], y-coord is [1]
     */
    public double[] getToPoint(){
    	return toPoint;
    }
    
   /**
     * Return a string representation of this edge.
     */
    public String toString() {
        return from + "->" + to + ", " + length + " m and " + time + " minutes";
    }
}
