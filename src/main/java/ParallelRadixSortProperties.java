public class ParallelRadixSortProperties {

    public ParallelRadixSortProperties(int nThreads, int useBits){
        this.maxCandidates = new int[nThreads];
        this.useBits = useBits;
    }

    /**
     * How many bits we when sorting the digits.
     * Most efficiently between 8-11
     * E.g if we're sorting digits where max digit has 16 bits and we've set useBits to 8 it means that
     * we're first radix sorting the first 8 bits of all numbers as a group (doing counting etc), then the last 8 bits of all numbers as a group,
     */
    private int useBits;

    /**
     * number of bits that is needed to represent max value
     */
    private int maxValueBits = 1;
    /**
     * Max digit in the unsorted array
     */
    private int maxNumber = 0;

    /**
     * Numbers which is candidates for being max number
     */
    private int[] maxCandidates;

    public int getMaxValueBits() {
        return maxValueBits;
    }

    public void setMaxValueBits(int maxValueBits) {
        this.maxValueBits = maxValueBits;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public int[] getMaxCandidates() {
        return maxCandidates;
    }

    public void setMaxCandidates(int[] maxCandidates) {
        this.maxCandidates = maxCandidates;
    }

    public int getUseBits() {
        return useBits;
    }

    public void setUseBits(int useBits) {
        this.useBits = useBits;
    }
}
