import java.util.Arrays;

public class MainParallelSort {

    private static int totalRuns = 7;
    private static double[] seqTimes = new double[totalRuns];
    private static double[] paraTimes = new double[totalRuns];


    private static int[] testNumbers = new int[]{1000, 10_000, 100_0000, 100_000_0, 100_000_00, 100_000_000};

    public static void main(String[] args) {
        int n, seed, useBits, nThreads;
        try {
            n = Integer.parseInt(args[0]);
            seed = Integer.parseInt(args[1]);
            useBits = Integer.parseInt(args[2]);
            nThreads = Integer.parseInt(args[3]);
        } catch (Exception e) {

            System.out.println("Correct usage is: java MainParallelSort <n> <seed> <useBits> <threads>");
            return;
        }
        if (nThreads == 0) {
            nThreads = Runtime.getRuntime().availableProcessors();
        }


        if (n == 0) {
            for (int i = 0; i < testNumbers.length; i++) {
                sort(testNumbers[i], seed, nThreads, useBits);
            }
        } else {
            sort(n, seed, nThreads, useBits);
        }
    }

    private static void sort(int n, int seed, int nThreads, int useBits) {
        for (int runId = 0; runId < totalRuns; runId++) {
            //Sequential sorting
            int[] a = Oblig4Precode.generateArray(n, seed);
            long seqTime = System.nanoTime();
            RadixSort rs = new RadixSort(useBits);
            a = rs.radixSort(a);
            seqTimes[runId] = (System.nanoTime() - seqTime) / 1000000.0;;

            for (int i = 0; i < a.length - 1; i++) {
                if (a[i] > a[i + 1]) {
                    System.out.println("Error in sorting! Index " + i + " of sequential array has greater value than index " + i + 1);
                }
            }
            //System.out.println("All elements of sequential sorted array is in correct order!");


            int[] b = Oblig4Precode.generateArray(n, seed);
            long paraTime = System.nanoTime();
            ParallelRadixSort parallelRadixSort = new ParallelRadixSort(nThreads, useBits, b);
            parallelRadixSort.sort();
            b = parallelRadixSort.getResult();
            paraTimes[runId] = (System.nanoTime() - paraTime) / 1000000.0;;

            for (int i = 0; i < b.length - 1; i++) {
                if (b[i] > b[i + 1]) {
                    System.out.println("Error in sorting! Index " + i + " of parallel array has greater value than index " + i + 1);
                }
            }
            //System.out.println("All elements of parallel sorted array is in correct order!");


            if (Arrays.equals(a, b)) {
                //System.out.println("Success! Parallel sorted array is equal to sequential sorted array");
            } else {
                System.out.println("Fail! Parallel sorted array is not equal to sequential sorted array");
                return;
            }

            //Write results
            Oblig4Precode.saveResults(Oblig4Precode.Algorithm.SEQ, seed, a);
            Oblig4Precode.saveResults(Oblig4Precode.Algorithm.PAR, seed, b);
        }

        double seqMedian = seqTimes[(seqTimes.length) / 2];
        double paraMedian = paraTimes[(paraTimes.length) / 2];
        System.out.println("Median time of sequential run: " + seqMedian );
        System.out.println("Median time of paralell run: " + paraMedian );
        System.out.println("Total speedup: " + seqMedian / paraMedian + " for number " + n);
    }
}
