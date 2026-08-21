import java.lang.Iterable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first;

    // inner class that helps to build a deque
    private class Node {
        public Item item;
        public Node front;
        public Node next;
    }

    // construct an empty deque
    public Deque() {
        this.first = new Node();
        this.first.item = null;
        this.first.front = this.first;
        this.first.next = this.first;
        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node temp = new Node();
        temp.item = item;
        temp.front = this.first;
        temp.next = this.first.next;
        this.first.next.front = temp;
        this.first.next = temp;
        this.size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node temp = new Node();
        temp.item = item;
        temp.next = this.first;
        temp.front = this.first.front;
        this.first.front.next = temp;
        this.first.front = temp;
        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        Node temp = this.first.next;
        Item tempItem = temp.item;
        temp.next.front = this.first;
        this.first.next = temp.next;
        this.size--;
        return tempItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        Node temp = this.first.front;
        Item tempItem = temp.item;
        temp.front.next = this.first;
        this.first.front = temp.front;
        this.size--;
        return tempItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // private class that used for DequeIterator
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing
    public static void main(String[] args) {

        }
    }