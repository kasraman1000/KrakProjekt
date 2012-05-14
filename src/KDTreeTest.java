import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import models.KDTree;
import models.Loader;
import models.Node;
import models.Region;

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
			Loader.load("kdv_node_unload.txt","kdv_unload.txt", "zip_codes.txt");
			nodes = Loader.getNodesForKDTree();
			@SuppressWarnings("unchecked")
			ArrayList<Node> buildNodes = (ArrayList<Node>) nodes.clone();
			kdTree.initialize(buildNodes);
		} catch (Exception e) {
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
		Region r2 = new Region(0, 0, 70000, 90000);
		Region r3 = new Region(100000, 0, 300000, 300000);
		Region r4 = new Region(300000, 200000, 310000, 220000);
		Region r5 = new Region(43750, 42750, 213850, 261450);
		Region r6 = new Region(77770, 112743, 139006, 191466);
		Region r7 = new Region(209560, 155016, 458392, 294984);
		assertEquals(linearSizeRequest(r1), kdTreeSizeRequest(r1));
		assertEquals(linearSizeRequest(r2), kdTreeSizeRequest(r2));
		assertEquals(linearSizeRequest(r3), kdTreeSizeRequest(r3));
		assertEquals(linearSizeRequest(r4), kdTreeSizeRequest(r4));
		assertEquals(linearSizeRequest(r5), kdTreeSizeRequest(r5));
		assertEquals(linearSizeRequest(r6), kdTreeSizeRequest(r6));
		assertEquals(linearSizeRequest(r7), kdTreeSizeRequest(r7));
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
