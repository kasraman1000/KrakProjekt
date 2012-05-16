package errorHandling;

/**
 * A class to be thrown if the road name given by the client does not exists
 * 
 * @author Group 1, B-SWU, 2012E
 */
public class NoSuchRoadnameException extends ClientInputException {
	private String roadname;
	
	public NoSuchRoadnameException(String roadname) {
		this.roadname = roadname;		
	}
	
	@Override
	public StatusCode getStatusCode(){
		return StatusCode.NO_SUCH_ROADNAME;
	}
	
	@Override
	public String getMessage(){
		String message = "Could not find road: " + roadname;
		return message;
	}
	
}
