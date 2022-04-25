/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int N;

    private class Node() {
        Item item;
        Node next;
    }

    // construct an empty deque
    public Deque() {


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
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (isEmpty()) {
            first = last;
        }
        else {
            first.next = oldfirst;
        }
        N++;

    }

    // add the item to the back
    public void addLast(Item item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        }
        else {
            oldlast.next = last;
        }
        N++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
            N--;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() throws NoSuchElementException {
        Item item = last.item;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        public boolean hasNext() {
            return N == 0;
        }

        public Item next() {
            return null;
        }
    }

    // unit testing (required)
    public static void main(String[] args)

}
