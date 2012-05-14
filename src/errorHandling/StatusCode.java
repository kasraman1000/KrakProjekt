/**
 * 
 */
package errorHandling;

/**
 * @author Yndal
 *
 */
public enum StatusCode {
	ALL_WORKING(0, "Everything is running fine"),
	NO_SUCH_ROADNAME(1, "This roadname does not exist"),
	SERVER_DOWN(2, "Server seems to be down"),
	PARSE_ERROR(3, "There was an error during the parsing of the XML String"),
	NO_SUCH_ADDRESS(4, "Couldn't find this address"),
	IO_ERROR(5, "There was an error with the I/O"),
	LOADER_FILE_NOT_FOUND(6, "The loader couldn't find the data files"),
	XML_DOCUMENT(7, "The XML Document was not created properbly"),
	XML_TRANSFORMER(8, "The transformation to a XML-string went bad");
	
	private final String description;
	private final int codeNumber;
	
	
	StatusCode(int codeNumber, String description){
		this.codeNumber = codeNumber;
		this.description = description;
	}
	
	public int getCodeNumber(){
		return codeNumber;
	}
	
	public String getDescription(){
		return description;
	}

}
