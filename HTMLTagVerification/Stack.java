public class Stack<T> implements StackADT<T> {
    // Stack is implemented by Queue
    private Queue<T> Q1; // Main queue
    private Queue<T> Q2; // Helper queue
    private int size;

    // Default constructor
    public Stack(int size) {
        this.size = size;
        Q1 = new Queue<>(size);
        Q2 = new Queue<>(size);
    }

    // Returns true if the stack is empty
    // Args: none, Return: true / false
    @Override
    public boolean isEmptyStack() {
        return Q1.isEmpty();
    }

    // Returns true if the stack is full
    // Args: none, Return: true / false
    @Override
    public boolean isFullStack() {
        return Q1.isFull();
    }

    // Returns the current size of the stack
    // Args: none, Return: int size
    @Override
    public int size() {
        return Q1.size();
    }

    // Adds an item to the top of the stack
    // Args: object T, Return: none
    @Override
    public void push(T item) throws Exception {
        // First detects if the stack is full
        if (isFullStack()) {
            throw new Exception("Stack is full.");
        }

        // if Q1 is empty enqueue item to Q1
        if (Q1.isEmpty()) {
            Q1.enqueue(item);
        } else {
            // enqueue all elements to Q2
            while (!Q1.isEmpty()) {
                Q2.enqueue(Q1.dequeue());
            }
            // enqueue item to Q1
            Q1.enqueue(item);
            // enqueue all elements from Q2 back to Q1
            while (!Q2.isEmpty()) {
                Q1.enqueue(Q2.dequeue());
            }
            // item is now correctly at top of the stack
        }
    }

    // Returns item at the top without removal
    // Args: none, Return: object T
    @Override
    public T peek() throws Exception {
        // First detects if the stack is empty
        if (Q1.isEmpty()) {
            throw new Exception("Stack is empty.");
        }
        return Q1.first();
    }

    // Removes and returns item from the top of the stack
    // Args: none, Return: object T
    @Override
    public T pop() throws Exception {
        // First detects if the stack is empty
        if (Q1.isEmpty()) {
            throw new Exception("Stack is empty.");
        }
        return Q1.dequeue();
    }
}
