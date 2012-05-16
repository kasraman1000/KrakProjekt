package errorHandling;

/**
 * An abstract class to be used when the client gives an invalid input
 * 
 * @author Group 1, B-SWU, 2012E
 */
public abstract class ClientInputException extends Exception {
	
	/**
	 * Get the status code for this exception
	 * @return An enum containing code and description
	 */
	abstract StatusCode getStatusCode();
	
}