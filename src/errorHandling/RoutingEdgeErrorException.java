package errorHandling;

public class RoutingEdgeErrorException extends ClientInputException {
	private int routeNodeId, edgeId1, edgeId2;
	
	public RoutingEdgeErrorException(int routeNodeId, int edgeId1, int edgeId2) {
		this.routeNodeId = routeNodeId;
		this.edgeId1 = edgeId1;
		this.edgeId2 = edgeId2;
	}
	
	public StatusCode getStatusCode(){
		return StatusCode.ROUTING_EDGE_ERROR;
	}
	
	@Override
	public String getMessage(){
		return "Node not corresponding to id\n" +
				"nodeId: " + routeNodeId + ", and edgeId1: " + edgeId1 + ", edgeId2: " + edgeId2;
		
	}
}
