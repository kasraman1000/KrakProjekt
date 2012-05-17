package errorHandling;

/**
 * An Enum to contain all the different status codes of this program.
 * This will describe what went wrong and give the possibility to
 * sent an error code to the client or a local log. 
 * 
 * @author Group 1, B-SWU, 2012E
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
	XML_TRANSFORMER(8, "The transformation to a XML-string went bad"),
	ROUTING_EDGE_ERROR(9,"The route does not start or end at the desired destination"),
	CONNECTOR_IO(10,"Something went wrong when trying to send or recieve data from the browser"),
	CONNECTOR_SOCKET(11,"Could not make the socket for the connection"),
	CONNECTOR_DECODING(12,"Couldn't decode the input from the browser");
	
	private final String description;
	private final int codeNumber;
	
	/**
	 * The Constructor
	 * 
	 * @param codeNumber Code number for the enum
	 * @param description Description for the enum
	 */
	StatusCode(int codeNumber, String description){
		this.codeNumber = codeNumber;
		this.description = description;
	}
	
	/**
	 * Get the code number
	 * @return Code number
	 */
	public int getCodeNumber(){
		return codeNumber;
	}
	
	/**
	 * Get the description
	 * @return description
	 */
	public String getDescription(){
		return description;
	}
}