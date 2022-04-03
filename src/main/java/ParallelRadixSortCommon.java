import java.util.HashMap;

public class ParallelRadixSortCommon {

    public ParallelRadixSortCommon(int nThreads, int useBits){
        this.maxCandidates = new int[nThreads];
        this.useBits = useBits;
        this.allCount = new int[nThreads][];
        this.readings = new HashMap<>();
    }

    /**
     * How many bits we when sorting the digits.
     * Most efficiently between 8-11
     * E.g if we're sorting digits where max digit has 16 bits and we've set useBits to 8 it means that
     * we're first radix sorting the first 8 bits of all numbers as a group (doing counting etc), then the last 8 bits of all numbers as a group,
     */
    private int useBits;

    /**
     * Max digit in the unsorted array
     */
    private int maxNumber = 0;

    /**
     * Numbers which is candidates for being max number
     */
    private int[] maxCandidates;

    /**
     * Counting of digits per thread
     */
    private int[][] allCount;

    private int numOfPositions;

    public int[] sumCount;
    public int[] digitPointer;

    public int getMaxNumber() {
        return maxNumber;
    }

    /**
     * The range which each thread should sum from
     */
    public HashMap<Integer, int[]> readings;

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public int[] getMaxCandidates() {
        return maxCandidates;
    }


    public void setMaxCandidate(int threadId, int cadidate){
        this.maxCandidates[threadId] = cadidate;
    }

    public int getUseBits() {
        return useBits;
    }

    public void setUseBits(int useBits) {
        this.useBits = useBits;
    }

    public int getNumOfPositions() {
        return numOfPositions;
    }

    public void setNumOfPositions(int numOfPositions) {
        this.numOfPositions = numOfPositions;
    }

    public void incrementNumOfPositions(){
        numOfPositions++;
    }

    public int[][] getAllCount() {
        return allCount;
    }

    public void addReadSize(int threadId,int fromElement, int toElement, int fromColumn, int toColumn){
        this.readings.put(threadId, new int[]{fromElement,toElement, fromColumn, toColumn});
    }

    public int[] getReadSize(int threadId){
        return this.readings.get(threadId);
    }
}
