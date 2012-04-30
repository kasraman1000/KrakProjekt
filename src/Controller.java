import java.io.IOException;

/**
 * 
 */

/**
 * @author Yndal
 *
 */
public class Controller {
	private static XML xml;
	
	private static JSConnector jsConnector;
	private static RoadSelector roadSelector;
	
	
	public static void main(String[] args) {
		Controller controller = new Controller();
	}
	
	
	public Controller(){
		System.out.println("System startup - please wait...");

		roadSelector = new RoadSelector();
		xml = new XML();
		
		System.out.println("System up running...");
		jsConnector = new JSConnector(this);
	}
	
	public static String getXmlString(Region region){
		Road[] roads = roadSelector.search(region);
		String s = "";
		
		try {
//			xml.createFile(roads, "C:\\Users\\Mark\\Desktop\\TestingOfXml.xml");
			s = xml.createString(roads);
		} catch (Exception e) {
			System.out.println("Unable to create XML-string!");
			e.printStackTrace();
		}
		return s;
	}
	
	
	public static double getMaxXOriginal(){
		return KDTree.getTree().top[0];
	}
	
	public static double getMaxYOriginal(){
		return KDTree.getTree().top[1];
	}

}
