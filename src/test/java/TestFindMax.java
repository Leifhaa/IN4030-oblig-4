import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TestFindMax {

    @Test
    public void TestRadixSortParallel() {
        int threads = Runtime.getRuntime().availableProcessors();

        int[] intArray = new int[]{ 3,4,7,399,11,17,5,1,2,16 };
        ParallelRadixSort parallelRadixSort = new ParallelRadixSort(threads, 2, intArray);
        parallelRadixSort.sort();
    }

    @Test
    public void testSeqRadixSort(){
        int[] intArray = new int[]{ 3,4,7,399,11,17,5,1,2,16 };
        RadixSort rs = new RadixSort(2);
        int[] res = rs.radixSort(intArray);
    }



    @Test
    public void testSpeedSeq(){
        int[] intArray = Oblig4Precode.generateArray(100_000_000, 1);
        long time = System.nanoTime();
        RadixSort rs = new RadixSort(8);
        int[] res = rs.radixSort(intArray);
        double timeTaken = (System.nanoTime() - time) / 1000000.0;
        System.out.println("Seq took : " + timeTaken);
    }


    @Test
    public void testSpeedPara(){
        int n = 100_000_000;
        int seed = 1;
        int[] intArray = Oblig4Precode.generateArray(n, seed);
        long time1 = System.nanoTime();
        ParallelRadixSort parallelRadixSort = new ParallelRadixSort(8, 8, intArray);
        parallelRadixSort.sort();
        double timeTaken1 = (System.nanoTime() - time1) / 1000000.0;
        System.out.println("Para took : " + timeTaken1);
        int[] result = parallelRadixSort.getResult();
        int[] arraysort = Oblig4Precode.generateArray(n, seed);
        Arrays.sort(arraysort);
        Assert.assertTrue(Arrays.equals(arraysort, result));

    }
}
