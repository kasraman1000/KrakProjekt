import java.math.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;

public class KrakEdgeGraph{
    private int numberOfEdges;
    private HashMap<Integer,Node> nodes;
    private BigDecimal length; 
    
    public KrakEdgeGraph(){
        nodes = new HashMap<Integer,Node>();
        numberOfEdges = 0;
    }
    
    /**
     * Adds a node to the HashMap at the specified key
     */
    public void addNode(Node node, int id){ 
        nodes.put(id, node);
    }
    
    /**
     * Adds references to an edge to both relevant nodes, 
     * and calculates the length
     */
    public void addEdge(Edge edge) {
        int id = edge.either();
        int id2 = edge.other(id);
        Node tmpnode = nodes.get(id);
        Node tmpnode2 = nodes.get(id2);
        tmpnode.addEdge(edge);
        tmpnode2.addEdge(edge);
        
        if(edge.getLength() == null)
        {
        edge.calcLength(tmpnode.x, tmpnode2.x, tmpnode.y, tmpnode2.y);
        }
        
        numberOfEdges++;
    }
    
    /**
     * Removes a node with exactly two edges connected,
     * and creates a new edge-object in place
     */
    public void combine(int key){
        // check if only two edges are connected to this node
        //System.out.println("Combining node id:" + key + " " + nodes.get(key).getEdges().size());
        if (nodes.get(key).getEdges().size() == 2) {
            HashSet<Edge> edges = nodes.get(key).getEdges();
            
            Edge e1;
            Edge e2;            
            Node n1;
            Node n2;
            Iterator<Edge> i = edges.iterator();
            e1 = i.next();
            e2 = i.next();
            
            //Checks for nodes that are connected in a closed circuit.
            if(!(e1.other(key) == e2.other(key)) || !(nodes.get(e1.other(key)).getEdges().size() == 2))
            {
                edges.clear();
                n1 = nodes.get(e1.other(key));
                n2 = nodes.get(e2.other(key));
                
                
                n1.removeEdge(e1);
                n2.removeEdge(e2);
                numberOfEdges -= 2;
                
                removeNode(key);
                
                // Add a new edge to the two hanging nodes
                // in the absense of the newly removed node.
                // Since a hashset cannot contain dublicates
                // two unique edges are needed instead if
                // both edges pointed towards the same node
                if (n1.equals(n2)) {
                    addEdge(new Edge(e1.other(key), e2.other(key), e1.getLength().add(e2.getLength())));
                    addEdge(new Edge(e1.other(key), e2.other(key), e1.getLength().add(e2.getLength())));
                }
                else
                    addEdge(new Edge(e1.other(key), e2.other(key), e1.getLength().add(e2.getLength())));
            }
        }
        else
            throw new IllegalArgumentException();
    }
    
    /**
     * Removes a node from the HashMap
     */
    public void removeNode(int key) {
        // Check to see if any edges remain
        if (nodes.get(key).getEdges().size() == 0)
            nodes.remove(key);
        else
            throw new IllegalArgumentException();
    }
    
    /**
     * Test method
     */
    public void printEdges(int key) {
        System.out.println("Node " + key + " has following edges:");
        
        for (Edge e : nodes.get(key).getEdges()) {
            System.out.println(e.either() + "\t" + e.other(e.either()));
        }
        
        System.out.println();
    }
    
    /**
     * Returns the entrySet of the HashMap
     */
    public Set<Map.Entry<Integer,Node>> getNodes() {
        return nodes.entrySet();
    }
    
    /**
     * Recalculates the number of edges in total
     */
    public void calculateNumberOfEdges() {
        int count = 0;
        Set<Map.Entry<Integer,Node>> n = nodes.entrySet();
        
        for (Map.Entry<Integer,Node> node : n) {
            count += node.getValue().getEdges().size();
        }
        
        numberOfEdges = count/2;
    }
    
    /**
     * Returns the total number of edges
     */
    public int getNumberOfEdges() {
        return numberOfEdges;
    }
    
    /**
     * Returns the total number of nodes
     */
    public int getSize() {
        return nodes.size();
    }
    
    /**
     * Returns an iterator through all the edges
     */
    public Bag<Edge> edges() {
        Bag<Edge> b = new Bag<Edge>();
        
        Set<Map.Entry<Integer,Node>> n = nodes.entrySet();
        
        for (Map.Entry<Integer,Node> node : n) {
            for (Edge e : node.getValue().getEdges())
                if (e.other(node.getKey()) >= node.getKey())
                b.add(e);
        }
        
        
        return b;
    }
    
}