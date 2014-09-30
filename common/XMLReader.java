/**
* Author: Douglas Skrypa
*/

package common;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLReader {
	private BFReader reader;

	public static XMLElement getFileContents(final String path) throws IOException
	{
		final XMLReader x = new XMLReader();
		return x.getContents(path);
	}

	private XMLReader(){}

	private XMLElement getContents(final String path) throws IOException
	{
		reader = new BFReader(path);
		final XMLElement primary = parseLine(getValidLine());
		while(primary != null && !primary.terminates()){
			addContentsToItem(primary);
		}
		reader.close();
		return primary;
	}

	private void addContentsToItem(final XMLElement primary){
		final XMLElement secondary = parseLine(getValidLine());
		if(secondary == null){return;}
		while(!secondary.terminates()){
			if(primary.getName().equals(secondary.getName())){
				primary.terminateNow();
				return;
			}
			addContentsToItem(secondary);
		}
		primary.addChild(secondary);
	}

	private static XMLElement parseLine(final String input){
		if(input == null){return null;}

		boolean terminates = false;
		final String pairedMatch = "<(\\w+?)>(.*?)</\\1>";
		final String singleMatch = "</?(\\w+?)>";
		Matcher m = Pattern.compile(pairedMatch).matcher(input);

		if(m.matches()){
			terminates = true;
		} else {
			m = Pattern.compile(singleMatch).matcher(input);
			if(m.matches()){}
			else {return null;}
		}

		try{
			final XMLElement i = new XMLElement(m.group(1));
			if(terminates){
				i.setValue(m.group(2));
			}
			return i;
		} catch (final Exception e){
			System.out.println("\"" + input + "\"");
			e.printStackTrace();
			return null;
		}
	}

	private String getValidLine(){
		String line = getLine();
		if(line == null) {
			return null;
		}

		line = line.replaceAll("(?:.{3}(?=<\\?)){0,1}<(!--|\\?).*?(--|\\?)>","");
		line = line.replaceAll("^\\s+|\\s+$", "");

		if(line.matches("<!--.*?(?!-->)")){
			while(!line.matches(".*?-->.*")){
				line = getValidLine();
			}
			final Pattern p = Pattern.compile(".*-->\\s*(.+)$");
			final Matcher m = p.matcher(line);
			if(m.matches()){
				line = m.group(1);
			}
		}
		if(line !=null && (line.length() < 1 || line.equals(""))){
			line = getValidLine();
		}
		return line;
	}

	private String getLine(){
		try {
			return reader.readLine();
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}