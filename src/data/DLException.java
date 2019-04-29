package data;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Xin Liu
 *
 * This class will handle all the exceptions thrown in the Data Layer and writes the error to a text file for error log keeping.
 */
public class DLException extends Exception {
	public static final String LOG_FILE = "errors.txt";
	private PrintWriter pw = null;

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String dateString = format.format( new Date() );

	/**
	 * This constructor will take the exception passed in from a data layer
	 *
	 * @param e exception caught a data layer
	 */
	public DLException(Exception e) {
		super("Failed to complete this operation at this time!");
		writeLog(e, null, null);
	}

	/**
	 * This constructor will take the exception and an array of strings passed in from a data layer
	 *
	 * @param e exception caught a data layer
	 * @param msg error message
	 * @param s array of strings
	 */
	public DLException(Exception e, String msg, String... s) {
		super(msg);
		writeLog(e, s);
	}

	/**
	 * Writes all available information, a timestamp to a text log file. Called in the constructor.
	 *
	 * @param e Exception caught
	 * @param s Information strings that are passed to the constructor
	 */
	public void writeLog(Exception e, String... s) {
		// Create StringBuilder to append Strings
		StringBuilder errString = new StringBuilder();

		// Append all strings passed through the constructor
		for(int i=0; i<s.length; i++) {
			errString.append(s[i] + "\n");
		}

		// Append the actual exception
		errString.append(e + "\n");

		// Get the entire exception stack trace
		for(StackTraceElement ste : e.getStackTrace()) {
			errString.append(ste + "\n");
		}

		// Create PrintWriter to write to file
		try {
			pw = new PrintWriter( new FileOutputStream(LOG_FILE, true) );
		}
		catch(FileNotFoundException fnfe) { fnfe.printStackTrace(); }

		// Write to file
		pw.println(dateString + " " + errString.toString());
		pw.flush();
		pw.close();
	}
}
