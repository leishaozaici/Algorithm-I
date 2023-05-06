/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int n;

    private class Node {
        private Item item;
        private Node pre, next;
    }

    // construct an empty deque
    public Deque() {
        n = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.pre = null;
        if (n != 0) {
            first.next = oldfirst;
            oldfirst.pre = first;
        }
        else {
            last = first;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.pre = oldlast;
        if (n != 0) {
            oldlast.next = last;
        }
        else {
            first = last;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        n--;
        if (n == 0) {
            last = null;
        }
        else {
            first.pre = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.pre;
        n--;
        if (n == 0) {
            first = null;
        }
        else {
            last.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;

        }
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


