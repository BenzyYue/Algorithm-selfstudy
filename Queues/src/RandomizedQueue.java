import java.lang.Iterable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] randomizedQueue;
    private int size;
    private int capacity;
    private int frontPointer;

    // construct an empty randomized queue
    public RandomizedQueue() {
        capacity = 8;
        randomizedQueue = (Item[]) new Object[capacity];
        size = 0;
        frontPointer = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (size == capacity) {
            randomizedQueue = resize(randomizedQueue);
        }
        randomizedQueue[frontPointer] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        int randomPointer = -1;
        Item returnedItem = null;
        while (returnedItem == null) {
            randomPointer = StdRandom.uniformInt(0, frontPointer);
            returnedItem = randomizedQueue[randomPointer];
        }
        randomizedQueue[randomPointer] = null;
        size--;
        if (size > 0 && size == capacity / 4) {
            randomizedQueue = shrink(randomizedQueue);
        }
        return returnedItem;
    }

    // return a random item (but not remove it)
    public Item sample() {
        int randomPointer;
        Item returnedItem = null;
        while (returnedItem == null) {
            randomPointer = StdRandom.uniformInt(0, frontPointer);
            returnedItem = randomizedQueue[randomPointer];
        }
        return returnedItem;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {}

    // resize a full randomized queue
    private Item[] resize(Item[] queue) {

    }

    // shrink a randomized queue that fits the condition of shrinking
    private Item[] shrink(Item[] queue) {}

    // unit testing
    public static void main(String[] args) {}
}
