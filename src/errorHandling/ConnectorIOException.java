package errorHandling;

/**
 * A class thrown if we experience a IOException
 * 
 * @author Group 1, B-SWU, 2012E
 */
public class ConnectorIOException extends ServerRuntimeException {
	public ConnectorIOException(Exception e) {
		super(e);
		
	}
	
	@Override
	public StatusCode getStatusCode(){
		return StatusCode.CONNECTOR_IO;
	}
}
