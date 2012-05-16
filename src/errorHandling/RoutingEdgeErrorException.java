package errorHandling;

/**
 * This class is to be thrown if the client is searching 
 * for a route where no driving is allowed
 * 
 * @author Group 1, B-SWU, 2012E
 */
public class RoutingEdgeErrorException extends ClientInputException {
	private int routeNodeId, edgeId1, edgeId2;
	
	/**
	 * If the id's of the edges did not match
	 * 
	 * @param routeNodeId Id to be compared to
	 * @param edgeId1 First possibility
	 * @param edgeId2 Second possibility
	 */
	public RoutingEdgeErrorException(int routeNodeId, int edgeId1, int edgeId2) {
		this.routeNodeId = routeNodeId;
		this.edgeId1 = edgeId1;
		this.edgeId2 = edgeId2;
	}
	
	@Override
	public StatusCode getStatusCode(){
		return StatusCode.ROUTING_EDGE_ERROR;
	}
	
	@Override
	public String getMessage(){
		return "Node not corresponding to id\n" +
				"nodeId: " + routeNodeId + ", and edgeId1: " + edgeId1 + ", edgeId2: " + edgeId2;
	}
}
