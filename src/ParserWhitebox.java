import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import errorHandling.AddressInputFormatException;
//import org.junit.Before;

import routing.AddressParser;


public class ParserWhitebox {
	
	/**
	 * findRoadName()-test
	 * @throws AddressInputFormatException 
	 */
	
	@Test
	public void RoadNameTest1() throws AddressInputFormatException {
		
		String[] result = AddressParser.parseAddress("123, 123");
		assertEquals("",result[0]);
	}
	
	@Test
	public void RoadNameTest2() throws AddressInputFormatException {
		
		String[] result = AddressParser.parseAddress("Langvej 5a, 5. sal, 2300 Kbh S");
		assertEquals("Langvej",result[0]);
	}
	
	/**
	 * findCityName()-test
	 * @throws AddressInputFormatException 
	 */
	
	@Test
	public void CityNameTest1() throws AddressInputFormatException {
		
		String[] result = AddressParser.parseAddress("123, 123");
		assertEquals("",result[4]);
	}
	
	@Test
	public void CityNameTest2() throws AddressInputFormatException {
		
		String[] result = AddressParser.parseAddress("Langvej 5a, 5. sal, 2300 S");
		assertEquals("",result[4]);
	}
	
	@Test
	public void CityNameTest3() throws AddressInputFormatException {
		
		String[] result = AddressParser.parseAddress("Langvej 5a, 5. sal, 2300 i Kbh S");
		assertEquals("Kbh S",result[4]);
	}
	
	@Test
	public void CityNameTest4() throws AddressInputFormatException {
		
		String[] result = AddressParser.parseAddress("Langvej 5a, 5. sal, 2300 Kbh S");
		assertEquals("Kbh S",result[4]);
	}
	
	/**
	 * findZipcode()-test
	 * @throws AddressInputFormatException 
	 */
	
	@Test
	public void ZipcodeTest1() throws AddressInputFormatException {
		
		String[] result = AddressParser.parseAddress("Langvej 5a, 5. sal, 123 Kbh S");
		assertEquals("",result[3]);
	}
	
	@Test
	public void ZipcodeTest2() throws AddressInputFormatException {
		
		String[] result = AddressParser.parseAddress("Langvej 5a, 5. sal, 2300 Kbh S");
		assertEquals("2300",result[3]);
	}
	
	/**
	 * findHouseNumber()-test
	 * @throws AddressInputFormatException 
	 */
	
	@Test
	public void HouseNumberTest1() throws AddressInputFormatException {
		
		String[] result = AddressParser.parseAddress("Langvej");
		assertEquals("",result[1]);
	}
	
	@Test
	public void HouseNumberTest2() throws AddressInputFormatException {
		
		String[] result = AddressParser.parseAddress("Langvej 5a, 4. sal, 2300 Kbh S");
		assertEquals("5",result[1]);
	}
	
	@Test
	public void HouseNumberTest3() throws AddressInputFormatException {
		
		String[] result = AddressParser.parseAddress("Langvej 5., 4. sal, 2300 Kbh S");
		assertEquals("5",result[1]);
	}
	
	@Test
	public void HouseNumberTest4() throws AddressInputFormatException {
		
		String[] result = AddressParser.parseAddress("Langvej 5 4. sal, 2300 Kbh S");
		assertEquals("5",result[1]);
	}
	
	@Test
	public void HouseNumberTest5() throws AddressInputFormatException {
		
		String[] result = AddressParser.parseAddress("Langvej 2300 Kbh S");
		assertEquals("",result[1]);
	}
	
	/**
	 * findHouseLetter()-test
	 * @throws AddressInputFormatException 
	 */
	
	@Test
	public void HouseLetterTest1() throws AddressInputFormatException {
		
		String[] result = AddressParser.parseAddress(",a");
		assertEquals("a",result[2]);
	}
	
	@Test
	public void HouseLetterTest2() throws AddressInputFormatException {
		
		String[] result = AddressParser.parseAddress("Langvej 5, 5. sal, 2300 Kbh");
		assertEquals("",result[2]);
	}
	
	@Test
	public void HouseLetterTest3() throws AddressInputFormatException {
		
		String[] result = AddressParser.parseAddress("Langvej 5 a, 5. sal, 2300 Kbh");
		assertEquals("a",result[2]);
	}
	
	@Test
	public void HouseLetterTest4() throws AddressInputFormatException {
		
		String[] result = AddressParser.parseAddress("Langvej 5 a, 5. sal, 2300 Kbh S");
		assertEquals("a",result[2]);
	}

	@Test
	public void HouseLetterTest5() throws AddressInputFormatException {
		
		String[] result = AddressParser.parseAddress("Langvej 5, 5. sal, 2300 Kbh S");
		assertEquals("",result[2]);
	}
}
