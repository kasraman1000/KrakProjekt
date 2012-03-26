import java.util.Set;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Stack;
import java.io.IOException;

public class KrakGraphKlient {
    
    public static void main(String[] args) { 
        
        try {
            // Read in all the data from the big txt's
            KrakEdgeGraph keg = KrakLoader.load();
            // Remove all nodes with two edges only
            clean(keg);
            // Reassign ID's so they start from 1
            keg = reindex(keg);
            

            
            Bag<Edge> edges = keg.edges();
            String[] result = new String[edges.size() + 1];
            
            result[0] = keg.getSize() + " " + edges.size();
            
            int count = 1;
            for (Edge e : edges) {
                result[count] = 
                    e.either() + " " + 
                    e.other(e.either()) + " " + 
                    e.getLength().doubleValue();
                count++;
            }
            
            // Write result to output.txt
            TextIO.write(result);
            System.out.println("Succes!!");
        }
        catch (IOException e) {
            System.out.println("ERROR READING FROM FILE");
        }
    }
    
    /**
     * Runs though a KrakEdgeGraphs HashMap for all nodes that only have two edges,
     * and removes those.
     */
    public static void clean(KrakEdgeGraph k) {
        // Keeps trak of all keys of nodes to remove
        Stack<Integer> keys = new Stack<Integer>();
        
        // get a collection of all nodes
        Set<Map.Entry<Integer,Node>> nodes = k.getNodes();
        
        System.out.println("COUNTING ALL NODES WITH ONLY TWO EDGES...");
        
        // iterate though all nodes
        for (Map.Entry<Integer,Node> n : nodes) {
            // If only two edges are associated with this node, add the key to the stack
            if (n.getValue().getEdges().size() == 2 && !n.getValue().containsCircle()) {  
                keys.push(n.getKey()); 
            }
            
        }   
        
        System.out.println("REMOVING ALL NODES WITH ONLY TWO EDGES...");
        // combine all nodes which keys are in this list
        while (!keys.empty()) {
            k.combine(keys.pop());
        } 
    }
    
    /**
     * Reassigns all keys for all nodes so that keys start from 1
     */
    public static KrakEdgeGraph reindex(KrakEdgeGraph k) {
        System.out.println("REINDEXING ALL NODES...");
        
        // Make a new KrakEdgeGraph
        KrakEdgeGraph newK = new KrakEdgeGraph();
        // getting all the nodes in the hashmap
        Set<Map.Entry<Integer,Node>> nodes = k.getNodes();
        // loop invariant, for Hashmap keys
        int key = 1;
        
        // Run thorugh all nodes
        for (Map.Entry<Integer,Node> n : nodes) {
            Node node = n.getValue();
            int oldKey = n.getKey();
            
            // Add the node to the new, with a new key
            newK.addNode(node, key);
            
            // replace edges id-reference with the new key
            for (Edge e : node.getEdges()) {
                e.replace(oldKey, key);
            }
            
            key++;
            
        }   
        
        // recalculate edges
        newK.calculateNumberOfEdges();
        
        return newK;
    }
    
    
}
