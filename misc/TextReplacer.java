/**
* Author: Douglas Skrypa
*/

package misc;

import java.util.HashMap;

public class TextReplacer {
	public static String replaceText(final String original, final HashMap<String, String> replacements){
		String altered = original;
		for(final String key : replacements.keySet()){
			final String value = replacements.get(key);
			altered = altered.replaceAll(key, value);
		}
		return altered;
	}
}
