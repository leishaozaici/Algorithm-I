/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[1];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    private void resize(int newsize) {
        Item[] temp = (Item[]) new Object[newsize];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("缺失参数");
        }
        if (a.length == n) {
            resize(a.length * 2);
        }
        a[n++] = item;

    }

    // remove and return a random item
    public Item dequeue() {
        if (n == 0) {
            throw new NoSuchElementException("Empty queue");
        }
        int i = StdRandom.uniform(0, n);
        Item temp = a[i];
        n--;
        if (i != n) {
            a[i] = a[n];
        }
        a[n] = null;
        if (n > 0 && n == a.length / 4) {
            resize(a.length / 2);
        }
        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (n == 0) {
            throw new NoSuchElementException("Empty Queue");
        }
        return a[StdRandom.uniform(0, n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<Item> {
        private final Item[] iterq;
        private int m;

        private RandomQueueIterator() {
            iterq = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) iterq[i] = a[i];
            StdRandom.shuffle(iterq);
        }


        public boolean hasNext() {
            return m < n;
        }

        public Item next() {
            if (m >= n) {
                throw new NoSuchElementException("No next element");
            }
            return iterq[m++];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        for (int i = 0; i < 10; i++) {
            randomizedQueue.enqueue(String.valueOf(i));
        }
        for (String s : randomizedQueue) {
            StdOut.println(s);
        }
    }

}
