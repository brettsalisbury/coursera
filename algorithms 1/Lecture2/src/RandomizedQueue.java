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
        createNewQueueOfSize(newSize);
    }

    private void createNewQueueOfSize(int newSize) {
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
        this.collection[removeIndex] = this.collection[this.next - 1];
        this.collection[this.next - 1] = null;
        this.next--;

        reduceMemoryIfRequired();

        return val;
    }

    private void reduceMemoryIfRequired() {
        if (this.next < (this.size / 2)) {
            int newSize = this.size / 2;
            createNewQueueOfSize(newSize);
        }
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
            this.collection = new Object[size];
            for (int i = 0; i < size; i++) {
                this.collection[i] = collection[i];
            }

            this.size = size;
            this.currIndex = 0;
            shuffle();
        }

        private void shuffle() {
            for (int i = 0; i < this.size; i++) {
                int swapIndex = StdRandom.uniform(size);
                Object temp = this.collection[swapIndex];
                this.collection[swapIndex] = this.collection[i];
                this.collection[i] = temp;
            }
        }

        @Override
        public boolean hasNext() {
            return this.currIndex < this.size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Item next() {
            validateNextIsValidOperaton();

            Item val = (Item) this.collection[this.currIndex];
            this.currIndex++;
            return val;
        }

        private void validateNextIsValidOperaton() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
}
