/**
* Author: Douglas Skrypa
* ReTimer can be used to set the timestamp on all files in the given directory
* to the new specified time.
*/

package misc;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReTimer {
	public final static SimpleDateFormat sdf = new SimpleDateFormat("EEE dd MMM yyyy hh:mm:ss a");
	public final static SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public static void main(final String[] args) throws ParseException
	{
		final String dir = "/media/ssd/";
		final long newTime = dateParser.parse("2013-09-20 00:00:00").getTime();
		
		retime(dir + "temp", newTime);
	}

	public static void retime(final String path, final long newTime){
		final String newDate = sdf.format(new Date(newTime));
		System.out.println(path);
		final File f = new File(path);
		final String[] files = f.list();

		for (final String file : files) {
			final String tPath = path + "/" + file;
			final File temp = new File(tPath);
			if(temp.isDirectory()){
				retime(tPath, newTime);
				temp.setLastModified(newTime);
			} else {
				System.out.println("Changing date for: " + temp.getAbsolutePath() + " from " + sdf.format(new Date(temp.lastModified())) + " to " + newDate);
				temp.setLastModified(newTime);
			}
		}
	}
}
