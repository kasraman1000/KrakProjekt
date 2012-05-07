package errorHandling;

public class LoaderFileNotFoundException extends ServerStartupException {
	
	public LoaderFileNotFoundException(Exception e) {
		super(e);
	}

	public StatusCode getStatusCode(){
		return StatusCode.LOADER_FILE_NOT_FOUND;
	}

}
