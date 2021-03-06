/**
 * 
 */
package routing;




//Below is the syntax highlighted version of DirectedEdge.java from � Algorithms.   Here is the Javadoc.


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
    private final String name;
    private final double length;
    private final double time;
    private final double[] fromPoint;
    private final double[] toPoint;
    private static boolean isLengthWeighted;
    
   /**
     * Create a directed edge from v to w with given weight.
     */
    public DirectedEdge(int from, int to, String name, double length, double time, double[] fromPoint, double[] toPoint){
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
    
    public String getName(){
    	return name;
    }

   /**
     * Return the weight (time or length) of this edge.
     */
    public double weight() { 
    	if(isLengthWeighted) return length;
    	return time;
    	}
    
    /**
     * Return the time of this edge
     */
    public double time(){ return time; }

    public static boolean isLengthWeighted(){
    	return isLengthWeighted;
    }
    
    public static void setWeight(boolean lengthWeighted){
    	isLengthWeighted = lengthWeighted;
    }
    
    public double[] getFromPoint(){
    	return fromPoint;
    }
    
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
