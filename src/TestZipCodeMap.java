
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import models.Loader;

import org.junit.BeforeClass;
import org.junit.Test;


public class TestZipCodeMap {

	private static HashMap<String, Integer> zipCodeMap;
	
	@BeforeClass
	public static void setup()
	{
		try {
			Loader.buildZipCodeMap("zip_codes.txt");
			zipCodeMap = Loader.getZipCodeMap();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test()
	{
		assertTrue(zipCodeMap.get("Herlev").equals(new Integer(2730)));
		assertTrue(zipCodeMap.get("Almind").equals(new Integer(6051)));
		assertTrue(zipCodeMap.get("Gentofte").equals(new Integer(2820)));
		assertTrue(zipCodeMap.get("København N").equals(new Integer(2200)));
		assertTrue(zipCodeMap.get("Lundby").equals(new Integer(4750)));
		assertTrue(zipCodeMap.get("Sjællands Odde").equals(new Integer(4583)));
	}
}
