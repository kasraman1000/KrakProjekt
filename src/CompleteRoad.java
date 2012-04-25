import routing.Bag;
import routing.DirectedEdge;


public class CompleteRoad implements Comparable<CompleteRoad> 
{
	private String roadName;
	private int zipCode;
	private Bag<DirectedEdge> edges;
	
	public CompleteRoad(String roadName, int zipCode)
	{
		this.roadName = roadName;
		this.zipCode = zipCode;
		edges = new Bag<DirectedEdge>();
	}
	
	public String getRoadName() {
		return roadName;
	}

	public int getZipCode() {
		return zipCode;
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
