import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

/**
 * 
 */

/**
 * @author Yndal
 *
 */
public class Controller {
	private DataHelper dataHelper;
	private static XML xml;
	private static KDTree kdTree;
	private static JSConnector jsConnector;
	
	
	public static void main(String[] args) {
		Controller controller = new Controller();
	}
	
	
	public Controller(){
		System.out.println("System startup - please wait...");
		kdTree = KDTree.getTree();
		try {
/*
			kdTree.initialize("..\\kdv_node_unload.txt",
					"..\\kdv_unload.txt");
		*/			
			kdTree.initialize("kdv_node_unload.txt","kdv_unload.txt");
			System.out.println("PATH READ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataHelper = new DataHelper(kdTree);
		xml = new XML();
		System.out.println("System up running...");
		jsConnector = new JSConnector(this);
	}
	
	

	
	public String getXmlString(Region region){
		Road[] roads = kdTree.searchRange(region);
		String s = "";
		try {
			xml.createFile(roads, "C:\\Users\\Mark\\Desktop\\TestingOfXml.xml");
			s = xml.createString(roads);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	
//	public Road[] getRoadsFromString(String xmlString, int[] roadTypesToExtract){
//		//Pre check
////TODO	if(xmlString.length() == 0) throw SomeKindOfException
//		//If roadTypes are left empty - all roads will be returned (from type 0-99)
//		if(roadTypesToExtract.length == 0){
//			final int HIGHEST_ROAD_TYPE = 99;
//			roadTypesToExtract = new int[HIGHEST_ROAD_TYPE+1];
//			for(int roadTypeIndex=0; roadTypeIndex<=HIGHEST_ROAD_TYPE; roadTypeIndex++){
//				roadTypesToExtract[roadTypeIndex] = roadTypeIndex;
//			}
//		}
//				
//		
//		Road[] foundRoads = new Road[0];
//		try {
//			foundRoads = xml.getRoadsFromString(xmlString, roadTypesToExtract);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
////TODO	if(foundRoads.length == 0){
////			doSomethingClever
////		} else {
//		return foundRoads;
//		
//	}
	
	
	public static double getMaxXOriginal(){
		return KDTree.getTree().top[0];
	}
	
	public static double getMaxYOriginal(){
		return KDTree.getTree().top[1];
	}
	
	
	
	private String getErrorString(int errorCode){
		return null;
	}
}
