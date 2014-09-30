/**
* Author: Douglas Skrypa
*/

package misc;

public class MyCustomParseException extends Exception {
	private static final long serialVersionUID = 0; //Regenerate
	private static final String defaultMessage = "Unable to parse filename";
	
	public MyCustomParseException() {
		super(defaultMessage);
	}
	
	public MyCustomParseException(final String message) {
		super(defaultMessage + " " + message);
	}	
}
