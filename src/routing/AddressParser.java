package routing;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class AddressParser {
    
    /**
     * Parses a string and returns an array containing:
     * 
     * 0: street name
     * 1: house number
     * 2: letter associated with house number
     * 3: zip code
     * 4: city name
     */
    public static String[] parseAddress(String s) throws IllegalArgumentException {
        // The result array to return
        String[] result = {"","","","",""};
        
        // Road Name 
        findRoadName(s, result);
        
        // House Number (And maybe house letter)
        findHouseNumber(s, result);

        // City Name (And maybe house letter)
        findCityName(s, result);
        
        // Zipcode 
        findZipcode(s, result);

        // We're done parsing, let's return the results
        return result;
    }
    
    /**
     * Searches input string for road name
     */
    private static void findRoadName(String s, String[] result) {
        Pattern roadNamePattern = Pattern.compile("\\A[a-zA-ZæøåÆØÅüÜ'\\s]+\\b");
        Matcher roadNameMatcher = roadNamePattern.matcher(s);
        
        if (roadNameMatcher.find()) {
            result[0] = s.substring(roadNameMatcher.start(), roadNameMatcher.end()).trim();
        }
    }
    
    /**
     * Searches input string for city name
     */
    private static void findCityName(String s, String[] result) {
        Pattern cityNamePattern = Pattern.compile("\\b[a-zA-ZæøåÆØÅüÜ\\s]+\\z");
        Matcher cityNameMatcher = cityNamePattern.matcher(s);
        
        // If the matcher finds something in the specified pattern (City name)
        if (cityNameMatcher.find()) {
            String match = s.substring(cityNameMatcher.start(), cityNameMatcher.end()).trim();
            
            // The match needs to be more than a single character to be valid
            if (match.length() > 1) {
                // If the second character is a whitespace, it means the first word
                // is a single letter, and should be removed)
                if (Character.isWhitespace(match.charAt(1)))
                    result[4] = match.substring(2);
                else 
                    result[4] = match;
            }
        }
        
        // Call findHouseLetter in here, becase it needs the Matcher-object 
        // from this method for comparisons
        findHouseLetter(s, result, cityNameMatcher);
    }
    
    /**
     * Searches input string for zipcode
     */
    private static void findZipcode(String s, String[] result) {
        Pattern zipcodePattern = Pattern.compile("\\b[1-9]\\d{3}\\b");
        Matcher zipcodeMatcher = zipcodePattern.matcher(s);
        
        if (zipcodeMatcher.find()) 
            result[3] = s.substring(zipcodeMatcher.start(), zipcodeMatcher.end());
    }
    
    /**
     * Searches input string for House number, and if a letter is attached 
     * to the number, it will include that as a house letter as well. 
     */
    private static void findHouseNumber(String s, String[] result) {
        Pattern houseNumberPattern = Pattern.compile("\\b\\d{1,3}+.");
        Matcher houseNumberMatcher = houseNumberPattern.matcher(s);
        
        // If the matcher finds something in the specified pattern (house number)
        if (houseNumberMatcher.find()) {
            String match = s.substring(houseNumberMatcher.start(), houseNumberMatcher.end());
            
            // If final character in matched string is a letter
            if (Character.isLetter(match.charAt(match.length() - 1))) {
                result[2] = match.substring(match.length() - 1);
                result[1] = match.substring(0, match.length() - 1);
            }
            // If final character in matched string isn't a number
            else if (!Character.isDigit(match.charAt(match.length() - 1))) {      
                result[1] = match.substring(0, match.length() - 1);
            }
            else if (match.length() < 4)
                result[1] = match;
        }
    }
    
    /**
     * Searches input string for House letter.
     * Uses the cityNameMatcher to compare wether the letter found isn't
     * already a part of the city name
     */
    private static void findHouseLetter(String s, String[] result, Matcher cityNameMatcher) {
        if (result[2] == "") {
            Pattern houseLetterPattern = Pattern.compile("\\b[a-zA-Z]\\b");
            Matcher houseLetterMatcher = houseLetterPattern.matcher(s);
            
            // If the matcher finds something in the specified pattern (house letter)
            if (houseLetterMatcher.find()) {
                // Check if the letter found isn't part of the city name...
                // If a city name couldn't be found, just add the match to the result array
                if (result[4] == "")
                    result[2] = result[2] = s.substring(houseLetterMatcher.start(), houseLetterMatcher.end());
                // If a city name was found, check if the index of the house letter
                // is before the last letter in the city name
                else {
                    if (houseLetterMatcher.end() < cityNameMatcher.end() - 1)
                        result[2] = s.substring(houseLetterMatcher.start(), houseLetterMatcher.end());
                }
            }
        }
    }
    
        
    
}
