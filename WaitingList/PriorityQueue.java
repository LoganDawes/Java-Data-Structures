// Implements basic PriorityQueue methods
public class PriorityQueue implements PriorityQueueADT<Record> {
    private Record[] queue;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    // Arguments: none
    // Returns: none
    // Desc: Constructor for basic priority queue
    public PriorityQueue() {
        this.queue = new Record[DEFAULT_CAPACITY];
        this.size = 0;
    }

    // Arguments: Record
    // Returns: Record
    // Desc: insert for priority queue, doesn't maintain heap properties
    @Override
    public Record insert(Record record) throws IllegalArgumentException {
        queue[size] = record;
        size++;
        return record;
    }

    // Arguments: none
    // Returns: Record
    // Desc: returns record with highest priority, has to find manually. Heap overrides by finding root
    @Override
    public Record min() {
        if (isEmpty()) return null;

        Record minRecord = queue[0];
        for (int i = 1; i < size; i++) {
            if (queue[i].compareTo(minRecord) < 0) {
                minRecord = queue[i];
            }
        }
        return minRecord;
    }

    // Arguments: none
    // Returns: Record
    // Desc: returns and removes record with highest priority, has to find manually. Heap overrides by finding root
    @Override
    public Record removeMin() {
        if (isEmpty()) return null;

        int minIndex = 0;
        for (int i = 1; i < size; i++) {
            if (queue[i].compareTo(queue[minIndex]) < 0) {
                minIndex = i;
            }
        }

        Record minRecord = queue[minIndex];

        // Shift elements to the left to fill the gap
        for (int i = minIndex; i < size - 1; i++) {
            queue[i] = queue[i + 1];
        }

        queue[size - 1] = null;  // Clear the last element
        size--;
        return minRecord;
    }

    // Arguments: none
    // Returns: int
    // Desc: return the number of elements in the queue's array
    @Override
    public int size() {
        return size;
    }

    // Arguments: none
    // Returns: boolean
    // Desc: returns true if the queue's array is empty
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
