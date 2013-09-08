import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private Node<Item> head;
	private Node<Item> tail;

	// construct an empty deque
	public Deque() {
		this.head = null;
		this.tail = null;
	}

	// is the deque empty?
	public boolean isEmpty() {
		return ((this.head == this.tail) && this.head == null);
	}

	// return the number of items on the deque
	public int size() {
		Iterator<Item> itr = this.iterator();
		int count = 0;
		while (itr.hasNext() == true) {
			count++;
			itr.next();
		}

		return count;
	}

	// insert the item at the front
	public void addFirst(Item item) {
		validateItemIsNotNull(item);

		Node<Item> newNode = new Node<Item>(item);
		newNode.setPrev(null);
		newNode.setNext(this.head);

		if (isEmpty()) {
			this.tail = newNode;
		} else {
			this.head.setPrev(newNode);
		}

		this.head = newNode;
	}

	private void validateItemIsNotNull(Item item) {
		if (item == null) {
			throw new NullPointerException();
		}
	}

	// insert the item at the end
	public void addLast(Item item) {
		validateItemIsNotNull(item);

		Node<Item> newNode = new Node<Item>(item);
		newNode.setNext(null);
		newNode.setPrev(this.tail);

		if (isEmpty()) {
			this.head = newNode;
		} else {
			this.tail.setNext(newNode);
		}

		this.tail = newNode;
	}

	// delete and return the item at the front
	public Item removeFirst() {
		validateQueueIsNotEmpty();

		Node<Item> removed = this.head;

		this.head = removed.getNext();

		if (this.head != null) {
			this.head.setPrev(null);
		} else {
			this.tail = null;
		}

		return removed.getValue();
	}

	private void validateQueueIsNotEmpty() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		}
	}

	// delete and return the item at the end
	public Item removeLast() {
		validateQueueIsNotEmpty();

		Node<Item> removed = this.tail;

		this.tail = removed.getPrev();

		if (this.tail != null) {
			this.tail.setNext(null);
		} else {
			this.head = null;
		}

		return removed.getValue();
	}

	@Override
	// return an iterator over items in order from front to end
	public Iterator<Item> iterator() {
		return new DequeItr<Item>(this.head);
	}

	private class Node<Item> {

		private Node<Item> next;
		private Node<Item> prev;
		private final Item value;

		public Node(Item value) {
			this.next = null;
			this.prev = null;
			this.value = value;
		}

		public void setNext(Node<Item> next) {
			this.next = next;
		}

		public Node<Item> getNext() {
			return this.next;
		}

		public void setPrev(Node<Item> prev) {
			this.prev = prev;
		}

		public Node<Item> getPrev() {
			return this.prev;
		}

		public Item getValue() {
			return this.value;
		}
	}

	private class DequeItr<Item> implements Iterator<Item> {

		private Node<Item> current;

		public DequeItr(Node<Item> head) {
			this.current = head;
		}

		@Override
		public boolean hasNext() {
			return this.current != null;
		}

		@Override
		public Item next() {
			validateNextIsValidOperaton();

			Item value = current.getValue();
			current = current.getNext();
			return value;
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
