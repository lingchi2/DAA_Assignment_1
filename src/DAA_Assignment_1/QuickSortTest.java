package DAA_Assignment_1;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {

    @Test
    public void testRandomArraysAgainstStandard() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int[] original = random.ints(500, -1000, 1000).toArray();
            int[] expected = original.clone();
            Arrays.sort(expected);

            int[] actual = original.clone();
            QuickSort.sort(actual, new Metrics());
            assertArrayEquals(expected, actual);
        }
    }

    @Test
    public void testRecursionDepthOnSortedArray() {
        int n = 100_000;
        int[] sorted = new int[n];
        for (int i = 0; i < n; i++) {
            sorted[i] = i;
        }

        Metrics metrics = new Metrics();
        QuickSort.sort(sorted, metrics);

        double maxAllowedDepth = 2 * (Math.log(n) / Math.log(2));
        assertTrue(metrics.maxDepth <= maxAllowedDepth,
                "Recursion depth " + metrics.maxDepth + " exceeded allowed limit " + maxAllowedDepth);
    }
}