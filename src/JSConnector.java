import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class JSConnector {
	
	private static ServerSocket ss;
	private static Socket s;
	public JSConnector() {
		createSocket();
	}
	private void createSocket() {
		try {
			ss = new ServerSocket(80);
			s = ss.accept();
			BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
			createXMLDocument();
			//readParameters(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void createXMLDocument() {
		int i = 0;
		System.out.println("createXML " + i);
		i++;
		XML xml = new XML();
		xml.createFile(xml.createRoadsForTesting(),"roads.xml");
//			output.writeUTF("<g><line x1=\"400\" y1=\"200\" x2=\"0\" y2=\"0\" style=\"stroke:black;stroke-width:5\" /><line x1=\"400\" y1=\"200\" x2=\"0\" y2=\"400\" style=\"stroke:yellow;stroke-width:5\"/></g>");
		
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
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	        	try {
					s.close();
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	    }, "Shutdown-thread"));
	}

}
