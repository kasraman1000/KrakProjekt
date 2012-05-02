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
	IO_ERROR(1, "There was an error with the I/O"),
	SERVER_DOWN(2, "Server seems to be down"),
	PARSE_ERROR(3, "There was an error during the parsing of the XML String");
	
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
