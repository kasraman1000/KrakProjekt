package errorHandling;

public class XMLTransformerException extends ServerRuntimeException {
	public XMLTransformerException(Exception e) {
		super(e);
		
	}
	
	public StatusCode getStatusCode(){
		return StatusCode.XML_TRANSFORMER;
	}
}
