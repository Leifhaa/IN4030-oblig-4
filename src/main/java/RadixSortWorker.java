public class RadixSortWorker implements Runnable {
    private int[] unsortedArray;
    private int readFromIndex;
    private int readToIndex;
    private int tmpMax = 0;

    public RadixSortWorker(int[] unsortedArray, int readFromIndex, int readToIndex) {
        this.unsortedArray = unsortedArray;
        this.readFromIndex = readFromIndex;
        this.readToIndex = readToIndex;
    }

    public int getMax(){
        return tmpMax;
    }

    @Override
    public void run() {
        for (int i = readFromIndex; i < readToIndex; i++){
            if (unsortedArray[i] > tmpMax){
                tmpMax = unsortedArray[i];
            }
        }
    }
}
