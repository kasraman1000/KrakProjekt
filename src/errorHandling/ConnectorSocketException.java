package errorHandling;

public class ConnectorSocketException extends ServerStartupException {
	
	public ConnectorSocketException(Exception e) {
		super(e);
	}

	public StatusCode getStatusCode(){
		return StatusCode.CONNECTOR_SOCKET;
	}

}
