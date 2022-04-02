import java.util.concurrent.CyclicBarrier;

public class ParallelRadixSort {
    private int nThreads;
    /**
     * How many bits we when sorting the digits.
     * Most efficiently between 8-11
     * E.g if we're sorting digits where max digit has 16 bits and we've set useBits to 8 it means that
     * we're first radix sorting the first 8 bits of all numbers as a group (doing counting etc), then the last 8 bits of all numbers as a group,
     */
    private int useBits;
    private CyclicBarrier barrier;

    /**
     * number of bits that is needed to represent max value
     */
    int maxValueBits = 1;


    public ParallelRadixSort(int nThreads, int useBits) {
        this.nThreads = nThreads;
        this.useBits = useBits;
    }

    public void sort(int[] unsortedArray) {
        /**
         * Step a: find max of a
         */
        int max = findMax(unsortedArray);

    }

    /**
     * Step a: Finn max verdi i a[]
     *
     * @param unsortedArray
     * @return
     */
    public int findMax(int[] unsortedArray) {
        int result = 0;
        Thread[] t = new Thread[nThreads];
        FindMaxWorker[] workers = new FindMaxWorker[nThreads];
        int readSize = unsortedArray.length / nThreads;
        int readFrom = 0;
        for (int i = 0; i < nThreads; i++) {
            int tmpReadSize = readSize;
            if (i < unsortedArray.length % nThreads) {
                tmpReadSize++;
            }
            FindMaxWorker worker = new FindMaxWorker(unsortedArray, readFrom, readFrom + tmpReadSize);
            workers[i] = worker;
            t[i] = new Thread(worker);
            t[i].start();
            readFrom += tmpReadSize;
        }

        //Wait for threads to finish
        for (int i = 0; i < nThreads; i++) {
            try {
                t[i].join();
                int threadMax = workers[i].getMax();
                if (result < threadMax) {
                    result = threadMax;
                }
            } catch (Exception e) {
                System.out.println("Exception : " + e);
            }
        }
        return result;
    }


    /**
     * Edits the useBits in case they are bad
     */
    private void setFinalBits(int maxNumber) {
        while (maxNumber >= (1L << maxValueBits))
            maxValueBits++;

        // Substep: Finding number of bits that is needed to represent max value
        int numBitsMax = 1;
        while (maxValueBits >= (1L << numBitsMax))
            numBitsMax++;


        // Substep: Finding the number of positions needed to represent the max value
        int numOfPositions = numBitsMax / useBits;
        if (numBitsMax % useBits != 0) numOfPositions++;


        // Substep: If useBits is larger than numBitsMax,
        // set useBits equal to numBitsMax to save space.
        if (numBitsMax < useBits) useBits = numBitsMax;
    }


    /**
     * Step b: opptelling av ulike sifferverdier i a[]
     */
    public void countOccurrenceOnDigit(int[] unsortedArray) {
        int[][] allCount = new int[nThreads][];
        int[] sumCount = new int[maxValueBits];
        Thread[] t = new Thread[nThreads];

        int readSize = unsortedArray.length / nThreads;
        int readFrom = 0;
        for (int i = 0; i < nThreads; i++) {
            int tmpReadSize = readSize;
            if (i < unsortedArray.length % nThreads) {
                tmpReadSize++;
            }
            DigitOccurrenceWorker worker = new DigitOccurrenceWorker(unsortedArray, readFrom, readFrom + tmpReadSize, this.maxValueBits);
            t[i] = new Thread(worker);
            t[i].start();
            readFrom += tmpReadSize;
        }
    }
}
