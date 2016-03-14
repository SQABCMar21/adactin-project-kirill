/**
 * File Name: Arrays.java<br>
 * Nepton, Jean-francois<br>
 * Java Boot Camp Exercise<br>
 * Instructor: Jean-francois Nepton<br>
 * Created: Feb 10, 2016
 */
package com.sqa.jf.util.helper;

import java.util.*;

/**
 * Arrays //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 *
 * @author Nepton, Jean-francois
 * @version 1.0.0
 * @since 1.0
 */
public class ArrayUtil {

	// Create a method that takes a String array and increases it value by one
	// element
	public static String[] addStringElement(String[] elements, String addString) {
		// Local variables
		String[] newStringArray;
		// Check if the supplied String array is null and if so, we will be
		// adding one element
		if (elements != null) {
			// Supplied array contains elements so the new size will be
			// increased by 1 and add element
			newStringArray = Arrays.copyOf(elements, elements.length + 1);
			// Set the last element to supplied String addString
			newStringArray[elements.length] = addString;
		} else {
			// Supplied array does not contain any elements yet
			newStringArray = new String[1];
			// Set the first/last and only element to supplied String addString
			newStringArray[0] = addString;
		}
		return newStringArray;
	}
}
