public class DigitOccurrenceWorker implements Runnable {
    private int[] unsortedArray;
    private int readFrom;
    private int readTo;
    private final int[] count;

    public DigitOccurrenceWorker(int[] unsortedArray, int readFrom, int readTo, int maxValueBits){
        this.unsortedArray = unsortedArray;
        this.readFrom = readFrom;
        this.readTo = readTo;
        count = new int[maxValueBits];
    }

    @Override
    public void run() {
        for (int i = readFrom; i < readTo; i++){

        }
    }
}
