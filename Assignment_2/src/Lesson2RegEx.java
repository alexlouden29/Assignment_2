import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Reads from file neighbor-dump.txt, and extracts PAN IDs, MAC Addresses, and RF_RSSI_M vales per MAC address.
 * Can be altered to use a different file.
 * @author alexl
 *
 */
public class Lesson2RegEx {

	//Source file
	static Path path = Paths.get("resources/neighbor-dump.txt");
	
	public static void main(String[] args) {
		try {
			Processor processor = new Processor();
			
			ArrayList<String> panList = processor.getPANIDFromFile(path);
			System.out.println("- List of PAN IDs (Total of " + panList.size() + ")");
			printAllMatches(panList);
			
			System.out.println();
			
			ArrayList<String> macList = processor.getMACFromFile(path);
			System.out.println("- List of MAC Addresses (Total of " + macList.size() + ")");
			printAllMatches(macList);
			
			System.out.println();
			
			System.out.println("- List of RF_RSSI_M values for each MAC address");
			ArrayList<String> rssiList = processor.getRSSIFromFile(path);
			printAllMatches(rssiList);
			
		} catch(IOException e) {
			System.out.println(path + " is not valid.  Exiting program.");
		}

	}
	
	/**
	 * Print out an array of matches.
	 * @param matches
	 */
	private static void printAllMatches(List<String> matches) {
		for( int x = 0; x < matches.size(); x++){
			System.out.println(matches.get(x));
		}
	}

}
