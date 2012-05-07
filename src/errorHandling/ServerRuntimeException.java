package errorHandling;

public abstract class ServerRuntimeException extends Exception {
	
	private Exception e;
	
	public ServerRuntimeException(Exception e){
		this.e = e;
	}
	
	abstract StatusCode getStatusCode();
	
	public Exception geException(){
		return e;
	}
	
}
