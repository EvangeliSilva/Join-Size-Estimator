package hw8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The Utils class provides utility methods for working with ArrayLists.
 * 
 * @author Evangeli Silva (esilva2@albany.edu)
 *
 */
public class Utils {
	
	/**
	 * Returns a string representation of the given ArrayList by converting it to an array 
	 * and replacing the square brackets with parentheses.
	 * 
	 * @param list
	 * 			The ArrayList to be converted to a string representation
	 * @return A string representation of the given ArrayList with square brackets replaced by parentheses
	 */
	public static String toString(ArrayList<String> list) {
		return Arrays.toString(list.toArray()).replace("[", "(").replace("]", ")");
	}
	
	/**
	 * Returns a new ArrayList containing the intersection of the two input ArrayLists.
	 * 
	 * @param list1
	 * 			The first input ArrayList
	 * @param list2 
	 * 			The second input ArrayList
	 * @return A new ArrayList containing the intersection of the two input ArrayLists
	 */
	public static ArrayList<String> getIntersection(ArrayList<String> list1, ArrayList<String> list2) {
		return list1.stream().filter(list2::contains)
				.collect(Collectors.toCollection(ArrayList::new));
	}

}
