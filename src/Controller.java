import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
		
		controller.startUpFromKrakData("kdv_node_unload.txt",	"kdv_unload.txt");
//		controller.buildFileFromKrakData("kdv_node_unload.txt",	"kdv_unload.txt", "C:\\Users\\Yndal\\Desktop\\OutputOfKDTree.kdt");
//		controller.startUpFromLocalFile("C:\\Users\\Yndal\\Desktop\\OutputOfKDTree.kdt");
	}
	
	
	public Controller(){
		kdTree = KDTree.getTree();
		dataHelper = new DataHelper();
		xml = new XML();
	}
	
	public void startUpFromKrakData(String nodePath, String roadPath){
		System.out.println("Initializing...");
		try {
			kdTree.initialize(nodePath, roadPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("System ready...");
		jsConnector = new JSConnector(this);
	}
	
	
	public void buildFileFromKrakData(String nodePath, String roadPath, String  saveFileName){
		System.out.println("Building file... Please wait.");
		try {
			System.out.println("Building KDTree...");
			kdTree.initialize(nodePath, roadPath);
			/*
			kdTree.initialize("..\\kdv_node_unload.txt",
					"..\\kdv_unload.txt");
		*/			
			System.out.println("KDtree build.");
			System.out.println("Creating file...");
			FileOutputStream fileOutput = new FileOutputStream(saveFileName);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOutput);
			objectOut.writeObject(kdTree);
			objectOut.close();
			fileOutput.close();
			System.out.println("File created!");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("File build.");
	}
	
	public void startUpFromLocalFile(String fileName){
		System.out.println("Starting system from local file");
		try {
			FileInputStream fileInput = new FileInputStream(fileName);
			ObjectInputStream objectIn = new ObjectInputStream(fileInput);
			kdTree = (KDTree) objectIn.readObject();
			objectIn.close();
			fileInput.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("System ready...");
		jsConnector = new JSConnector(this);
	}
	

	
	public static String getXmlString(Region region){
		Road[] roads = kdTree.searchRange(region);
		String s = "";
		try {
			xml.createFile(roads, "C:\\Users\\Yndal\\Desktop\\TestingOfXml.xml");
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
	
}
