public class Queue<T> implements QueueADT<T>{
    // Queue is implemented by LinkedList
    private LinkedList<T> list;
    private int size;

    // Default constructor
    public Queue(int size) {
        this.size = size;
        this.list = new LinkedList<>();
    }

    // Returns true if queue is empty
    // Args: none, Return: true / false
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    // Returns true if queue is full
    // Args: none, Return: true / false
    @Override
    public boolean isFull() {
        return list.size() == size;
    }

    // Returns current size of queue
    // Args: none, Return: int size
    @Override
    public int size() {
        return list.size();
    }

    // Adds an item to the back of the queue
    // Args: object T, Return: none
    @Override
    public void enqueue(T item) throws Exception {
        // First detects if the queue is full
        if (isFull()) {
            throw new Exception("Queue is full.");
        }
        list.addLast(item);
    }

    // Removes and returns item from the front of the queue
    // Args: none, Return: object T
    @Override
    public T dequeue() throws Exception {
        // First detects if the queue is empty
        if (isEmpty()) {
            throw new Exception("Queue is empty.");
        }
        return list.removeFirst();
    }

    // Returns item from the front of the queue without removal
    // Args: none, Return: object T
    @Override
    public T first() throws Exception {
        // First detects if the queue is empty
        if (isEmpty()) {
            throw new Exception("Queue is empty.");
        }
        return list.getFirst();
    }
}
