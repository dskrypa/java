/**
* Author: Douglas Skrypa
* Generates objects that represent and wrap the data stored in an XML.  Objects
* must use the XmlLoadableEntity interface and implement the class that handles
* setting fields based on variable name.
*/

package misc;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class EntityLoader<T extends XmlLoadableEntity>
{
	private final Class<T> c;
	
	public EntityLoader(final Class<T> c){
		this.c = c;
	}
	
	public List<T> getEntitiesFromXml(final XMLElement fullXml) throws MyCustomParseException, ReflectiveOperationException
	{
		final List<T> ts = new ArrayList<>();
		for(final XMLElement t : fullXml.getChildElements()){
			ts.add(getEntityFromXml(t));
		}
		return ts;
	}
	
	public T getEntityFromXml(final XMLElement entity) throws MyCustomParseException, ReflectiveOperationException
	{
		T t = null;
		for(final XMLElement property : entity.getChildElements()){
			if(t==null){
				try {
					Constructor<T> con = c.getConstructor(String.class);
					t = con.newInstance(property.getStringValue());
				} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new ReflectiveOperationException("Unable to construct object of type " + c.getName() + "; Field: " + property.getName() + "\tValue: " + property.getStringValue());
				}
			} else {
				t.setField(property.getName(), property.getStringValue());
			}
		}
		return t;
	}
}
