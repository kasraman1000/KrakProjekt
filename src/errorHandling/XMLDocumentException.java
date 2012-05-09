package errorHandling;

public class XMLDocumentException extends ServerRuntimeException {
	public XMLDocumentException(Exception e) {
		super(e);
		
	}
	
	public StatusCode getStatusCode(){
		return StatusCode.XML_DOCUMENT;
	}
}
