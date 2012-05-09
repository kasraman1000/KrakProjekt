package errorHandling;

public class NoSuchRoadnameException extends ClientInputException {
	private String roadname;
	
	public NoSuchRoadnameException(String roadname) {
		this.roadname = roadname;		
	}
	
	public StatusCode getStatusCode(){
		return StatusCode.NO_SUCH_ROADNAME;
	}
	
	@Override
	public String getMessage(){
		String message = "Could not find road: " + roadname;
		return message;
	}
	
}
