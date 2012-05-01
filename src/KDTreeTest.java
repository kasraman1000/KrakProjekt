import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;



public class KDTreeTest {
	
	private static KDTree kdTree = KDTree.getTree();
	private static ArrayList<Node> nodes;
	
	public static int linearSizeRequest(Region r)
	{
		int size = 0;
		r.adjust();
		double[] p1 = r.getLeftPoint();
		double[] p2 = r.getRightPoint();
		for(Node n : nodes)
		{
			if(p1[0] <= n.getCoord(0) && p2[0] >= n.getCoord(0) && p1[1] <= n.getCoord(1) && p2[1] >= n.getCoord(1))
			{
				size++;
			}
		}
		return size;
	}
	
	public static int kdTreeSizeRequest(Region r)
	{
		r.adjust();
		int size = kdTree.searchRange(r).size();
		return size;
	}
	
	
	@BeforeClass
	public static void setup()
	{
		try {
			Loader.load("kdv_node_unload.txt","kdv_unload.txt");
			nodes = Loader.getNodesForKDTree();
			kdTree.initialize(nodes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void metaTest()
	{
		Region r1 = new Region(0, 0, 10000000, 10000000);
		assertEquals(nodes.size(), linearSizeRequest(r1));
	}
	
	@Test
	public void testSearchRegion()
	{
		Region r1 = new Region(0, 0, 10000000, 10000000);
		Region r2 = new Region(10000, 10000, 20000, 20000);
		Region r3 = new Region(100000, 100000, 300000, 300000);
		Region r4 = new Region(595527.0, 6402050.0, 595528.0, 6402051.0);
		assertEquals(linearSizeRequest(r1), kdTreeSizeRequest(r1));
		assertEquals(linearSizeRequest(r2), kdTreeSizeRequest(r2));
		assertEquals(linearSizeRequest(r3), kdTreeSizeRequest(r3));
		assertEquals(linearSizeRequest(r4), kdTreeSizeRequest(r4));
		assertEquals(1, linearSizeRequest(r4));
	}
	
	@Test
	public void testIntersecting()
	{
		Region r1 = new Region(10, 15, 15, 20);
		Region r2 = new Region(13, 12, 20, 17);
		Region r3 = new Region(5, 5, 10, 10);	
		Region r4 = new Region(7, 5, 15, 10);	
		assertTrue(kdTree.intersecting(r1.getLeftPoint(), r1.getRightPoint(), r2.getLeftPoint(), r2.getRightPoint()));
		assertFalse(kdTree.intersecting(r1.getLeftPoint(), r1.getRightPoint(), r3.getLeftPoint(), r3.getRightPoint()));
		assertTrue(kdTree.intersecting(r3.getLeftPoint(), r3.getRightPoint(), r4.getLeftPoint(), r4.getRightPoint()));
		assertFalse(kdTree.intersecting(r2.getLeftPoint(), r2.getRightPoint(), r3.getLeftPoint(), r3.getRightPoint()));
	}
	
	@Test
	public void testFullyContained()
	{
		Region r1 = new Region(10, 10, 20, 20);
		Region r2 = new Region(12, 12, 18, 18);
		Region r3 = new Region(7, 5, 15, 10);
		assertTrue(kdTree.fullyContained(r2.getLeftPoint(), r2.getRightPoint(), r1.getLeftPoint(), r1.getRightPoint()));
		assertFalse(kdTree.fullyContained(r3.getLeftPoint(), r3.getRightPoint(), r1.getLeftPoint(), r1.getRightPoint()));
	}
	
	
	
	

}
