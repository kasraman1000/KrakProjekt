package errorHandling;

public abstract class ClientInputException extends Exception {
	
	abstract StatusCode getStatusCode();
	
}