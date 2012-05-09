package errorHandling;

public class AddressInputFormatException extends ClientInputException {
	public AddressInputFormatException() {
		
	}
	
	public StatusCode getStatusCode(){
		return StatusCode.NO_SUCH_ADDRESS;
	}
}
