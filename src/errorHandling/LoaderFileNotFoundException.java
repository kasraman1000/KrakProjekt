package errorHandling;

/**
 * This class is to be thrown if the Loader is unable to load the files
 * 
 * @author Group 1, B-SWU, 2012E
 */
public class LoaderFileNotFoundException extends ServerStartupException {
	
	public LoaderFileNotFoundException(Exception e) {
		super(e);
	}

	@Override
	public StatusCode getStatusCode(){
		return StatusCode.LOADER_FILE_NOT_FOUND;
	}

}
