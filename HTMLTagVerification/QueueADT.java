public interface QueueADT<T> {
    boolean isEmpty();
    boolean isFull();
    int size();
    void enqueue(T item) throws Exception;
    T dequeue() throws Exception;
    T first() throws Exception;
}
