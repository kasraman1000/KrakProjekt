import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class JSConnector {
	
	public JSConnector() {
		createSocket();
	}
	private void createSocket() {
		try {
			ServerSocket ss = new ServerSocket(80);
			Socket s = ss.accept();
			BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
			DataOutputStream output = new DataOutputStream(s.getOutputStream());
			output.writeUTF("<g><line x1=\"400\" y1=\"200\" x2=\"0\" y2=\"0\" style=\"stroke:black;stroke-width:5\" /><line x1=\"400\" y1=\"200\" x2=\"0\" y2=\"400\" style=\"stroke:yellow;stroke-width:5\"/></g>");
			output.flush();
			output.close();
			s.close();
			ss.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		new JSConnector();
	}

}
