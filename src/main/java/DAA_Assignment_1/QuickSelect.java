package DAA_Assignment_1;

public class QuickSelect {

    public static int select(int[] array, int k, Metrics metrics) {
        if (array == null || array.length == 0 || k < 0 || k >= array.length) {
            throw new IllegalArgumentException("Array is empty or k is out of range");
        }

        int low = 0, high = array.length - 1;

        while (low <= high) {
            int[] bounds = QuickSort.partition(array, low, high, metrics);
            int left = bounds[0];
            int right = bounds[1];

            if (k >= left && k <= right) {
                return array[left];
            } else if (k < left) {
                high = left - 1;
            } else {
                low = right + 1;
            }
        }
        return -1;
    }
}