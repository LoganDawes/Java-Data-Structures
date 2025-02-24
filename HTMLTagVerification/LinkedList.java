public class LinkedList<T> implements LinkedListADT<T>{
    private Node<T> head;
    private Node<T> tail;
    private int size;

    // Simple Node class for the linked list
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    // Default constructor
    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    // Returns true if the list is empty
    // Args: none, Return: true / false
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Returns the size of the list
    // Args: none, Return: int size
    @Override
    public int size() {
        return size;
    }

    // Adds an item to the end of the list
    // Args: object T, Return: none
    @Override
    public void addLast(T item) {
        Node<T> newNode = new Node<>(item);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    // Removes and returns the first item of the list
    // Args: none, Return: object T
    @Override
    public T removeFirst() throws Exception {
        // First detects if the list is empty
        if (isEmpty()) {
            throw new Exception("List is empty.");
        }
        T data = head.data;
        head = head.next;
        size--;
        if (isEmpty()) {
            tail = null; // List is now empty, reset tail
        }
        return data;
    }

    // Returns first item without removal
    // Args: none, Return: object T
    @Override
    public T getFirst() throws Exception {
        // First detects if the list is empty
        if (isEmpty()) {
            throw new Exception("List is empty.");
        }
        return head.data;
    }
}
