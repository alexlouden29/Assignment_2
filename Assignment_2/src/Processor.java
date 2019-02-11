import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Processor class can get various ID numbers from any text file.  To adjust regex's, developer only needs to worry about the
 * private variables.  New methods should follow the pattern get[# Type]FromFile.
 * @author alexl
 *
 */
public class Processor {

	//Regex Strings
	private final String PANID_REGEX = "(PANID\\s*=\\s*\\p{Alnum}*)(\\s*\n)";
	private final String MAC_REGEX = "(\\p{XDigit}{4}\\s+)(\\p{XDigit}{16})";
	private final String RSSI_REGEX = MAC_REGEX + "\\s(\\S+\\s+){5}(\\S+)";
	
	//Default ctor
	public Processor() {
		//Do nothing
	}
	
	/**
	 * Given the path of a file, returns an array of all PANID's in that file.
	 * @param file
	 * @return List<String> 
	 * @throws IOException IOException If path is invalid.
	 */
	public ArrayList<String> getPANIDFromFile(Path path) throws IOException {
		
		//Create Matcher
		Matcher matcher = createMatcherFromFilePath(PANID_REGEX, path);
		
		//Get Matches
		return getMatches(matcher, 1);
	}
	
	/**
	 * Given the path of a file, returns an array of all MAC addresses in that file.
	 * Relies on the SADDR being a 4 digit hex directly before the MAC.
	 * @param path
	 * @return
	 * @throws IOException If path is invalid.
	 */
	public ArrayList<String> getMACFromFile(Path path) throws IOException {
		
		//Create Matcher
		Matcher matcher = createMatcherFromFilePath(MAC_REGEX, path);
		
		//Get Matches
		return getMatches(matcher, 2);
	}
	
	/**
	 * Given the path of a file, returns an array of all RSSI's in that file.
	 * Relies on order of elements in file to be exact.
	 * @param path
	 * @return
	 * @throws IOException If path is invalid.
	 */
	public ArrayList<String> getRSSIFromFile(Path path) throws IOException {
		
		//Create Matcher
		Matcher matcher = createMatcherFromFilePath(RSSI_REGEX, path);
		
		//Get Matches
		//Cannot call helper method, need to concatenate disparate groups
		ArrayList<String> matches = new ArrayList<String>();
		while(matcher.find()) {
			matches.add(matcher.group(2) + " " +  matcher.group(4));
		}
		
		return matches;
	}
	
	/**
	 * Helper method gets matches given a regex and a path
	 * @param regex The regex to create the pattern with.
	 * @param path The path of the file to be read from.
	 * @return
	 * @throws IOException If path is invalid.
	 */
	private Matcher createMatcherFromFilePath(String regex, Path path) throws IOException {
		Pattern pattern = Pattern.compile(regex);
		
		//Read from file
		String input = new String(Files.readAllBytes(path));
		
		//Return matcher
		return pattern.matcher(input);
	}
	
	/**
	 * Helper method makes List<String> out of a matcher, that contains all found matches.
	 * @param matcher The matcher
	 * @return
	 */
	private ArrayList<String> getMatches(Matcher matcher, int group) {
		//Use list because number of matches is unknown.
		ArrayList<String> matches = new ArrayList<String>();
		while(matcher.find()) {
			matches.add(matcher.group(group));
		}
		
		return matches;
	}
	
}
