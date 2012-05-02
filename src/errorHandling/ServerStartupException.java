package errorHandling;

public class ServerStartupException extends Exception {
private final String nameOfClass;
private final Exception e;
	public ServerStartupException(Exception e, String nameOfClass){
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
