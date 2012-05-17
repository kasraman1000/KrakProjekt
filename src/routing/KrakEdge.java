/**
 * 
 */
package routing;



/*************************************************************************
 *  THIS CLASS HAS BEEN COPIED FROM THE BOOK S&W USED IN
 * THE "BADS" COURSE! THERE HAS BEEN MASSIVE CHANGES TO MAKE
 * IT FIT OUR NEEDS - MOST OF THE CLASS HAS BEEN CHANGED/EDITED
 *  
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

public class KrakEdge { 
	private final int from;
    private final int to;
    private final String name;
    private final double length;
    private final double time;
    private final double[] fromPoint;
    private final double[] toPoint;
    private final int vPost;
    private final int hPost;
    private final int vFromHouseNumber;
    private final int vToHouseNumber;
    private final int hFromHouseNumber;
    private final int hToHouseNumber;
    private static boolean isLengthWeighted;
    
    /**
     * Create a directed edge from v to w with given weight.
     * 
     * @param from From id
     * @param to To id
     * @param name Name of the edge
     * @param length Length of the edge
     * @param time The time it takes to travel form one end of the edge to another
     * @param fromPoint The coordinates for the start of the edge
     * @param toPoint The coordinates to the end of the edge
     * @param vPost Postal code for the left side of the edge
     * @param hPost Postal code for the right side of the edge
     * @param vFromHouseNumber The beginning index of the left side of the house numbers
     * @param vToHouseNumber The ending index of the left side of the house numbers
     * @param hFromHouseNumber The beginning index of the right side of the house numbers
     * @param hToHouseNumber The ending index of the right side of the house numbers
     */
    public KrakEdge(int from, int to, String name, double length, double time, double[] fromPoint, double[] toPoint, 
    		int vPost, int hPost, int vFromHouseNumber, int vToHouseNumber, int hFromHouseNumber, int hToHouseNumber){
        this.from = from;
        this.to = to;
        this.name = name;
        this.length = length;
        this.time = time;
        this.fromPoint = fromPoint;
        this.toPoint = toPoint;
        this.vPost = vPost;
        this.hPost = hPost;
        this.vFromHouseNumber = vFromHouseNumber;
        this.vToHouseNumber = vToHouseNumber;
        this.hFromHouseNumber = hFromHouseNumber;
        this.hToHouseNumber = hToHouseNumber;
        
        isLengthWeighted = true;
    }

   /**
     * Return the nodes id where this edge begins.
     * @return beginning node id
     */
    public int from() {
        return from;
    }

   /**
     * Return the nodes id where this edge ends.
     * @return ending node id
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
     * The length of the edge
     * @return length of the edge
     */
    public double getLength(){
    	return length;
    }
    
    /**
     * The time to get from one end of the edge to the other (in minutes)
     * @return time to pass the edge
     */
    public double getTime(){
    	return time;
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
    * The postal code for the left side 
	* @return the vPost
	*/
	public int getvPost() {
		return vPost;
	}

	/**
	 * The postal code for the right side
	 * @return the hPost
	 */
	public int gethPost() {
		return hPost;
	}

	/**
	 * The beginning index of the house numbers at the left side of the edge
	 * @return the vFromHouseNumber
	 */
	public int getvFromHouseNumber() {
		return vFromHouseNumber;
	}

	/**
	 * The ending index of the house numbers at the right side of the edge
	 * @return the vToHouseNumber
	 */
	public int getvToHouseNumber() {
		return vToHouseNumber;
	}

	/**
	 * The beginning index of the house numbers at the right side of the edge
	 * @return the hFromHouseNumber
	 */
	public int gethFromHouseNumber() {
		return hFromHouseNumber;
	}

	/**
	 * The ending index of the house numbers at the right side of the edge
	 * @return the hToHouseNumber
	 */
	public int gethToHouseNumber() {
		return hToHouseNumber;
	}

	/**
     * Return a string representation of this edge.
     * @return A string representation of the edge
     */
    public String toString() {
        return name + ": " + from + "->" + to + ", " + length + " m and " + time + " minutes"
        		+ "\t (house numbers: " + vFromHouseNumber + "-" + vToHouseNumber + ", "
        		+ + hFromHouseNumber + "-" + hToHouseNumber + "), Postal code: " + vPost + "-" + hPost;
    }
}
