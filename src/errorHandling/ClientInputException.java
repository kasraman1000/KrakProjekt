package errorHandling;

public class ClientInputException extends Exception {
	private final String nameOfClass;
	private final Exception e;
	
	public ClientInputException(Exception e, String nameOfClass){
		this.e = e;
		this.nameOfClass = nameOfClass;
	}
	public String getNameOfClass() {
		return nameOfClass;
	}
	public Exception getException() {
		return e;
	}
	
}