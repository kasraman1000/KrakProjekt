import routing.Bag;
import routing.DirectedEdge;


public class CompleteRoad implements Comparable<CompleteRoad> 
{
	private String roadName;
	private int postCode;
	private Bag<DirectedEdge> edges;
	
	public String getRoadName() {
		return roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public int getPostCode() {
		return postCode;
	}

	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}

	public Bag<DirectedEdge> getEdges() {
		return edges;
	}

	public void addEdge(DirectedEdge e) {
		edges.add(e);
	}

	public int compareTo(CompleteRoad cr)
	{
		return roadName.compareTo(cr.getRoadName());
	}
}
