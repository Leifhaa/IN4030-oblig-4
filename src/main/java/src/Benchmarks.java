package src;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
public class Benchmarks {
    private static int n = 100_000_0;
    private static int seed = 1;
    private static int bits = 8;

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 3)
    public void testSequential(){
        // Radix sorting
        int[] a = Oblig4Precode.generateArray(n, seed);
        RadixSort rs = new RadixSort(bits);
        a = rs.radixSort(a);
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 3)
    public void testParallel(){
        int[] a = Oblig4Precode.generateArray(n, seed);
        ParallelRadixSort radixSort = new ParallelRadixSort(8, bits, a);
        radixSort.sort();
        a = radixSort.getResult();
    }
}
