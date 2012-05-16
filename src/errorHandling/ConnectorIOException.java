package errorHandling;

public class ConnectorIOException extends ServerRuntimeException {
	public ConnectorIOException(Exception e) {
		super(e);
		
	}
	
	public StatusCode getStatusCode(){
		return StatusCode.CONNECTOR_IO;
	}
}
