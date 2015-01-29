package mmaia.coursera.algorithm.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Scanner;
import java.util.logging.Logger;

public class Utils {
	
	private final static Logger log = Logger.getLogger(Utils.class.getName());

	
	/**
	 * Load the file in a Scanner object.
	 * 
	 * @param fileName
	 *            to be loded to the scanner.
	 * @return a scanner with the file loaded.
	 */
	public final static Scanner prepareScanner(String fileName) {
		Scanner result = null;
		File file = null;
		try {
			file = new File(fileName);
			result = new Scanner(file);
			log.info("File loaded " + file.getName());
		} catch (FileNotFoundException e) {
			log.severe("Error loading file: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Gives the number of lines(start counting from 0) of the given file. It
	 * can only process files which are not bigger than java Integer.MAX_VALUE
	 * if value is bigger than that it will return Integer.MAX_VALUE instead.
	 * 
	 * @param file
	 *            - File object constructed from the desired file to count
	 *            number of lines
	 * @return the number of lines (start counting from 0, so to have the exact
	 *         size add 1 to the return code of this method)
	 */
	@SuppressWarnings("resource")
	public final static int numberOfFileLines(String fileName) {
		File file = null;
		int result = 0;
		// getting the number of lines of the file
		LineNumberReader lnr = null;
		try {
			file = new File(fileName);
			lnr = new LineNumberReader(new FileReader(file));
			// because this is bigger than the number of lines of the file goes
			// to last line
			lnr.skip(Integer.MAX_VALUE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = lnr.getLineNumber();
		log.info("Number of lines of this file: " + result
				+ " need to add 1 to the result as this counting started from 0");
		return result;
	}
}
