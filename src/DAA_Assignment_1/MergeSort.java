package DAA_Assignment_1;

public class MergeSort {

    public static void sort(int[] array, Metrics metrics) {
        if (array == null || array.length == 0) return;
        int[] buffer = new int[array.length];
        mergeSort(array, buffer, 0, array.length - 1, 1, metrics);
    }

    private static void mergeSort(int[] array, int[] buffer, int low, int high, int depth, Metrics metrics) {
        metrics.maxDepth = Math.max(metrics.maxDepth, depth);

        if (high - low < 15) {
            insertionSort(array, low, high, metrics);
            return;
        }

        int mid = low + (high - low) / 2;
        mergeSort(array, buffer, low, mid, depth + 1, metrics);
        mergeSort(array, buffer, mid + 1, high, depth + 1, metrics);

        metrics.comparisons++;
        if (array[mid] <= array[mid + 1]) {
            return;
        }

        merge(array, buffer, low, mid, high, metrics);
    }

    private static void merge(int[] array, int[] buffer, int low, int mid, int high, Metrics metrics) {
        for (int k = low; k <= high; k++) {
            buffer[k] = array[k];
        }

        int i = low;
        int j = mid + 1;
        int k = low;

        while (i <= mid && j <= high) {
            metrics.comparisons++;
            if (buffer[i] <= buffer[j]) {
                array[k++] = buffer[i++];
            } else {
                array[k++] = buffer[j++];
            }
        }

        while (i <= mid) {
            array[k++] = buffer[i++];
        }
    }

    private static void insertionSort(int[] array, int low, int high, Metrics metrics) {
        for (int i = low + 1; i <= high; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= low) {
                metrics.comparisons++;
                if (array[j] > key) {
                    array[j + 1] = array[j];
                    j--;
                } else {
                    break;
                }
            }
            array[j + 1] = key;
        }
    }
}