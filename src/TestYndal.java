import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import models.Loader;
import routing.KrakEdgeWeightedDigraph;
import controllers.Controller;

/**
 * @author Yndal
 *
 */
public class TestYndal {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		new Controller();
		
		double startWrite = System.nanoTime();
//		FileOutputStream graphOut = new FileOutputStream("graph.ser");
		OutputStream file = new FileOutputStream("graph.ser");
		OutputStream buffer = new BufferedOutputStream(file); 
		ObjectOutput objectOut = new ObjectOutputStream(buffer);
//		OutputStream buffOut = new BufferedOutputStream(objectOut);
		objectOut.writeObject(Loader.getGraph());
		objectOut.close();
//		buffOut.w.witeObject(Loader.getGraph());
//		buffOut.close();
//		graphOut.close();
		double endWrite = System.nanoTime();
		
		System.out.println("Time taken to write file: " + (endWrite-startWrite)/1000000000);
		
		
		double startRead = System.nanoTime();
		InputStream fileIn = new FileInputStream("graph.ser");
		InputStream bufferIn = new BufferedInputStream(fileIn);
		ObjectInput input = new ObjectInputStream(bufferIn);
		
		KrakEdgeWeightedDigraph newGraph = (KrakEdgeWeightedDigraph) input.readObject();
		
		input.close();
		
		
//		FileInputStream graphIn = new FileInputStream("graph.ser");
//		ObjectInputStream objectIn = new ObjectInputStream(graphIn);
//		KrakEdgeWeightedDigraph newGraph = (KrakEdgeWeightedDigraph) objectIn.readObject();
//		objectIn.close();
//		graphIn.close();
		double endRead = System.nanoTime();
		
		System.out.println("Time taken to load file: " + (endRead-startRead)/1000000000);
		
		
//		FileOutputStream(Loader.getGraph())
//		
//		BufferedOutputStream(new ObjectOutputStream(Loader.getGraph()))
//		
//		
//		
//		double start = System.nanoTime();
//		
//		
//		KrakEdgeWeightedDigraph graph = Loader.getGraph();
//		
//		
//		
//		int firstNumber = 0;
//		int lastNumber = 0;
//		for(KrakEdge e : graph.edges()){
//			firstNumber++;
//		}
//		
//		graph.addEdge(new KrakEdge(1, 3, "Test Edge1", 0.900, 0.218, new double[]{5,5}, new double[]{8,8}, 4600, 4600, 0, 20, 1, 21));
//		graph.addEdge(new KrakEdge(2, 5, "Test Edge2", 0.900, 0.218, new double[]{10,10}, new double[]{20,20}, 4600, 4600, 0, 20, 1, 21));
//		
//		graph = KrakEdgeWeightedDigraph.class.
//		for(KrakEdge e : Loader.getGraph().edges()){
//			lastNumber++;
//		}
		
		
//		System.out.println("firstNumber: " + firstNumber + ", lastNumber: " + lastNumber);
		
		
		
		
		
//		KrakEdgeWeightedDigraph graph = new KrakEdgeWeightedDigraph(Loader.getGraph().V());
//		for(KrakEdge edge : Loader.getGraph().edges()){
//			graph.addEdge(edge);
//		}
		
		
		

		
		
		
		
		double end = System.nanoTime();
		
//		System.out.println("Timen taken to create new graph: " + (end-start)); 
//		System.exit(0);
		
		
		
		
		
	}
}
