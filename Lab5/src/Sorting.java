//Lab5
/*
 * Created on Sep 22, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
/*
/**
 * @author Amer Diwan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Sorting {
	private static void swap(int a[], int i, int j) {
		int t = a[i]; //very obvious bug here, does the temp save but doesn't utilize temp save so the file breaks. Lesson learned; resolve warnings too
		a[i] = a[j];
		a[j] = t;
	}
	private static void qSortHelper(int a[], int start, int end) {
		if (start < end) {
			int pivot = a[start];
			int up = start+1;
			int down = (end-1); //down is used for an array that is 5 long. Setting down = 5, then calling a[down] will cause an out of bounds error
			while (up < down) {
				while (a[up] < pivot && up <= down) {
					up++;
				}
				while (a[down] > pivot && down >= up) {
					down--;
				}
				if (up < down) { 
				swap(a, up, down);
					down--; up++;
				}
			}
			swap(a, down, start); 
			qSortHelper(a, start, down);
			qSortHelper(a, down+1, end);
		}
	}
		
	public static int[] qSort(int a[]) {
		int retval[] = new int[a.length];
		// copy array
		for (int i = 0; i < a.length; ++i) {
			retval[i] = a[i];
		}
		qSortHelper(retval, 0, retval.length);
		return retval;
	}
	
	public static void printArray(int a[]) {
		for (int i = 0; i < a.length; ++i) {
			System.out.println("a[" + i + "]=" + a[i]);
		}
	}
	public static void main(String argv[]) {
		int a[] = {1,5,9,3,2};
		printArray(a);
		printArray(qSort(a));
	}
}

