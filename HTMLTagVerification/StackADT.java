public interface StackADT<T> {
    boolean isEmptyStack();
    boolean isFullStack();
    int size();
    void push(T item) throws Exception;
    T peek() throws Exception;
    T pop() throws Exception;
}
