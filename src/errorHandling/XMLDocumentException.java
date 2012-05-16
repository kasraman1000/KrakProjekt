package errorHandling;

/**
 * A class to be thrown if a Document is unable to be made
 * 
 * @author Group 1, B-SWU, 2012E
 */
public class XMLDocumentException extends ServerRuntimeException {
	public XMLDocumentException(Exception e) {
		super(e);
		
	}
	
	@Override
	public StatusCode getStatusCode(){
		return StatusCode.XML_DOCUMENT;
	}
}
