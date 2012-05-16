package errorHandling;

public class ConnectorDecodingException extends ServerRuntimeException {
	public ConnectorDecodingException(Exception e) {
		super(e);
		
	}
	
	public StatusCode getStatusCode(){
		return StatusCode.CONNECTOR_DECODING;
	}
}
