package DAA_Assignment_1;

import java.util.Random;

public class QuickSort {
    private static final Random RANDOM = new Random();

    public static void sort(int[] array, Metrics metrics) {
        if (array == null || array.length == 0) return;
        quickSort(array, 0, array.length - 1, 1, metrics);
    }

    private static void quickSort(int[] array, int low, int high, int depth, Metrics metrics) {
        int currentDepth = depth;

        while (low < high) {
            metrics.maxDepth = Math.max(metrics.maxDepth, currentDepth);
            int[] bounds = partition(array, low, high, metrics);
            int left = bounds[0];
            int right = bounds[1];

            if (left - low < high - right) {
                quickSort(array, low, left - 1, currentDepth + 1, metrics);
                low = right + 1;
            } else {
                quickSort(array, right + 1, high, currentDepth + 1, metrics);
                high = left - 1;
            }
            currentDepth++;
        }
        metrics.maxDepth = Math.max(metrics.maxDepth, currentDepth);
    }

    public static int[] partition(int[] array, int low, int high, Metrics metrics) {
        int pivotIndex = low + RANDOM.nextInt(high - low + 1);
        swap(array, low, pivotIndex);
        int pivot = array[low];

        int left = low;
        int right = high;
        int i = low;

        while (i <= right) {
            metrics.comparisons++;
            if (array[i] < pivot) {
                swap(array, left++, i++);
            } else {
                metrics.comparisons++;
                if (array[i] > pivot) {
                    swap(array, i, right--);
                } else {
                    i++;
                }
            }
        }
        return new int[]{left, right};
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}