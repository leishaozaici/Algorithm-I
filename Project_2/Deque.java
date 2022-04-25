/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Item[] a;
    private int head;
    private int tail;
    private int N;

    // construct an empty deque
    public Deque() {
        N = 0;
        head = 0;
        tail = 0;
        a = (Item[]) new Object[1];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the deque
    public int size() {
        return N;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (N == a.length) {
            resize(a.length * 2);
        }
        a[head--] = item;
        N++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (N == a.length) {
            resize(a.length * 2);
        }
        a[tail++] = item;
        N++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = a[head];
        a[head++] = null;
        if (N > 0 && N == a.length / 4) {
            resize(a.length / 2);
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = a[tail];
        a[tail--] = null;
        if (N > 0 && N == a.length / 4) {
            resize(a.length / 2);
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private int i = N;

        public boolean hasNext() {
            return i > 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return a[--i];
        }
    }

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        for (int i = 0; i < 3; i++) {
            deque.addLast(i + "");
        }
        while (!deque.isEmpty()) {
            StdOut.println(deque.removeFirst());
        }
        Deque<Integer> ideque = new Deque<>();
        for (int i = 0; i < 20; i++) {
            ideque.addFirst(i);
        }
        Iterator<Integer> iterator = ideque.iterator();
        while (iterator.hasNext()) {
            StdOut.println(iterator.next());
        }

        while (!ideque.isEmpty()) {
            StdOut.println(ideque.removeLast());
        }
        System.out.println("************************");
        for (int i = 0; i < 3; i++) {
            if (i % 2 == 0) {
                ideque.addFirst(i);
            }
            else {
                StdOut.println(ideque.removeLast());
            }
        }

    }

}


