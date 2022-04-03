import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ParallelRadixSort {
    /**
     * Array to sort
     */
    private final int[] unsortedArray;
    private final int[] b;
    /**
     * Number of threads used to sort
     */
    private int nThreads;
    private CyclicBarrier mainBarrier;
    private CyclicBarrier workerBarrier;

    RadixSortWorker[] workers;
    Thread[] t;
    private ParallelRadixSortCommon common;

    public ParallelRadixSort(int nThreads, int useBits, int[] unsortedArray) {
        this.nThreads = nThreads;
        this.unsortedArray = unsortedArray;
        this.common = new ParallelRadixSortCommon(nThreads, useBits);
        this.mainBarrier = new CyclicBarrier(nThreads + 1);
        this.workerBarrier = new CyclicBarrier(nThreads);
        this.workers = new RadixSortWorker[nThreads];
        this.t = new Thread[nThreads];
        this.b = new int[unsortedArray.length];
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
            RadixSortWorker worker = new RadixSortWorker(i, unsortedArray, b, common, readFrom, readFrom + tmpReadSize, workerBarrier, nThreads);
            workers[i] = worker;
            t[i] = new Thread(worker);
            t[i].start();
            readFrom += tmpReadSize;
        }


        for (int i = 0; i < nThreads; i++){
            try {
                t[1].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int[] getResult(){
        return this.unsortedArray;
    }
}
