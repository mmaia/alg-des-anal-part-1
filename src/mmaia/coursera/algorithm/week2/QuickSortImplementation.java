package mmaia.coursera.algorithm.week2;

import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

import mmaia.coursera.algorithm.utils.Utils;

public class QuickSortImplementation {

	private final static Logger log = Logger.getLogger(QuickSortImplementation.class.getName());
	private static int[] listOfIntegersForPivotFirst;
	private static int pivotPosition;

	public static void main(String[] args) {
		// String fileName = "QuickSort.txt";
		String fileName = "Week2TC1_Size10_Answers_25_29_21.txt";
		// String fileName = "Week2TC2_Size100_Answers_615_587_518.txt";
		// String fileName = "Week2TC3_Size1000_Answers_10297_10184_8921.txt";

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

		quickSortPivotFirst(listOfIntegersForPivotFirst);

	}

	private static void quickSortPivotFirst(int[] listOfIntegersForPivotFirst) {
		log.info("Start sorting with Pivot first");
		// initializes pivot to be first element of array;
		pivotPosition = 0;
		quickSort1(0, listOfIntegersForPivotFirst.length - 1);
	}

	private static void quickSort1(int posPartitioned, int posUnpartitioned) {
		int partitionedCounter = posPartitioned; // i
		int unpartitionedCounter = posUnpartitioned; // j
		int pivot = listOfIntegersForPivotFirst[pivotPosition];
		while (partitionedCounter <= unpartitionedCounter) {
			while (listOfIntegersForPivotFirst[partitionedCounter] < pivot) {
				partitionedCounter++;
			}
			while (listOfIntegersForPivotFirst[unpartitionedCounter] > pivot) {
				unpartitionedCounter--;
			}

			if (partitionedCounter <= unpartitionedCounter) {
				swapPositions(partitionedCounter, unpartitionedCounter, listOfIntegersForPivotFirst);
				partitionedCounter++;
				unpartitionedCounter--;
			}
		}
		// recursion
		if (posPartitioned < unpartitionedCounter) {
			quickSort1(posPartitioned, unpartitionedCounter);
		}
		if (partitionedCounter < posUnpartitioned) {
			quickSort1(partitionedCounter, posUnpartitioned);
		}

		log.info("Finished sorting with Pivot first");
	}

	private static void swapPositions(int i, int j, int[] theList) {
		int temp = theList[i];
		theList[i] = theList[j];
		theList[j] = temp;
	}
}
