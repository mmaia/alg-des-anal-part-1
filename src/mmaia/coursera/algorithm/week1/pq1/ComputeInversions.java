package mmaia.coursera.algorithm.week1.pq1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Does a linear computation of the number of inversions on the given file
 * required to order the numbers contained in it.
 * 
 * @author mmaia - maia.marcos@gmail.com
 */
public class ComputeInversions {
	private final static Logger log = Logger.getLogger(ComputeInversions.class.getName());

	public static void main(String[] args) {
		// String fileName = "IntegerArray.txt";
//		String fileName = "TC1Week1PA1_Answer_3.txt";
		 String fileName = "TC2Week1PA1_Answer_4.txt";
		// String fileName = "TC3Week1PA1_Answer_10.txt";
		// String fileName = "TC4Week1PA1_Answer_5.txt";
		// String fileName = "TC5Week1PA1_Answer_56.txt";
		// String fileName = "TC6Week1PA1_Answer_590.txt";
		// String fileName = "TC7Week1PA1_Answer_2372.txt";

		Scanner scanner = prepareScanner(fileName);

		// starts counting from 0 so numberOfLines is 1 less the the real one
		// when star counting from 1.
		int numberOfLines = numberOfFileLines(fileName);

		int[] listOfIntegersForInsertionSort = new int[numberOfLines + 1];

		// loads the array from file lines
		for (int i = 0; i < listOfIntegersForInsertionSort.length; i++) {
			listOfIntegersForInsertionSort[i] = scanner.nextInt();
		}
		int[] listOfIntegersForMergeSort = Arrays.copyOf(listOfIntegersForInsertionSort,
				listOfIntegersForInsertionSort.length);
		log.info("Finished loading arrays ==>> " + Arrays.toString(listOfIntegersForInsertionSort));
		log.info("Original Array for insertion sort: " + Arrays.toString(listOfIntegersForInsertionSort));

		// sort the array using the InsertionSort algorithm(linear) Big-O(n^2)
		insertionSort(listOfIntegersForInsertionSort);

		
		log.info("Original Array for merge sort: " + Arrays.toString(listOfIntegersForMergeSort));
		long initialTime = System.currentTimeMillis();
		mergeSort(listOfIntegersForMergeSort);
		long finalTime = System.currentTimeMillis();
		long totalTime = finalTime - initialTime;
		log.info("Finished sorting array using merge sort, total time:  " + totalTime + " miliseconds " + " sorted Array: " + Arrays.toString(listOfIntegersForMergeSort));
		// log.info("The total number of inversions of the file: " + fileName +
		// " is: " + numberOfInversions);
	}

	// TODO - This insertion sort implementation runs 2.5 times faster than
	// the next one insertionSortNotOptimal below while parsing the
	// IntegerArray.txt file).
	// I need to study this and find out why.
	/**
	 * Sort array passed using linear implementation(InsertionSort algorithm).
	 * Asymptotic Big-O(worst case) running time O(n^2), Big-theta(best case)
	 * running time O(n).
	 * 
	 * @param arrayToSort
	 *            - The array to be sorted.
	 */
	private static final void insertionSort(int[] toSort) {
		long initialTime = System.currentTimeMillis();
		long inversionCount = 0;
		for (int i = 0; i < toSort.length; i++) {
			int value = toSort[i];
			int j = i - 1;
			while (j >= 0 && toSort[j] > value) {
				toSort[j + 1] = toSort[j];
				j = j - 1;
				inversionCount++;
			}
			toSort[j + 1] = value;
		}
		long finalTime = System.currentTimeMillis();
		long totalTime = finalTime - initialTime;
		log.info("Finished sorting array using insertion Sort, total time:  " + totalTime + " miliseconds "
				+ " number of inversions: " + inversionCount);
		log.info("Sorted array===========: \n" + Arrays.toString(toSort));
	}

	/**
	 * This was my first implementation try, it's much slower than the above, optimal one that 
	 * I implemented after studying the algorithm in more details.
	 * @param arrayToSort
	 */
	private static final void insertionSortNotOptimal(int[] arrayToSort) {
		long initialTime = System.currentTimeMillis();
		long inversionCount = 0;

		for (int i = 0; i < arrayToSort.length; i++) {
			int currentValue = arrayToSort[i];
			// creates a counter to navigate other elements of array comparing
			// with currentValue
			int j = i + 1;
			// iterates from second element to the end comparing each number and
			// holds always the smaller one
			while (j < arrayToSort.length) {
				if (arrayToSort[j] < currentValue) {
					currentValue = arrayToSort[j];
					// switch numbers so current pointer will not be lost.
					arrayToSort[j] = arrayToSort[i];
					arrayToSort[i] = currentValue;
					inversionCount++;
				}
				j++;
			}
		}
		long finalTime = System.currentTimeMillis();
		long totalTime = finalTime - initialTime;
		log.info("Finished sorting array using insertion Sort, total time:  " + totalTime + " miliseconds "
				+ " number of inversions: " + inversionCount);
		log.info("Sorted array===========: \n" + Arrays.toString(arrayToSort));
	}

	/**
	 * Recursive merge sort implementation. Implements the divide and conquer paradigm and recursion.
	 * Divides the array and than merge the nodes parsing it calling the mergeParts below. 
	 * 
	 * @param arrayToSort
	 */
	private static final void mergeSort(int[] arrayToSort) {
		
		if (arrayToSort.length <= 1) {
			return;
		}

		//first half size
		int[] firstHalf = new int[arrayToSort.length / 2];
		//copies first half from original array.
		System.arraycopy(arrayToSort, 0, firstHalf, 0, firstHalf.length);

		//defines the size of the second half(deals with odd numbers from the original list so this can be 1 bigger than the first half)
		int[] secondHalf = new int[arrayToSort.length - firstHalf.length];
		//copies the rest of the original array in the secondHalf array
		System.arraycopy(arrayToSort, firstHalf.length, secondHalf, 0, secondHalf.length);
		
		//recurse first half
		mergeSort(firstHalf);
		
		//recurse second half
		mergeSort(secondHalf);
		
		//merge each leafs sorting it
		mergeParts(firstHalf, secondHalf, arrayToSort);
	}

	//TODO - not yet finished. issues an error as it is. 
	private static final void mergeParts(int[] firstHalf, int[] secondHalf, int [] arrayToSort) {
		//keeps track of firstHalf array
		int firstCounter = 0;
		
		//keeps track of secondHalfArray
		int secondCounter = 0;
		
		int arraySortCounter = 0;
		
		while (firstCounter < firstHalf.length && secondCounter < secondHalf.length) {
			if(firstHalf[firstCounter] < secondHalf[secondCounter]){
				arrayToSort[arraySortCounter] = firstHalf[firstCounter];
				firstCounter++;
			}else{
				arrayToSort[arraySortCounter] = secondHalf[secondCounter];
				secondCounter++;
			}
			arraySortCounter++;
		}
		
		//if it's even initial list there will be one missing value not processed in the while loop above. Need to take care of that: 
//		if(firstHalf[firstCounter]{
//			//than copy last element from firstHalf into final merged array.
//			arrayToSort[arraySortCounter] = firstHalf[firstCounter];
//		}else{
//			//than copy last element from secondHalf into final merged array.
//			arrayToSort[arraySortCounter] = secondHalf[secondCounter];
//		}
	}

	/**
	 * Load the file in a Scanner object.
	 * 
	 * @param fileName
	 *            to be loded to the scanner.
	 * @return a scanner with the file loaded.
	 */
	private final static Scanner prepareScanner(String fileName) {
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
	private final static int numberOfFileLines(String fileName) {
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
