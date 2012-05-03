package errorHandling;

public abstract class ServerStartupException extends Exception {
	
	private Exception e;
	
	public ServerStartupException(Exception e){
		this.e = e;
	}
	
	abstract StatusCode getStatusCode();
	
	public Exception geException(){
		return e;
	}
	
}
