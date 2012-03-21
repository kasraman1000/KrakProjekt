import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

class KrakLoader {

	public static Collection<Node> load(String nodePath, String edgePath) throws IOException{
		HashMap<Integer, Node> result = new HashMap<Integer, Node>();
		//reads the nodes file
		File file = new File(nodePath);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String curLine;
		// first line is irrelevant
		reader.readLine();

		//Adding all nodes to result HashMap with their ID as keys
		while((curLine = reader.readLine()) != null){
			String[] lineArray = curLine.split(",");
			double[] coords = {Double.valueOf(lineArray[3]), Double.valueOf(lineArray[4])};
			Node node = new Node(coords);
			result.put(Integer.valueOf(lineArray[2]), node);
		}

		//reads the edgeList 
		File file2 = new File(edgePath);
		BufferedReader reader2 = new BufferedReader(new FileReader(file2));
		String curLine2;
		// first line is irrelevant
		reader2.readLine();

		//Creating roads and adding references from nodes to roads. Coordinates from nodes is added to roads.
		while((curLine2 = reader2.readLine()) != null){
			String[] lineArray2 = curLine2.split(",");
			Node node1 = result.get(Integer.valueOf(lineArray2[0]));
			Node node2 = result.get(Integer.valueOf(lineArray2[1]));


			Road road = new Road(
					node1.getCoord(0),
					node1.getCoord(1),
					node2.getCoord(0),
					node2.getCoord(1),
					Integer.valueOf(lineArray2[5]),
					lineArray2[6]);

			node1.addRoad(road);
			node2.addRoad(road);
		}

		return result.values();
	}
	public static void main(String[] args) {
		try {
			Collection<Node> nodes =KrakLoader.load("C:\\Users\\DE\\Dropbox\\1. årsprojekt - gruppe 1\\krak-data\\kdv_node_unload.txt", 
					"C:\\Users\\DE\\Dropbox\\1. årsprojekt - gruppe 1\\krak-data\\kdv_unload.txt");
			
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