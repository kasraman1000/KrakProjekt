package errorHandling;

/**
 * A class to be thrown when the address given by the client does not exists
 * 
 * @author Group 1, B-SWU, 2012E
 */
public class NoSuchAddressException extends ClientInputException {
	@Override
	public StatusCode getStatusCode(){
		return StatusCode.NO_SUCH_ADDRESS;
	}
}
