/**
 * 
 */
package models;

/**
 * @author Yndal
 *
 */
public enum StatusCode {
	ALL_WORKING("Everything is running fine"),
	IO_ERROR("There was an error with the I/O"),
	SERVER_DOWN("Server seems to be down"),
	PARSE_ERROR("There was an error during the parsing of the XML String");
	
	private final String description;
	
	
	StatusCode(String description){
		this.description = description;
		
	}
	
	
	public String getDescription(){
		return description;
	}

}
