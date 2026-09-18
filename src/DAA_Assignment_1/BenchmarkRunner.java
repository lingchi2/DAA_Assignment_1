package DAA_Assignment_1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class BenchmarkRunner {
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        System.out.println("Starting JVM Warm-up...");
        warmUp();
        System.out.println("Warm-up finished. Starting benchmark...\n");
        runBenchmark();
    }

    private static void warmUp() {
        for (int i = 0; i < 20; i++) {
            int[] arr = generateArray(10_000, "random");
            MergeSort.sort(arr.clone(), new Metrics());
            QuickSort.sort(arr.clone(), new Metrics());
            QuickSelect.select(arr.clone(), arr.length / 2, new Metrics());
        }
    }

    public static void runBenchmark() {
        int[] sizes = {1_000, 10_000, 100_000, 1_000_000};
        String[] types = {"random", "sorted", "duplicates"};
        String[] algorithms = {"MergeSort", "QuickSort", "QuickSelect"};

        try (PrintWriter writer = new PrintWriter(new FileWriter("results.csv"))) {
            writer.println("algorithm,input,n,time_ms,comparisons,max_depth");

            for (String algo : algorithms) {
                for (String type : types) {
                    for (int n : sizes) {
                        Metrics[] runs = new Metrics[5];

                        for (int i = 0; i < 5; i++) {
                            int[] array = generateArray(n, type);
                            Metrics metrics = new Metrics();

                            long startTime = System.nanoTime();

                            switch (algo) {
                                case "MergeSort":
                                    MergeSort.sort(array, metrics);
                                    break;
                                case "QuickSort":
                                    QuickSort.sort(array, metrics);
                                    break;
                                case "QuickSelect":
                                    QuickSelect.select(array, n / 2, metrics);
                                    break;
                            }

                            metrics.timeNano = System.nanoTime() - startTime;
                            runs[i] = metrics;
                        }

                        Arrays.sort(runs, Comparator.comparingLong(m -> m.timeNano));
                        Metrics median = runs[2];

                        double timeMs = median.timeNano / 1_000_000.0;
                        writer.printf("%s,%s,%d,%.3f,%d,%d%n",
                                algo, type, n, timeMs, median.comparisons, median.maxDepth);
                        System.out.printf("Done: %11s | %10s | N = %7d | %7.3f ms%n",
                                algo, type, n, timeMs);
                    }
                }
            }
            System.out.println("\nBenchmark finished. Saved to results.csv");
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }

    private static int[] generateArray(int n, String type) {
        int[] array = new int[n];
        if (type.equals("random")) {
            for (int i = 0; i < n; i++) {
                array[i] = RANDOM.nextInt();
            }
        } else if (type.equals("duplicates")) {
            for (int i = 0; i < n; i++) {
                array[i] = RANDOM.nextInt(10);
            }
        } else if (type.equals("sorted")) {
            for (int i = 0; i < n; i++) {
                array[i] = i;
            }
        }
        return array;
    }
}