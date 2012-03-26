import java.math.BigDecimal;

public class Edge {
    
    private int id1;
    private int id2;
    private BigDecimal length;
    
    
    public Edge(int id1, int id2) {
        this.id1 = id1;
        this.id2 = id2;
    }
    
    public Edge(int id1, int id2, BigDecimal length)
    {
        this.id1 = id1;
        this.id2 = id2;
        this.length = length;
    }
    
    /**
     * Sets the length of this edge
     */
    public void setLength(BigDecimal l) {
        this.length = l;
    }
    
    /**
     * Calculates the length of this edge, given two sets of coordinates (x,y)
     */
    public void calcLength(BigDecimal x1, BigDecimal x2, BigDecimal y1, BigDecimal y2) {
        length = BigDecimal.valueOf(Math.sqrt(Math.pow((x2.doubleValue() - 
                                                        x1.doubleValue()),2) + 
                                              (Math.pow((y2.doubleValue() - 
                                                         y1.doubleValue()), 2))));
    }
    
    /**
     * Returns the length of this edge
     */
    public BigDecimal getLength() {
        return length;
    }
    
    /**
     * Returns the id of one of the nodes connected
     */
    public int either() {
        return id1;
    }
    
    /**
     * Returns the OTHER respective node connected
     */
    public int other(int i) {
        if (i == id1) return id2;
        else if (i == id2) return id1;
        else throw new IllegalArgumentException();
    }
    
    /**
     * Replaces a reference to a node with a new id
     */
    public void replace(int id, int newId) {
        if (id == id1) id1 = newId;
        else if (id == id2) id2 = newId;
        else throw new IllegalArgumentException();
    }
    
    
}
