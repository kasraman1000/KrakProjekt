/**
 * 
 */
package models;

import routing.KrakEdge;

/**
 * Binds two directed edges together and a house number
 * used for navigational purposes
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
