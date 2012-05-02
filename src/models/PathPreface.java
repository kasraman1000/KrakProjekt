/**
 * 
 */
package models;

import routing.KrakEdge;

/**
 * @author Yndal
 *
 */
public class PathPreface{
	KrakEdge edge1;
	KrakEdge edge2;
	int houseNumber;
	
	public PathPreface(KrakEdge e1, KrakEdge e2, int houseNumber){
		edge1 = e1;
		edge2 = e2;
		this.houseNumber = houseNumber;
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
