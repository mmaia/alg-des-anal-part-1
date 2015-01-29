package mmaia.coursera.algorithm.week1;

import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

import mmaia.coursera.algorithm.utils.Utils;

/**
 * Does a linear computation of the number of inversions on the given file
 * required to order the numbers contained in it.
 * 
 * @author mmaia - maia.marcos@gmail.com
 */
public class ComputeInversions {
	private final static Logger log = Logger.getLogger(ComputeInversions.class.getName());

	public static void main(String[] args) {
		 String fileName = "IntegerArray_Answer_2407905288.txt";
//		String fileName = "TC1Week1PA1_Answer_3.txt";
//		 String fileName = "TC2Week1PA1_Answer_4.txt";
//		 String fileName = "TC3Week1PA1_Answer_10.txt";
//		 String fileName = "TC4Week1PA1_Answer_5.txt";
//		 String fileName = "TC5Week1PA1_Answer_56.txt";
//		 String fileName = "TC6Week1PA1_Answer_590.txt";
//		 String fileName = "TC7Week1PA1_Answer_2372.txt";

		Scanner scanner = Utils.prepareScanner(fileName);

		// starts counting from 0 so numberOfLines is 1 less the the real one
		// when star counting from 1.
		int numberOfLines = Utils.numberOfFileLines(fileName);

		int[] listOfIntegersForInsertionSort = new int[numberOfLines + 1];

		// loads the array from file lines
		for (int i = 0; i < listOfIntegersForInsertionSort.length; i++) {
			listOfIntegersForInsertionSort[i] = scanner.nextInt();
		}
		int[] listOfIntegersForMergeSort = Arrays.copyOf(listOfIntegersForInsertionSort,
				listOfIntegersForInsertionSort.length);
//		log.info("Finished loading arrays ==>> " + Arrays.toString(listOfIntegersForInsertionSort));
//		log.info("Original Array for insertion sort: " + Arrays.toString(listOfIntegersForInsertionSort));

		// sort the array using the InsertionSort algorithm(linear) Big-O(n^2)
		insertionSort(listOfIntegersForInsertionSort);

		
//		log.info("Original Array for merge sort: " + Arrays.toString(listOfIntegersForMergeSort));
		long initialTime = System.currentTimeMillis();
		mergeSort(listOfIntegersForMergeSort);
		long finalTime = System.currentTimeMillis();
		long totalTime = finalTime - initialTime;
		log.info("Finished sorting array using merge sort, total time:  " + totalTime + " miliseconds");
		//		log.info("Finished sorting array using merge sort, total time:  " + totalTime + " miliseconds " + " sorted Array: " + Arrays.toString(listOfIntegersForMergeSort));
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
//		log.info("Sorted array===========: \n" + Arrays.toString(toSort));
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
//		log.info("Sorted array===========: \n" + Arrays.toString(arrayToSort));
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

	/**
	 * Sort the halfs of the array. This version is not counting the inversions at this point.
	 * @param firstHalf
	 * @param secondHalf
	 * @param arrayToSort
	 */
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
		
		//make sure copied the last element that didn't get processed in the while above(adjustment of this algorithm)
		System.arraycopy(firstHalf, firstCounter, arrayToSort, arraySortCounter, firstHalf.length - firstCounter);
        System.arraycopy(secondHalf, secondCounter, arrayToSort, arraySortCounter, secondHalf.length - secondCounter);
	}
}
