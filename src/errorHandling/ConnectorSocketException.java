package errorHandling;

/**
 * This class is to be thrown if unable to get a connection up running
 * 
 * @author Group 1, B-SWU, 2012E
 */
public class ConnectorSocketException extends ServerStartupException {
	public ConnectorSocketException(Exception e) {
		super(e);
	}
	
	@Override
	public StatusCode getStatusCode(){
		return StatusCode.CONNECTOR_SOCKET;
	}

}
