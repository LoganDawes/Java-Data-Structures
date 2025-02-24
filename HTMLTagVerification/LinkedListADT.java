public interface LinkedListADT<T> {
    boolean isEmpty();
    int size();
    void addLast(T item);
    T removeFirst() throws Exception;
    T getFirst() throws Exception;
}
