package errorHandling;

public class NoSuchAddressException extends ClientInputException {
	
	public NoSuchAddressException() {
	}
	
	public StatusCode getStatusCode(){
		return StatusCode.NO_SUCH_ADDRESS;
	}
}
