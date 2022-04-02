import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TestFindMax {

    @Test
    public void TestRadixSortParallel() {
        int threads = Runtime.getRuntime().availableProcessors();

        int[] intArray = new int[]{ 1,2,3,4,5,6,7,8,9,255 };
        ParallelRadixSort parallelRadixSort = new ParallelRadixSort(threads, 1, intArray);
        parallelRadixSort.sort();
    }

}
