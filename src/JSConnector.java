import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class JSConnector {
	
	public JSConnector() {
		createSocket();
	}
	private void createSocket() {
		try {
			ServerSocket ss = new ServerSocket(80);
			Socket s = ss.accept();
			BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
			readParameters(input);
			DataOutputStream output = new DataOutputStream(s.getOutputStream());
			output.writeUTF("<g><line x1=\"400\" y1=\"200\" x2=\"0\" y2=\"0\" style=\"stroke:black;stroke-width:5\" /><line x1=\"400\" y1=\"200\" x2=\"0\" y2=\"400\" style=\"stroke:yellow;stroke-width:5\"/></g>");
//			output.flush();
			output.close();
			s.close();
			ss.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Takes all the parameters which are passed from the browser and
	 * puts them in a hashmap
	 * 
	 * @param input
	 */
	private void readParameters(BufferedReader input) {
		HashMap<String,String> urlParameters = new HashMap<String,String>();
		try {
			String line = input.readLine();
			//discards everything before the questionmark
			line = line.split("\\?")[1];
			//discards everything after the space
			line = line.split(" ")[0];
			String[] lines = line.split("&");
			for(String pair : lines){
				String[] pairArray = pair.split("=");
				urlParameters.put(pairArray[0], pairArray[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		new JSConnector();
	}

}
