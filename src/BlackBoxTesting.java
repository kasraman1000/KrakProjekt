import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 */

/**
 * @author Lars Yndal Sørensen
 *
 */
public class BlackBoxTesting {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Black Box test of addressParser
	 * 
	 * Takes the input from the BlackBoxInput.txt file. Every odd line is going
	 * through the addressParser and then compared with the even line (check line) 
	 * 
	 * Note the graphical output has a HashSet of tests that are supposed to fail!
	 * 
	 */
	@Test
	public void test() {
		String[] testStrings = TextIO.read("BlackBoxInput.txt");
		String[] stringsToTest = new String[(testStrings.length/2)];
		String[] originalInput = new String[(testStrings.length/2)];
		String[] stringsToCompareWith = new String[(testStrings.length/2)];
		
		//Read the lines into the correct arrays
		for(int index=0; index < testStrings.length; index++){
			if (index%2 == 0){
				//Read test lines
				stringsToTest[index/2] = testStrings[index];
				originalInput[index/2] = testStrings[index];
			} else {
				//Read lines to compare with
				stringsToCompareWith[index/2] = testStrings[index];
			}
		}
		
		//Pass the test lines through the AddressParser and store the returned string[] as a string (because of upcoming comparing)
		for(int a=0; a<stringsToTest.length; a++){
			String[] tempString = new String[5]; //The String[] returned from parseAddress has a size of 5
			
			tempString = AddressParser.parseAddress(stringsToTest[a]);
			stringsToTest[a] = "";
			for(int b=0; b<tempString.length; b++){
				stringsToTest[a] += tempString[b];
				if(b != tempString.length-1) stringsToTest[a] += "#";
			}
		}
		
		assertEquals(stringsToTest.length,stringsToCompareWith.length);
		
		
		/**************************************************************************************
		 * 
		 * 		START OF THE OUTPUT - ONLY IMPLEMENTED TO GET A GRAPHICAL OUTPUT!!!
		 * 		(The assertEquals() is used right after the output)
		 * 
		 *************************************************************************************/
		boolean[] testSuccess = new boolean[stringsToTest.length];
		int[] testsSupposedToFail = new int[]{
				5,7,8,9,14,17,18,19,20,22,23,35,36,37,38,42,43,
				44,45,46,47,48,49,50,51,52,55,56,57,58,59,60,61,
				62,63,64,67,68,72,78,79,80,84,85,88,89,91,};
		HashSet<Integer> failSet = new HashSet<Integer>();
		for(int failIndex=0; failIndex<testsSupposedToFail.length; failIndex++){
			failSet.add(testsSupposedToFail[failIndex]);
		}
	
		System.out.println("!!!Black Box Test!!!");
		for(int c=1; c<=stringsToTest.length; c++){
			System.out.println("Checking line " + c + ":");
			System.out.println("Input line: \t" + originalInput[c-1]);
			System.out.println("Output line: \t" + stringsToTest[c-1]);
			System.out.println("Compare line: \t" + stringsToCompareWith[c-1]);
			if(stringsToTest[c-1].equals(stringsToCompareWith[c-1])){
				System.out.println("Succes");
				testSuccess[c-1] = true;
			} else{
				if(failSet.contains(c-1)) System.out.println("Failed - but supposed to!");
				else System.out.println("Failed!");
				testSuccess[c-1] = false;
			}
			System.out.println();
		}
		
		
		int numOfFails = 0;
		int numOfActualFails = 0;
		System.out.println("Tests failed: ");
		for(int d=1; d<testSuccess.length; d++){
			if(!testSuccess[d-1]){
				if(failSet.contains(d-1)){
					System.out.print("(" + d + ")" + "\t");
					numOfActualFails++;
				} else System.out.print(d + "\t");
				numOfFails++;
				if((numOfFails)%10 == 0) System.out.println();
			} 
		}	
		System.out.println("\nTotal: " + numOfFails + " fails (but only " + numOfActualFails + " expected)");
		
		/***************************************************************************************
		 * 
		 * 		END OF THE OUTPUT
		 * 
		 **************************************************************************************/
		
		
		
		//Compare the two lines
		for(int e=0; e<stringsToTest.length; e++){
			assertEquals(stringsToTest[e], stringsToCompareWith[e]);
		}
	}
}
