package kamisado.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class UniqueRandom2DArrayFactory {
	
	public static int[][] get8x8UniqueNumbersRandomArray() {
		int[][] array = {{0,1,2,3,4,5,6,7},
				         {1,2,3,4,5,6,7,0},
				         {2,3,4,5,6,7,0,1},
				         {3,4,5,6,7,0,1,2},
				         {4,5,6,7,0,1,2,3},
				         {5,6,7,0,1,2,3,4},
				         {6,7,0,1,2,3,4,5},
				         {7,0,1,2,3,4,5,6}};
		
		//shuffle 100 times
		for(int i = 0; i<=100 ; i++) {
			int rand1 = getRandomInt(0, 7);
			int rand2 = getRandomInt(0, 7);
			
			swapRows(rand1, rand2, array);
			swapColumns(rand1, rand2, array);
			transpose(array);
		}
		
		
		return array;
	}
	
	private static int getRandomInt(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	private static void swapRows(int rowIndex1, int rowIndex2, int[][] array) {
		int[] row1 = array[rowIndex1];
		int[] row2 = array[rowIndex2];
		
		array[rowIndex1] = row2;
		array[rowIndex2] = row1;
	}
	
	private static void swapColumns(int colIndex1, int colIndex2, int[][] array) {
		for(int i = 0 ; i < array.length ; i++) {
			int temp = array[i][colIndex1];
			array[i][colIndex1] = array[i][colIndex2];
			array[i][colIndex2] = temp;
		}
	}
	
	private static void transpose(int[][] array) {
		int[][] temp = cloneArray(array);
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j<array[i].length ; j++) {
				temp[j][i] = array[i][j];
			}
		}
		array = cloneArray(temp);
	}
	
	private static int[][] cloneArray(int[][] src) {
	    int length = src.length;
	    int[][] target = new int[length][src[0].length];
	    for (int i = 0; i < length; i++) {
	        System.arraycopy(src[i], 0, target[i], 0, src[i].length);
	    }
	    return target;
	}
	
	private static void printArray(int[][] src) {
		for(int i = 0 ; i < src.length; i++) {
			for (int j = 0 ; j< src[i].length; j++) {
				System.out.print(src[i][j]+" ");
			}
			System.out.print("\n");
		}
	}

}
