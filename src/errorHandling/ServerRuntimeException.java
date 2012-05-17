package errorHandling;

/**
 * A abstract class used for classes to handle a RuntimeException
 * 
 * @author Group 1, B-SWU, 2012E
 */
public abstract class ServerRuntimeException extends Exception {
	
	private Exception e;
	
	public ServerRuntimeException(Exception e){
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
