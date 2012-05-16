package errorHandling;

/**
 * A class to be thrown if address is of an invalid format
 * 
 * @author Group 1, B-SWU, 2012E
 */
public class AddressInputFormatException extends ClientInputException {
	@Override
	public StatusCode getStatusCode(){
		return StatusCode.NO_SUCH_ADDRESS;
	}
}
