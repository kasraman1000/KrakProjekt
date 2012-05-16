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
		
		
		String[] result;
			result = AddressParser.parseAddress("123, 123");
		assertEquals("",result[4]);
	}
	/*
	@Test
	public void CityNameTest2() {
		
		String[] result = {"","","","",""};
		AddressParser.findCityName("Langvej 5a, 5. sal, 2300 S", result);
		assertEquals("",result[4]);
	}
	
	@Test
	public void CityNameTest3() {
		
		String[] result = {"","","","",""};
		AddressParser.findCityName("Langvej 5a, 5. sal, 2300 i Kbh S", result);
		assertEquals("Kbh S",result[4]);
	}
	
	@Test
	public void CityNameTest4() {
		
		String[] result = {"","","","",""};
		AddressParser.findCityName("Langvej 5a, 5. sal, 2300 Kbh S", result);
		assertEquals("Kbh S",result[4]);
	}
	
	/**
	 * findZipcode()-test
	 */
	/*
	@Test
	public void ZipcodeTest1() {
		
		String[] result = {"","","","",""};
		AddressParser.findZipcode("Langvej 5a, 5. sal, 123 Kbh S", result);
		assertEquals("",result[3]);
	}
	
	@Test
	public void ZipcodeTest2() {
		
		String[] result = {"","","","",""};
		AddressParser.findZipcode("Langvej 5a, 5. sal, 2300 Kbh S", result);
		assertEquals("2300",result[3]);
	}
	
	/**
	 * findHouseNumber()-test
	 */
	/*
	@Test
	public void HouseNumberTest1() {
		
		String[] result = {"","","","",""};
		AddressParser.findHouseNumber("Langvej", result);
		assertEquals("",result[1]);
	}
	
	@Test
	public void HouseNumberTest2() {
		
		String[] result = {"","","","",""};
		AddressParser.findHouseNumber("Langvej 5a, 4. sal, 2300 Kbh S", result);
		assertEquals("5",result[1]);
	}
	
	@Test
	public void HouseNumberTest3() {
		
		String[] result = {"","","","",""};
		AddressParser.findHouseNumber("Langvej 5., 4. sal, 2300 Kbh S", result);
		assertEquals("5",result[1]);
	}
	
	@Test
	public void HouseNumberTest4() {
		
		String[] result = {"","","","",""};
		AddressParser.findHouseNumber("Langvej 5 4. sal, 2300 Kbh S", result);
		assertEquals("5",result[1]);
	}
	
	@Test
	public void HouseNumberTest5() {
		
		String[] result = {"","","","",""};
		AddressParser.findHouseNumber("Langvej 2300 Kbh S", result);
		assertEquals("",result[1]);
	}
	
	/**
	 * findHouseLetter()-test
	 */
	/*
	@Test
	public void HouseLetterTest1() {
		
		String s = "";
		Pattern cityNamePattern = Pattern.compile("\\b[a-zA-ZæøåÆØÅüÜ\\s]+\\z");
		Matcher cityNameMatcher = cityNamePattern.matcher(s);		
		cityNameMatcher.find();
		String[] result = {"","","a","",""};
		AddressParser.findHouseLetter(s, result, cityNameMatcher);
		assertEquals("a",result[2]);
	}
	
	@Test
	public void HouseLetterTest2() {
		
		String s = "Langvej 5, 5. sal, 2300 Kbh";
		Pattern cityNamePattern = Pattern.compile("\\b[a-zA-ZæøåÆØÅüÜ\\s]+\\z");
		Matcher cityNameMatcher = cityNamePattern.matcher(s);		
		cityNameMatcher.find();
		String[] result = {"","","","",""};
		AddressParser.findHouseLetter(s, result, cityNameMatcher);
		assertEquals("",result[2]);
	}
	
	@Test
	public void HouseLetterTest3() {
		
		String s = "Langvej 5 a, 5. sal, 2300 Kbh";
		Pattern cityNamePattern = Pattern.compile("\\b[a-zA-ZæøåÆØÅüÜ\\s]+\\z");
		Matcher cityNameMatcher = cityNamePattern.matcher(s);		
		cityNameMatcher.find();
		String[] result = {"","","","",""};
		AddressParser.findHouseLetter(s, result, cityNameMatcher);
		assertEquals("a",result[2]);
	}
	
	@Test
	public void HouseLetterTest4() {
		
		String s = "Langvej 5 a, 5. sal, 2300 Kbh S";
		Pattern cityNamePattern = Pattern.compile("\\b[a-zA-ZæøåÆØÅüÜ\\s]+\\z");
		Matcher cityNameMatcher = cityNamePattern.matcher(s);
		cityNameMatcher.find();
		String[] result = {"","","","","Kbh S"};
		AddressParser.findHouseLetter(s, result, cityNameMatcher);
		assertEquals("a",result[2]);
	}

	@Test
	public void HouseLetterTest5() {
		
		String s = "Langvej 5, 5. sal, 2300 Kbh S";
		Pattern cityNamePattern = Pattern.compile("\\b[a-zA-ZæøåÆØÅüÜ\\s]+\\z");
		Matcher cityNameMatcher = cityNamePattern.matcher(s);
		cityNameMatcher.find();
		String[] result = {"","","","","Kbh S"};
		AddressParser.findHouseLetter(s, result, cityNameMatcher);
		assertEquals("",result[2]);
	}
	*/
}
