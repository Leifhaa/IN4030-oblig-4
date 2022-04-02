import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class RadixSortWorker implements Runnable {
    private int threadId;
    private final int[] unsortedArray;
    private ParallelRadixSortProperties properties;
    private final int readFromIndex;
    private final int readToIndex;
    private final CyclicBarrier workerBarrier;
    private int tmpMax = 0;
    int[] count;

    public RadixSortWorker(int threadId, int[] unsortedArray, ParallelRadixSortProperties properties, int readFromIndex, int readToIndex, CyclicBarrier workerBarrier) {
        this.threadId = threadId;
        this.unsortedArray = unsortedArray;
        this.properties = properties;
        this.readFromIndex = readFromIndex;
        this.readToIndex = readToIndex;
        this.workerBarrier = workerBarrier;
    }

    @Override
    public void run() {
        // Step 1. Find max
        findMax();

        //Step 2. Count the different digit's in the array
    }


    private void findMax() {
        //Step 1. find max
        for (int i = readFromIndex; i < readToIndex; i++){
            if (unsortedArray[i] > tmpMax){
                tmpMax = unsortedArray[i];
            }
        }
        try {
            workerBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        //All threads has found the max number for their range. Thread 0 will synchronize (suggested as good method in lecture 4)
        if (threadId == 0){
            int[] maxCandidates = properties.getMaxCandidates();
            for (int i = 0; i < maxCandidates.length; i++){
                if (maxCandidates[i] > properties.getMaxNumber()){
                    properties.setMaxNumber(maxCandidates[i]);
                }
            }
            //Set max number & correct useBits if tey are bad
            setFinalBits();
        }
        try {
            workerBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    /**
     * Edits the useBits in case they are bad
     */
    private void setFinalBits() {
        // Substep: Finding number of bits that is needed to represent max value
        int numBitsMax = 1;
        while (properties.getMaxNumber() >= (1L << numBitsMax))
            numBitsMax++;


        // Substep: Finding the number of positions needed to represent the max value
        int numOfPositions = numBitsMax / properties.getUseBits();
        if (numBitsMax % properties.getUseBits() != 0) numOfPositions++;


        // Substep: If useBits is larger than numBitsMax,
        // set useBits equal to numBitsMax to save space.
        if (numBitsMax < properties.getUseBits()) properties.setUseBits(numBitsMax);
    }
}
