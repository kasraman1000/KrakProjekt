package errorHandling;

/**
 * A 
 * 
 * @author Group 1, B-SWU, 2012E
 */
public class ConnectorDecodingException extends ServerRuntimeException {
	public ConnectorDecodingException(Exception e) {
		super(e);
	}
	
	@Override
	public StatusCode getStatusCode(){
		return StatusCode.CONNECTOR_DECODING;
	}
}
