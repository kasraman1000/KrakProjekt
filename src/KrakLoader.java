import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

class KrakLoader {

	public static ArrayList<Node> load(String nodePath, String edgePath) throws IOException{
		HashMap<Integer, Node> map = new HashMap<Integer, Node>(1000000);
		//reads the nodes file
		File file = new File(nodePath);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String curLine;
		// first line is irrelevant
		reader.readLine();
		//variables for finding extremes
		double biggestX = 0;
		double biggestY = 0;
		double smallestX = 10000000;
		double smallestY = 10000000;
		
		
		//Adding all nodes to result HashMap with their ID as keys
		while((curLine = reader.readLine()) != null){
			String[] lineArray = curLine.split(",");
			double[] coords = {Double.valueOf(lineArray[3]), Double.valueOf(lineArray[4])};
			if(Double.valueOf(lineArray[3]) > biggestX)
			{
				biggestX = Double.valueOf(lineArray[3]);
			}
			if(Double.valueOf(lineArray[4]) > biggestY)
			{
				biggestY = Double.valueOf(lineArray[4]);
			}
			if(Double.valueOf(lineArray[3]) < smallestX)
			{
				smallestX = Double.valueOf(lineArray[3]);
			}
			if(Double.valueOf(lineArray[4]) < smallestY)
			{
				smallestY = Double.valueOf(lineArray[4]);
			}
			
			Node node = new Node(coords);
			map.put(Integer.valueOf(lineArray[2]), node);
		}
		double[] top = {biggestX, biggestY};
		double[] origo = {smallestX, smallestY};
		Road.setTop(top);
		Road.setOrigo(origo);

		//reads the edgeList 
		File file2 = new File(edgePath);
		BufferedReader reader2 = new BufferedReader(new FileReader(file2));
		String curLine2;
	
		// first line is irrelevant
		reader2.readLine();
		
		for(Node node : map.values()){
			node.coords[0] -= smallestX; 
			node.coords[1] = node.coords[1]*(-1) + biggestY;
			if(node.coords[1] > 1000000)
			{
				System.out.println("OMGWTFBBQ");
			}
		}
		
		double[] newTop = {biggestX-smallestX, biggestY-smallestY};
		double[] newOrigo = {0, 0};
		Road.setTop(newTop);
		Road.setOrigo(newOrigo);
		
		//Creating roads and adding references from nodes to roads. Coordinates from nodes is added to roads.
		while((curLine2 = reader2.readLine()) != null){
			String[] lineArray2 = curLine2.split(",");
			Node node1 = map.get(Integer.valueOf(lineArray2[0]));
			Node node2 = map.get(Integer.valueOf(lineArray2[1]));


			Road road = new Road(
					node1.coords[0],
					node1.coords [1],
					node2.coords [0],
					node2.coords [1],
					Integer.valueOf(lineArray2[5]),
					lineArray2[6]);
			node1.addRoad(road);
			node2.addRoad(road);
		}

		ArrayList<Node> result = new ArrayList<Node>(1000000);
		result.addAll(map.values());
		
		return result;
	}
	
	public static void main(String[] args) {
		try {
			Collection<Node> nodes = KrakLoader.load("TestNodes1000.txt", "TestEdges1000.txt");
			System.out.println("!!");
			
			for (Node n : nodes) {
				for (Road r : n.getRoads()) {
					System.out.println(r);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
