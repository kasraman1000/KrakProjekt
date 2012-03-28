import java.awt.Point;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * 
 */

/**
 * @author Yndal
 *
 */
public class Controller {
	private DataHelper dataHelper;
	
	
	//TODO Must be changed when JS is up running!!!
	//TODO Not supposed to do all these calculations!!!
	public void launchKrax(){
		dataHelper = DataHelper.getInstance();
		Road[] roadsOriginal;
		double scaleStart = 500;
		HashSet<Road> tempRoadHash = new HashSet<Road>();
		View view = new View();
		view.createFrame();
		System.out.println("Launch not done yet");
		try {
			KDTree kdTree = KDTree.getTree();
			kdTree.initialize("C:\\Users\\Yndal\\Desktop\\Dropbox\\1. årsprojekt - gruppe 1\\krak-data\\kdv_node_unload.txt",
					"C:\\Users\\Yndal\\Desktop\\Dropbox\\1. årsprojekt - gruppe 1\\krak-data\\kdv_unload.txt");
			Road[] allRoads = kdTree.searchRange(kdTree.origo, kdTree.top);
			
//			for (Node n : nodes) {
//				for (Road r : n.getRoads()) {
//					tempRoadHash.add(r);
//				}
//			}

//			Road[] roadsTemp = new Road[tempRoadHash.size()];
//			Iterator<Road> tempRoadIt = tempRoadHash.iterator();
//			for(int index=0; index<tempRoadHash.size(); index++){
//				roadsTemp[index] = tempRoadIt.next();
//			}

			
			roadsOriginal = dataHelper.cleanUpRoads(allRoads, 1);
//			dataHelper = new DataHelper(roadsOriginal);
			view.drawRoads(roadsOriginal);
			
			
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Load all data
		//Find original min and max in DataHelper (partly to storage the information)
		//Define starting scale
		//Create instance of JSConnector
	}
	
	
	public static void newZoomLevel(Point centerOfCurrentView, boolean zoomIn){
//		DataHelper.cleanupRoads(roadsOriginal, isZoomedIn);
		System.out.println("Zoom level changed");
	}
	

}
