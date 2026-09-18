package DAA_Assignment_1;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTest {

    @Test
    public void testRandomArraysAgainstStandard() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int[] original = random.ints(500, -1000, 1000).toArray();
            int[] expected = original.clone();
            Arrays.sort(expected);

            int[] actual = original.clone();
            MergeSort.sort(actual, new Metrics());
            assertArrayEquals(expected, actual);
        }
    }

    @Test
    public void testEdgeCases() {
        int[] empty = {};
        MergeSort.sort(empty, new Metrics());
        assertArrayEquals(new int[]{}, empty);

        int[] single = {42};
        MergeSort.sort(single, new Metrics());
        assertArrayEquals(new int[]{42}, single);

        int[] duplicates = {5, 5, 5, 5, 5};
        MergeSort.sort(duplicates, new Metrics());
        assertArrayEquals(new int[]{5, 5, 5, 5, 5}, duplicates);

        int[] sorted = {1, 2, 3, 4, 5};
        MergeSort.sort(sorted, new Metrics());
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, sorted);
    }
}