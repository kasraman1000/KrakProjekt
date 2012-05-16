package errorHandling;

/**
 * A class to be thrown if a Document is unable to be transformed
 * into for instance a String or File 
 * 
 * @author Group 1, B-SWU, 2012E
 */
public class XMLTransformerException extends ServerRuntimeException {
	public XMLTransformerException(Exception e) {
		super(e);
	}
	
	@Override
	public StatusCode getStatusCode(){
		return StatusCode.XML_TRANSFORMER;
	}
}
