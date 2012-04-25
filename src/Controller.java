import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import routing.*;

/**
 * 
 */

/**
 * @author Yndal
 *
 */
public class Controller {
	private static XML xml;
	private static KDTree kdTree;
	private static JSConnector jsConnector;
	
	
	public static void main(String[] args) {
		Controller controller = new Controller();
		
//		HashMap<Integer, HashMap<String,>>
	}
	
	
	public Controller(){
		double start = System.nanoTime();
		System.out.println("System startup - please wait...");
		kdTree = KDTree.getTree();
		try {
			Loader.load("kdv_node_unload.txt","kdv_unload.txt");
//			Loader.load("TestNodes.txt", "TestEdges.txt");
			kdTree.initialize(Loader.getNodesForKDTree());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xml = new XML();
		double end = System.nanoTime();
		System.out.println("System up running... (In " + (end-start)/1000000000 + " seconds)");
		jsConnector = new JSConnector(this);
	}
	 

	
	
	public static String getXmlString(Region region){
		Road[] roads = RoadSelector.searchRange(region);
		String s = "";
		RoadStatus.setScale(RoadSelector.getLastZoomLevel());
		try {
//			xml.createFile(roads, "C:\\Users\\Mark\\Desktop\\TestingOfXml.xml");
			s = xml.createString(roads);
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
	
	public void getRoute(int start, int target, boolean isLengthWeighted){
		DijkstraSP dij = new DijkstraSP(Loader.getGraph());
		dij.findRoute(start, target, isLengthWeighted);
		
		
		
	}

}
