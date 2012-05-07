package errorHandling;

public class ErrorHandler {

public static StatusCode handleClientInputException(ClientInputException e){
	return e.getStatusCode();
}

public static void handleServerStartupException(ServerStartupException e){
	System.out.println(e.getStatusCode().getDescription());
	System.exit(0);
}

public static void handleServerRuntimeException(ServerRuntimeException e){
	System.out.println(e.getStatusCode().getDescription());
}
}
