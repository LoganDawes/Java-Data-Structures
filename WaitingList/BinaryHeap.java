// Extends basic PriorityQueue functionality to maintain heap properties
public class BinaryHeap extends PriorityQueue {
    private static final int DEFAULT_CAPACITY = 10;
    private Record[] queue; // Array to store heap entries
    private int size; // Current size of the heap

    // Arguments: none
    // Returns: none
    // Desc: Constructs BinaryHeap with a starting capacity 10
    public BinaryHeap() {
        this.queue = new Record[DEFAULT_CAPACITY];
        this.size = 0;
    }

    // Arguments: Record
    // Returns: Record
    // Desc: inserts a record into the array and returns it after maintaining heap property. Resizes if array is full by Growth property 2N
    @Override
    public Record insert(Record record) throws IllegalArgumentException {
        if (size == queue.length) {
            resize(2 * queue.length); // Double the array size if it's full
        }
        queue[size] = record;
        size++;
        upheap(size - 1); // Maintain heap property
        return record;
    }

    // Arguments: none
    // Returns: Record
    // Desc: returns record with highest priority (root)
    @Override
    public Record min(){
        if (isEmpty()) return null;
        return queue[0];
    }

    // Arguments: none
    // Returns: none
    // Desc: returns and removes record with highest priority (root), then maintains heap property
    @Override
    public Record removeMin() {
        if (isEmpty()) return null;

        Record minRecord = queue[0]; // The root is the min element
        queue[0] = queue[size - 1]; // Move the last element to the root
        queue[size - 1] = null; // Clear the last element
        size--;
        downheap(0); // Restore heap property
        return minRecord;
    }

    // Arguments: Record
    // Returns: boolean
    // Desc: removes specified record from the array, returns true if action is successful
    public boolean remove(Record targetRecord) {
        for (int i = 0; i < size; i++) {
            if (queue[i].equals(targetRecord)) {
                queue[i] = queue[size - 1]; // Replace with last element
                queue[size - 1] = null; // Clear last element
                size--;
                downheap(i); // Restore heap order
                return true; // Record found and removed
            }
        }
        return false; // No matching record found
    }

    // Arguments: int index
    // Returns: Record
    // Desc: returns record at specified index
    public Record getRecord(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return queue[index];
    }

    // Arguments: int
    // Returns: none
    // Desc: Restore the heap property by moving the element at index j up
    protected void upheap(int j) {
        while (j > 0) { // Continue until reaching root (or break statement)
            int p = parent(j);
            if (queue[j].compareTo(queue[p]) >= 0) {
                break; // Heap property verified
            }
            swap(j, p); // Swap the current node with its parent
            j = p; // Continue from the parent's location
        }
    }

    // Arguments: int
    // Returns: none
    // Desc: Restore the heap property by moving the element at index j down
    protected void downheap(int j) {
        Record record = queue[j];
        while (hasLeft(j)) {
            int leftChild = left(j);
            int minChild = leftChild; // Start with the left child
            if (hasRight(j) && queue[leftChild].compareTo(queue[right(j)]) > 0) {
                minChild = right(j); // Choose the right child if it's smaller
            }
            if (record.compareTo(queue[minChild]) <= 0) break; // Heap property satisfied
            swap(j, minChild); // Move the smaller child up
            j = minChild; // Continue down
        }
        queue[j] = record; // Place the entry at its correct position
    }

    // Arguments: int
    // Returns: int
    // Desc: returns parent node's index of specified index
    public int parent(int j) {
        return (j - 1) / 2; // Formula to find the parent index
    }

    // Arguments: int
    // Returns: int
    // Desc: returns left child node's index of specified index
    public int left(int j) {
        return 2 * j + 1; // Formula to find the left child index
    }

    // Arguments: int
    // Returns: int
    // Desc: returns right child node's index of specified index
    public int right(int j) {
        return 2 * j + 2; // Formula to find the right child index
    }

    // Arguments: int
    // Returns: boolean
    // Desc: returns true if specified index has a left child node
    public boolean hasLeft(int j) {
        return left(j) < size; // Check if left child exists
    }

    // Arguments: int
    // Returns: boolean
    // Desc: returns true if specified index has a right child node
    public boolean hasRight(int j) {
        return right(j) < size; // Check if right child exists
    }

    // Arguments: int i, int j
    // Returns: none
    // Desc: swaps the nodes based on their index
    public void swap(int i, int j) {
        Record temp = queue[i];
        queue[i] = queue[j];
        queue[j] = temp; // Swap elements at indices i and j
    }

    // Arguments: none
    // Returns: int
    // Desc: return the number of elements in the heap's array
    @Override
    public int size() {
        return size;
    }

    // Arguments: none
    // Returns: boolean
    // Desc: returns true if the heap's array is empty
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Arguments: int
    // Returns: none
    // Desc: resizes by creating a new array and then copying over the elements
    public void resize(int capacity) {
        Record[] newArray = new Record[capacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = queue[i];
        }
        queue = newArray; // Update the array reference
    }
}
