import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TestFindMax {

    @Test
    public void TestGetFindMaxHighestLast() {
        int threads = Runtime.getRuntime().availableProcessors();

        int[] intArray = new int[]{ 1,2,3,4,5,6,7,8,9,10 };
        ParallelRadixSort parallelRadixSort = new ParallelRadixSort(threads);
        int result = parallelRadixSort.findMax(intArray);
        Assert.assertEquals(10, result);
    }


    @Test
    public void TestGetFindMaxHighestFirst() {
        int threads = Runtime.getRuntime().availableProcessors();

        int[] intArray = new int[]{ 255,2,3,4,5,6,7,8,9,0 };
        ParallelRadixSort parallelRadixSort = new ParallelRadixSort(threads);
        int result = parallelRadixSort.findMax(intArray);
        Assert.assertEquals(255, result);
    }


    @Test
    public void TestFindMaxRandom() {
        int threads = Runtime.getRuntime().availableProcessors();
        int[] intArray = Oblig4Precode.generateArray(100000, 0);
        ParallelRadixSort parallelRadixSort = new ParallelRadixSort(threads);
        int result = parallelRadixSort.findMax(intArray);
        int resultCheck = Arrays.stream(intArray).max().getAsInt();
        Assert.assertEquals(resultCheck, result);
    }


    @Test
    public void TestSpeedup() {
        int threads = Runtime.getRuntime().availableProcessors();
        int[] intArray = Oblig4Precode.generateArray(100000, 0);
        ParallelRadixSort parallelRadixSort = new ParallelRadixSort(threads);
        int result = parallelRadixSort.findMax(intArray);
        int resultCheck = Arrays.stream(intArray).max().getAsInt();
        Assert.assertEquals(resultCheck, result);
    }
}
