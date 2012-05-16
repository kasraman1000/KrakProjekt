package views;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;

import models.Region;
import controllers.Controller;
import errorHandling.*;

/**
 * The JavaScript connector, accepting requests from the browser client
 */
public class JSConnector {
	/**
	 * Constructor that makes the class ready for a request
	 */
	public JSConnector() throws ServerRuntimeException, 
								ServerStartupException{
		ServerSocket ss = null;
		try {
			//The server will listen for requests at port 8080
			ss = new ServerSocket(8080);
		} catch (IOException e) {
			throw new ConnectorSocketException(e);
		}

		listenForBrowserRequest(ss);
	}

	/**
	 * This method listens for a request from the browser
	 * 
	 * @param ss the ServerSocket object that creates the connection
	 * @throws IOException 
	 */
	private void listenForBrowserRequest(ServerSocket ss) throws ServerRuntimeException {
		Socket s = null;
		while(true){
			try{
				s = ss.accept();
			}
			catch(IOException e){
				throw new ConnectorIOException(e);
			}
			handleRequest(s);
		}
	}
	
	/**
	 * Converts the request to data types and calls for a xml-String from the Controller
	 * 
	 * @param s The socket to handle the request from
	 * @throws ServerRuntimeException 
	 */
	private void handleRequest(Socket s) throws ServerRuntimeException {
		BufferedReader input;
		HashMap<String,String> parameters = null;
		try {
			input = new BufferedReader(new InputStreamReader(s.getInputStream()));
			parameters = readParameters(input.readLine());
		} catch (IOException e) {
			throw new ConnectorIOException(e);
		}

			Double x1 = Double.valueOf(parameters.get("x1"));
			Double y1 = Double.valueOf(parameters.get("y1"));
			Double x2 = Double.valueOf(parameters.get("x2"));
			Double y2 = Double.valueOf(parameters.get("y2"));
			String from = parameters.get("from");
			String to = parameters.get("to");
			Boolean isDistance = Boolean.valueOf(parameters.get("isDistance"));
			double bufferPercent = Double.valueOf(parameters.get("bufferPercent"));
			
			String response = "";
			
			//if "from" is null then the client is not asking for routeplanning but only mapdata
			if(from == null){
				response = Controller.getXmlString(new Region(x1,y1,x2,y2), bufferPercent);
			}else{
				response = Controller.getRoadAndRoute(from, to, isDistance, bufferPercent);
			}
			
			sendResponseToBrowser(s,response);
		
	}

	/**
	 * Sends to response to the browser through the socket
	 * 
	 * @param s the socket that contains the outputstream
	 * @param response What will be sent to to the browser
	 */
	private void sendResponseToBrowser(Socket s, String response) throws ServerRuntimeException{
		try {
			DataOutputStream output = new DataOutputStream(s.getOutputStream());
			String httpHeader = "HTTP/1.1 200 OK\r\nAccess-Control-Allow-Origin: *\r\n\r\n";
			String httpResponse = httpHeader + response;
			output.write(httpResponse.getBytes());
			output.flush();
			output.close();
			s.close();
		} catch (IOException e) {
			throw new ConnectorIOException(e);
		}
	}

	/**
	 * Takes all the parameters which are passed from the browser and
	 * puts them in a hashmap
	 * 
	 * @param line the first line of the http request
	 * @return returns a hashMap with the parameters
	 */
	private HashMap<String, String> readParameters(String inLine) throws ServerRuntimeException{
		String line = "";
		try{
			line = URLDecoder.decode(inLine, "UTF-8");
		} catch(Exception e) {
			throw new ConnectorDecodingException(e);
		}
		HashMap<String,String> result = new HashMap<String,String>();
		
		//discards everything before the questionmark
		line = line.split("\\?")[1];

		//discards everything after the space and "HTTP"
		line = line.split(" HTTP")[0];
		String[] lines = line.split("&");
		for(String pair : lines){
			String[] pairArray = pair.split("=");
			result.put(pairArray[0], pairArray[1]);
		}
		return result;
	}
	
	/**
	 * Not used at the current moment, but very useful if the project is to be expanded (and for debugging)
	 * It will tell if the request has any parameters.
	 * 
	 * @param line String that may contain requests
	 * @return True if there are any parameters - else false
	 */
	@SuppressWarnings("unused")
	private boolean hasParameters(String line) {
		//search the string and tells if it contains a questionmark
		return line.indexOf("\\?")!=-1;
	}
}
