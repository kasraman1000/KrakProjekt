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

/**
 * The JavaScript connector, accepting requests from the browser client
 */
public class JSConnector {
	/**
	 * Constructor that makes the class ready for a request
	 */
	public JSConnector(){
		try {
			//The server will listen for requests at port 8080
			ServerSocket ss = new ServerSocket(8080);
			listenForBrowserRequest(ss);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method listens for a request from the browser
	 * 
	 * @param ss the ServerSocket object that creates the connection
	 * @throws IOException 
	 */
	private void listenForBrowserRequest(ServerSocket ss) throws IOException {
		Socket s;
		while(true){
			s = ss.accept();
			handleRequest(s);
		}
	}
	
	/**
	 * Converst the request to data types and calls for a xml-String from the Controller
	 * 
	 * @param s The socket to handle the request from
	 */
	private void handleRequest(Socket s) {
		BufferedReader input;
		try {
			input = new BufferedReader(new InputStreamReader(s.getInputStream()));
			HashMap<String,String> parameters = readParameters(input.readLine());

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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends to response to the browser through the socket
	 * 
	 * @param s the socket that contains the outputstream
	 * @param response What will be sent to to the browser
	 */
	private void sendResponseToBrowser(Socket s, String response) {
		try {
			DataOutputStream output = new DataOutputStream(s.getOutputStream());
			String httpHeader = "HTTP/1.1 200 OK\r\nAccess-Control-Allow-Origin: *\r\n\r\n";
			String httpResponse = httpHeader + response;
			output.write(httpResponse.getBytes());
			output.flush();
			output.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Takes all the parameters which are passed from the browser and
	 * puts them in a hashmap
	 * 
	 * @param line the first line of the http request
	 * @return returns a hashMap with the parameters
	 */
	private HashMap<String, String> readParameters(String inLine) {
		String line = "";
		try{
			line = URLDecoder.decode(inLine, "UTF-8");
		} catch(Exception e) {
			 System.err.println("JSConnector.readParameters() - linie 117 EXCEPTION  " + e);
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
	
	private boolean hasParameters(String line) {
		//search the string and tells if it contains a questionmark
		return line.indexOf("\\?")!=-1;
	}
}
