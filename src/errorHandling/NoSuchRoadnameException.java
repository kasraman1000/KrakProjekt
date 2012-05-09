package errorHandling;

public class NoSuchRoadnameException extends ClientInputException {
	public NoSuchRoadnameException() {
		
	}
	
	public StatusCode getStatusCode(){
		return StatusCode.NO_SUCH_ROADNAME;
	}
}
