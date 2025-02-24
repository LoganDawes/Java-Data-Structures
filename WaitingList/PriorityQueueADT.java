// Interface to declare PriorityQueue's basic methods
public interface PriorityQueueADT<Record> {
    Record insert(Record record) throws IllegalArgumentException;
    Record min();
    Record removeMin();
    int size();
    boolean isEmpty();
}
