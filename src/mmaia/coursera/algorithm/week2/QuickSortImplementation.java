package mmaia.coursera.algorithm.week2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;

import mmaia.coursera.algorithm.utils.Utils;

public class QuickSortImplementation {

	private final static Logger log = Logger.getLogger(QuickSortImplementation.class.getName());
	static int[] listOfIntegersForPivotFirst;

	public static void main(String[] args) {
//		String fileName = "QuickSort.txt";
		 String fileName = "Week2TC1_Size10_Answers_25_29_21.txt";
		// String fileName = "Week2TC2_Size100_Answers_615_587_518.txt";
		// String fileName = "Week2TC3_Size1000_Answers_10297_10184_8921.txt";
		// String fileName = "IntegerArray_Answer_2407905288.txt";
		Scanner scanner = Utils.prepareScanner(fileName);

		// starts counting from 0 so numberOfLines is 1 less the the real one
		// when star counting from 1.
		int numberOfLines = Utils.numberOfFileLines(fileName);

		listOfIntegersForPivotFirst = new int[numberOfLines + 1];

		// loads the array from file lines
		for (int i = 0; i < listOfIntegersForPivotFirst.length; i++) {
			listOfIntegersForPivotFirst[i] = scanner.nextInt();
		}

		log.info("Loaded array size: " + (numberOfLines + 1) + " array data: "
				+ Arrays.toString(listOfIntegersForPivotFirst));
		int[] listOfIntegersForPivotLast = Arrays.copyOf(listOfIntegersForPivotFirst,
				listOfIntegersForPivotFirst.length);

		int[] listOfIntegersForPivotMedianOfThree = Arrays.copyOf(listOfIntegersForPivotFirst,
				listOfIntegersForPivotFirst.length);

		int[] listOfIntegersForListImplementation = Arrays.copyOf(listOfIntegersForPivotFirst,
				listOfIntegersForPivotFirst.length);

		quickSortPivotFirst(listOfIntegersForPivotFirst, 0, listOfIntegersForPivotFirst.length - 1);
		System.out.println("Sorted array ======================= : " + Arrays.toString(listOfIntegersForPivotFirst));

		// programcreek.com implementation(code taken there). Very fast.
		// long initialTime = System.currentTimeMillis();
		// quickSort(listOfIntegersForPivotFirst, 0,
		// listOfIntegersForPivotFirst.length - 1);
		// long finalTime = System.currentTimeMillis();
		// long totalTime = finalTime - initialTime;
		// printArray(listOfIntegersForPivotFirst);
		// log.info("Using array the time was: " + totalTime);

		// ------------------------- call to implementation that uses lists
		// starts here
		// working with Lists instead of arrays of int, performance is not as
		// good but code is clearer and easier to understand.
		// transform the array of int[] into arrayList
		// List<Integer> listOfIntegers = new
		// ArrayList<Integer>(listOfIntegersForListImplementation.length);
		// for (int i = 0; i < listOfIntegersForListImplementation.length; i++)
		// {
		// listOfIntegers.add(listOfIntegersForListImplementation[i]);
		// }
		// calls quick sort passing the list prints the time
		// initialTime = System.currentTimeMillis();
		// List<Integer> sortedList = quickSortPivotFirst(listOfIntegers);
		// Collections.sort(listOfIntegers); //the java implementation is faster
		// than mine
		// finalTime = System.currentTimeMillis();
		// totalTime = finalTime - initialTime;
		// List<Integer> sortedList = listOfIntegers;
		// StringBuffer buffer = new StringBuffer("Sorted list ==>> \n");
		// for (Integer object : sortedList) {
		// buffer.append(" " + object + "\n");
		// }
		// log.info("total time to process using List and first element as pivot ==> "
		// + totalTime + " miliseconds");
	}

	// ------------------------------------END Of main method -
	// ---------------------------------------------------------

	// counter to keep track of inversions
	static long count = 0;

	private static void quickSortPivotFirst(int[] arrayOfIntegersToBeSorted, int initialPosition, int finalPosition) {
		if (arrayOfIntegersToBeSorted == null || arrayOfIntegersToBeSorted.length == 0)
			return;

		if (initialPosition >= finalPosition)
			return;

		// first element as pivot
		int pivot = arrayOfIntegersToBeSorted[initialPosition];// first element
																// of array
		System.out.println("Pivot in this run: " + pivot);
		// i keeps track of first element after pivot
		int i = initialPosition + 1;

		// j keeps track of unpartitioned part of array starting of first
		// element after pivot
		int j = initialPosition + 1;

		System.out.println("Values of i and j respectively: " + i + ", " + j);

		while (j <= finalPosition) {
			int currentNumber = arrayOfIntegersToBeSorted[j];
			if (currentNumber > pivot) {
				j++;
			} else if (currentNumber < pivot) {
				swapPositions(i, j, arrayOfIntegersToBeSorted);
				i++;
				j++;

			}
		}
		System.out.println("Values of i and j respectively: " + i + ", " + j);
		// swap pivot into its position
		swapPositions(initialPosition, i - 1, arrayOfIntegersToBeSorted);
		System.out.println("After pivot in-place positioning" + Arrays.toString(arrayOfIntegersToBeSorted));

		quickSort(arrayOfIntegersToBeSorted, 0, pivot - 1);


		quickSort(arrayOfIntegersToBeSorted, i, arrayOfIntegersToBeSorted.length - 1);
		System.out.println("inversion count " + count);
	}

	/**
	 * swap positions of two elements in one given array of int
	 * 
	 * @param i
	 *            - pos 1
	 * @param j
	 *            - pos 2
	 * @param theList
	 *            - array with elementos to be swaped
	 */
	private static void swapPositions(int i, int j, int[] theList) {
		int temp = theList[i];
		theList[i] = theList[j];
		theList[j] = temp;
	}

	// ---method taken from internet
	// credits to programcreek.com:
	// http://www.programcreek.com/2012/11/quicksort-array-in-java/
	public static void quickSort(int[] arr, int low, int high) {
		if (arr == null || arr.length == 0)
			return;

		if (low >= high)
			return;

		// pick the first element as the pivot
		int middle = low + (high - low) / 2;
		int pivot = arr[middle];
		// System.out.println("pivot value " + pivot);

		// make left < pivot and right > pivot
		int i = low, j = high;
		while (i <= j) {
			while (arr[i] < pivot) {
				i++;
			}

			while (arr[j] > pivot) {
				j--;
			}
			// swap here
			if (i <= j) {
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}

		// System.out.println("Values of low and j " + low + ", " + j);
		// System.out.println("Values of high and i " + high+", " + i);
		// recursively sort two sub parts
		if (low < j) {
			quickSort(arr, low, j);
		}

		if (high > i) {
			quickSort(arr, i, high);
		}
	}

	public static void printArray(int[] x) {
		for (int a : x)
			System.out.print(a + " ");
		// System.out.println("Comparsion count: " + count);
	}

	// ----Implementation using Lists starts here
	// ----------------------------------------------------------------------------

	/**
	 * quick sort implementation using first element as pivot, implemented using
	 * java Lists, this code is simpler and clearer to read than implementing
	 * with arrays but it's running time is much longer(average 10 times longer
	 * than primitive array implementation with a median pivot and average 2 to
	 * 3 times longer than Collections.sort implementation from JDK) can be
	 * easily changed to use last element or middle element as pivot as well.
	 * This is just for study pourposes in a real situation the
	 * Collections.sort(List) method should be used instead as it's faster and
	 * more reliable.
	 * 
	 * @param integersList
	 *            - Unsorted list of integers
	 * @return The sorted list.
	 */
	private static List<Integer> quickSortPivotFirst(List<Integer> integersList) {
		// log.info("Start sorting with Pivot first");
		if (integersList.size() < 2)
			return integersList;

		// get the first element as pivot for studies purposes, usually should
		// get the middle one.
		int pivot = integersList.get(0);

		List<Integer> beforePivot = new ArrayList<Integer>();
		List<Integer> pivotList = new ArrayList<Integer>(1);
		List<Integer> afterPivot = new ArrayList<Integer>();

		// divide by pivot reference
		for (Integer currentInteger : integersList) {
			if (currentInteger > pivot) {
				afterPivot.add(currentInteger);
			} else if (currentInteger < pivot) {
				beforePivot.add(currentInteger);
			} else {
				pivotList.add(currentInteger);
			}

		}
		return wrapup(quickSortPivotFirst(beforePivot), pivotList, quickSortPivotFirst(afterPivot));
	}

	/*
	 * wrap up and concatenates the lists from quick sort implementation.
	 */
	private final static List<Integer> wrapup(List<Integer> beforePivot, List<Integer> pivot, List<Integer> afterPivot) {
		List<Integer> list = new ArrayList<Integer>();

		list.addAll(beforePivot);

		list.addAll(pivot);

		list.addAll(afterPivot);

		return list;
	}
}
