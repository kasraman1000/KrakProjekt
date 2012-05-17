package errorHandling;

/**
 * This class is to decide what will happen if an error occurs 
 * 
 * @author Group 1, B-SWU, 2012E
 */
public class ErrorHandler {
	
	/**
	 * What to do if a ClientException is thrown
	 * @param e The original exception
	 * @return The status code for this exception
	 */
	public static StatusCode handleClientInputException(ClientInputException e){
		System.err.println("Illigal input " + e.getMessage());
		return e.getStatusCode();
	}
	
	/**
	 * What to do if a ServerStartupException is thrown
	 * @param e The original exception
	 * @return The status code for this exception
	 */
	public static void handleServerStartupException(ServerStartupException e){
		System.err.println(e.getStatusCode().getDescription());
		System.exit(0);
	}
	
	/**
	 * What to do if a ServerRuntimeException is thrown
	 * @param e The original exception
	 * @return The status code for this exception
	 */
	public static void handleServerRuntimeException(ServerRuntimeException e){
		System.err.println(e.getStatusCode().getDescription());
	}
}