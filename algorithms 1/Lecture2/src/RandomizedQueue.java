import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Object[] collection;
    private int size;
    private int next;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.size = 2;
        this.next = 0;
        this.collection = new Object[size];
    }

    // is the queue empty?
    public boolean isEmpty() {
        return (this.next == 0);
    }

    // return the number of items on the queue
    public int size() {
        return this.next;
    }

    // add the item
    public void enqueue(Item item) {
        validateItemIsNotNull(item);

        if (this.next == this.size) {
            increaseCollectionSize();
        }

        this.collection[this.next] = item;
        this.next++;
    }

    private void validateItemIsNotNull(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }

    private void increaseCollectionSize() {
        int newSize = this.size * 2;
        Object newCollection[] = new Object[newSize];
        for (int i = 0; i < this.next; i++) {
            newCollection[i] = this.collection[i];
        }

        this.collection = newCollection;
        this.size = newSize;
    }

    // delete and return a random item
    public Item dequeue() {
        validateQueueIsNotEmpty();

        int removeIndex = getIndex();
        @SuppressWarnings("unchecked")
        Item val = (Item) this.collection[removeIndex];
        for (int i = removeIndex + 1; i < this.next; i++) {
            this.collection[i - 1] = this.collection[i];
        }

        return val;
    }

    private void validateQueueIsNotEmpty() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    private int getIndex() {
        return StdRandom.uniform(this.next);
    }

    // return (but do not delete) a random item
    @SuppressWarnings("unchecked")
    public Item sample() {
        validateQueueIsNotEmpty();

        int sampleIndex = getIndex();
        return (Item) this.collection[sampleIndex];
    }

    @Override
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(this.collection, this.next);
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private final Object[] collection;
        private final int size;
        private int currIndex;

        public RandomizedQueueIterator(Object[] collection, int size) {
            this.collection = collection;
            this.size = size;
            this.currIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return this.currIndex < this.size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Item next() {
            Item val = (Item) this.collection[this.currIndex];
            this.currIndex++;
            return val;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
}
