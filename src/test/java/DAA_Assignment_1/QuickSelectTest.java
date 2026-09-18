package DAA_Assignment_1;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSelectTest {

    @Test
    public void testQuickSelectCorrectness() {
        Random random = new Random();
        for (int t = 0; t < 100; t++) {
            int[] original = random.ints(300, -500, 500).toArray();
            int[] expected = original.clone();
            Arrays.sort(expected);

            int k = random.nextInt(original.length);
            int[] actualArray = original.clone();

            int result = QuickSelect.select(actualArray, k, new Metrics());
            assertEquals(expected[k], result);
        }
    }

    @Test
    public void testInvalidInput() {
        int[] arr = {1, 2, 3};
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(arr, -1, new Metrics()));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(arr, 5, new Metrics()));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{}, 0, new Metrics()));
    }
}