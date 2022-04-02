import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ParallelRadixSort {
    /**
     * Array to sort
     */
    private final int[] unsortedArray;
    /**
     * Number of threads used to sort
     */
    private int nThreads;
    private CyclicBarrier mainBarrier;
    private CyclicBarrier workerBarrier;

    RadixSortWorker[] workers = new RadixSortWorker[nThreads];
    Thread[] t = new Thread[nThreads];
    private int[][] allCount = new int[nThreads][];
    private ParallelRadixSortProperties properties;

    public ParallelRadixSort(int nThreads, int useBits, int[] unsortedArray) {
        this.nThreads = nThreads;
        this.unsortedArray = unsortedArray;
        this.properties = new ParallelRadixSortProperties(nThreads, useBits);
        this.mainBarrier = new CyclicBarrier(nThreads + 1);
        this.workerBarrier = new CyclicBarrier(nThreads);
    }


    /**
     * Step a: Finn max verdi i a[]
     *
     * @return
     */
    public void sort() {
        int readSize = unsortedArray.length / nThreads;
        int readFrom = 0;
        for (int i = 0; i < nThreads; i++) {
            int tmpReadSize = readSize;
            if (i < unsortedArray.length % nThreads) {
                tmpReadSize++;
            }
            RadixSortWorker worker = new RadixSortWorker(i, unsortedArray,  properties, readFrom, readFrom + tmpReadSize, workerBarrier);
            workers[i] = worker;
            t[i] = new Thread(worker);
            t[i].start();
            readFrom += tmpReadSize;
        }

        try {
            mainBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
