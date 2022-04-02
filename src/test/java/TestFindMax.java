import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TestFindMax {

    @Test
    public void TestRadixSortParallel() {
        int threads = Runtime.getRuntime().availableProcessors();

        int[] intArray = new int[]{ 1,2,3,4,5,6,7,8,9,399 };
        ParallelRadixSort parallelRadixSort = new ParallelRadixSort(threads, 8, intArray);
        parallelRadixSort.sort();
    }

    @Test
    public void testSeqRadixSort(){
        int[] intArray = new int[]{ 1,2,3,4,5,6,7,8,9,399 };
        RadixSort rs = new RadixSort(8);
        int[] res = rs.radixSort(intArray);
    }

}
