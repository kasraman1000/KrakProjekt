/**
 * 
 */
package models;

import routing.KrakEdge;

/**
 * Represents an KrakEdge, but tells if the edge is a one way road 
 * or no driving is allowed. Besides that it will also contain a 
 * house number
 * 
 * @author Group 1, B-SWU, 2012E
 * 
 */
public class PathPreface{
	KrakEdge edge1;
	KrakEdge edge2;
	int houseNumber;
	
	/**
	 * The Constructor
	 * @param e1 The edge the one way (if any)
	 * @param e2 The edge the other way (if any)
	 * @param houseNumber The house number to start/end
	 */
	public PathPreface(KrakEdge e1, KrakEdge e2, int houseNumber){
		edge1 = e1;
		edge2 = e2;
		this.houseNumber = houseNumber;
	}

	@Override
	public String toString() {
		return "PathPreface [edge1=" + edge1 + ",\n edge2=" + edge2
				+ ",\n houseNumber=" + houseNumber + "]";
	}

	/**
	 * @return the edge1
	 */
	public KrakEdge getEdge1() {
		return edge1;
	}

	/**
	 * @return the edge2
	 */
	public KrakEdge getEdge2() {
		return edge2;
	}

	/**
	 * @return the houseNumber
	 */
	public int getHouseNumber() {
		return houseNumber;
	}
}