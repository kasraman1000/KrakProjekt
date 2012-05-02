package errorHandling;

public class ErrorHandler {

public static void handleClientInputException(ClientInputException e){
	
}

public static void handleServerStartupException(ServerStartupException e){
	System.out.println("File not found in class: " + e.getNameOfClass() + ", exception: " + e.getException().getMessage());
	System.exit(0);
}

//public static void handleServerRuntimeException(ServerRuntimeException e){
//	Exception exception = e.getException();
//}
}
