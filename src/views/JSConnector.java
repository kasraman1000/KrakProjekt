package views;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import controllers.Controller;
import models.Region;

/**
 * The JavaScript connector, accepting requests from the browser client
 */
public class JSConnector {
	/**
	 * Constructor that makes the class ready for a request
	 */
	public JSConnector(){
		try {
			//the parameter in ServerSocket is 80 because that is the default port for localhost
			ServerSocket ss = new ServerSocket(80);
			listenForBrowserRequest(ss);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method listens for a request from the browser and makes a recursive call
	 * when a request happens
	 * 
	 * @param ss the ServerSocket object that creates the connection
	 */
	private void listenForBrowserRequest(ServerSocket ss) {
		try {
			Socket s = ss.accept();
			handleRequest(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		listenForBrowserRequest(ss);

	}
	
	private void handleRequest(Socket s) {
		BufferedReader input;
		try {
			input = new BufferedReader(new InputStreamReader(s.getInputStream()));
			HashMap<String,String> parameters = readParameters(input.readLine());
//			System.out.println("js - handleRequest(): " + parameters.get("x1"));
			Double x1 = Double.valueOf(parameters.get("x1"));
			Double y1 = Double.valueOf(parameters.get("y1"));
			Double x2 = Double.valueOf(parameters.get("x2"));
			Double y2 = Double.valueOf(parameters.get("y2"));
			String response = Controller.getXmlString(new Region(x1,y1,x2,y2));
			sendResponseToBrowser(s,response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param s the socket that contains the outputstream
	 * @param response 
	 */
	private void sendResponseToBrowser(Socket s, String response) {
		try {
			DataOutputStream output = new DataOutputStream(s.getOutputStream());
//			String xmlString = "<g><line x1=\"400\" y1=\"200\" x2=\"0\" y2=\"0\" style=\"stroke:black;stroke-width:5\" /><line x1=\"400\" y1=\"200\" x2=\"0\" y2=\"400\" style=\"stroke:yellow;stroke-width:5\"/></g>";
			String httpHeader = "HTTP/1.1 200 OK\r\nAccess-Control-Allow-Origin: *\r\n\r\n";
			String httpResponse = httpHeader + response;
	//		output.writeUTF(httpResponse);
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
	private HashMap<String, String> readParameters(String line) {
		System.out.println("JSconnector.ReadParameters - line: " + line);
		HashMap<String,String> result = new HashMap<String,String>();
//		if(!hasParameters(line)) return result;
		//discards everything before the questionmark
		line = line.split("\\?")[1];
		//discards everything after the space
		line = line.split(" ")[0];
		String[] lines = line.split("&");
//		System.out.println("JSConnector.readParameters()");
		for(String pair : lines){
			String[] pairArray = pair.split("=");

//			System.out.println(pairArray[0] + ": " + pairArray[1]);
			result.put(pairArray[0], pairArray[1]);
		}
		return result;
	}
	
	private boolean hasParameters(String line) {
		//search the string and tells if it contains a questionmark
		return line.indexOf("\\?")!=-1;
	}
}
