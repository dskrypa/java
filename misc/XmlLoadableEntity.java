/**
* Author: Douglas Skrypa
* Interface that must be implemented by objects that are used to represent and
* wrap data stored in an XML file so that they may be loaded by my EntityLoader
* class.
*/

package misc;

public interface XmlLoadableEntity {
	/**
	 * @param field a String representing a variable/property name of this entity
	 * @param value a String representing the value of the given property
	 * @return true if the field was set successfully, false otherwise
	 * @throws MyCustomParseException if there was an error parsing the info
	 */
	public boolean setField(final String field, final String value) throws MyCustomParseException;
}
