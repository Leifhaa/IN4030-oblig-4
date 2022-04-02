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

}
