package errorHandling;

/**
 * An abstract class to be used if the server is unable to start up
 * 
 * @author Group 1, B-SWU, 2012E
 */
public abstract class ServerStartupException extends Exception {
	
	private Exception e;
	
	public ServerStartupException(Exception e){
		this.e = e;
	}
	
	/**
	 * Get the status code for this exception
	 * @return An enum containing code and description
	 */
	abstract StatusCode getStatusCode();
	
	public Exception getException(){
		return e;
	}
	
}
