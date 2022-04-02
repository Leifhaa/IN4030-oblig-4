import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class RadixSortWorker implements Runnable {
    private int threadId;
    private final int[] unsortedArray;
    private ParallelRadixSortCommon common;
    private final int readFromIndex;
    private final int readToIndex;
    private final CyclicBarrier workerBarrier;
    private int tmpMax = 0;
    int[] count;

    public RadixSortWorker(int threadId, int[] unsortedArray, ParallelRadixSortCommon common, int readFromIndex, int readToIndex, CyclicBarrier workerBarrier) {
        this.threadId = threadId;
        this.unsortedArray = unsortedArray;
        this.common = common;
        this.readFromIndex = readFromIndex;
        this.readToIndex = readToIndex;
        this.workerBarrier = workerBarrier;
    }

    @Override
    public void run() {
        // Step 1. Find max
        findMax();

        //Step 2. Count the different digit's in the array
        countOccurences();
    }


    private void findMax() {
        //Step 1. find max
        for (int i = readFromIndex; i < readToIndex; i++) {
            if (unsortedArray[i] > tmpMax) {
                tmpMax = unsortedArray[i];
            }
        }
        common.setMaxCandidate(threadId, tmpMax);
        try {
            workerBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        //All threads has found the max number for their range. Thread 0 will synchronize (suggested as good method in lecture 4)
        if (threadId == 0) {
            int[] maxCandidates = common.getMaxCandidates();
            for (int i = 0; i < maxCandidates.length; i++) {
                if (maxCandidates[i] > common.getMaxNumber()) {
                    common.setMaxNumber(maxCandidates[i]);
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
        while (common.getMaxNumber() >= (1L << numBitsMax))
            numBitsMax++;


        // Substep: Finding the number of positions needed to represent the max value
        common.setNumOfPositions(numBitsMax / common.getUseBits());
        if (numBitsMax % common.getUseBits() != 0) common.incrementNumOfPositions();


        // Substep: If useBits is larger than numBitsMax,
        // set useBits equal to numBitsMax to save space.
        if (numBitsMax < common.getUseBits()) common.setUseBits(numBitsMax);
    }


    private void countOccurences() {

        // Substep: Creating the mask and initialising the shift variable,
        // both of whom are used to extract the digits.
        int mask = (1 << common.getUseBits()) - 1;
        int shift = 0;

        //Each thread will count portion of the unsorted array
        for (int i = 0; i < common.getNumOfPositions(); i++) {
            countingSort(mask, shift);
            shift += common.getUseBits();

            //Todo
            // Setting array a to be the array to be sorted again
            //int[] temp = a;
            //a = b;
            //b = temp;
        }

        //Todo: Upload count to common data

    }

    private void countingSort(int mask, int shift) {
        // STEP B : Count the number of occurrences of each digit in a specific position.
        int[] count = new int[mask + 1];

        for (int i = readFromIndex; i < readToIndex; i++) {
            count[(unsortedArray[i] >> shift) & mask]++;
        }


        /**
         * Når tråd i er ferdig med tellinga, henger den sin count[] opp i den doble int-arrayen som da
         * vil inneholde alle opptellingene fra alle trådene, slik: allCount[i] = count;
         */
        common.getAllCount()[threadId] = count;


        //Synchronize
        try {
            workerBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        /**
         * All threads finished counting. All threads has to calculate total per digit
         * (Step C)
         */

        (Nå skal vi dele opp arrayen allCount[][] etter verdier i a[], slik at tråd0 får de n/k
        første elementene i sumCount[] og de n/k første kolonnene i allCount[][] ,
        tråd1 får de neste n/k elementene i sumCount[] og kolonnene i allCount[][] ,…, osv)


        try {
            workerBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }




    }
}
