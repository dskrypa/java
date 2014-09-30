/**
* Author: Douglas Skrypa
*/

package common;

import java.util.ArrayList;
import java.util.List;

/**
 * An XMLElement represents one element of an XML file and its contents,
 * which may include one or more other XMLElements.
 */
public class XMLElement {
	private String elementName;
	private Object value = null;
	private List<XMLElement> childElements = null;
	private boolean terminates;

	public XMLElement(String name){
		elementName = name;
		terminates = false;
	}

	public XMLElement get(String name){
		if(hasChildElements()){
			for(XMLElement temp : childElements){
				if(name.equals(temp.getName())){
					return temp;
				}
			}
		}
		return null;
	}

	public void terminateNow(){
		terminates = true;
	}
	
	public boolean terminates(){
		return terminates;
	}

	public int countChildren(){
		return childElements.size();
	}

	public void addChild(XMLElement i){
		if (childElements == null){
			childElements = new ArrayList<>();
		}
		childElements.add(i);
	}

	public void setValue(Object o){
		String s = (String)o;
		if(s.matches("\\d+")){
			value = Integer.parseInt(s);
		} else {
			if(s.matches("(?i)(?:true|false)")){
				value = s.equalsIgnoreCase("true");
			} else {
				value = s;
			}
		}
		terminates = true;
	}

	public boolean hasChildElements(){
		return childElements != null;
	}

	public boolean hasValue(){
		return value != null;
	}

	public boolean isEmpty(){
		return !hasValue() && !hasChildElements();
	}

	public String getName(){
		return elementName;
	}

	public List<XMLElement> getChildElements(){
		return childElements;
	}

	public Object getValue(){
		return value;
	}
	
	public String getStringValue(){
		return (String)value;
	}

	public Integer getIntegerValue(){
		if(value instanceof Integer){
			return (Integer)value;
		}
		return null;
	}

	public Boolean getBooleanValue(){
		if(value instanceof Boolean){
			return (Boolean)value;
		}
		return null;
	}
}