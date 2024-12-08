package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private class LNode {
        T data;
        LNode prev;
        LNode next;

        LNode(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private LNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new LNode(null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    @Override
    public void addFirst(T item) {
        LNode newNode = new LNode(item);
        newNode.prev = sentinel;
        newNode.next = sentinel.next;
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    @Override
    public void addLast(T item) {
        LNode newNode = new LNode(item);
        newNode.next = sentinel;
        newNode.prev = sentinel.prev;
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        LNode firstNode = sentinel.next;
        firstNode.next.prev = sentinel;
        sentinel.next = firstNode.next;
        size--;
        return firstNode.data;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        LNode lastNode = sentinel.prev;
        sentinel.prev = lastNode.prev;
        lastNode.prev.next = sentinel;
        size--;
        return lastNode.data;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void printDeque() {
        LNode current = sentinel.next;
        while (current != sentinel) {
            System.out.println(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int counter = 0;
        LNode current = sentinel.next;
        while (current != sentinel) {
            if (counter == index) {
                return current.data;
            }
            current = current.next;
            counter++;
        }
        return null;
    }

    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }

    private T getRecursiveHelper(int index, LNode current) {
        if (current == sentinel) {
            return null;
        }
        if (index == 0) {
            return current.data;
        }
        return getRecursiveHelper(index - 1, current.next);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        if (obj instanceof Deque<?>) {
            Deque<?> that = (Deque<?>) obj;
            if (this.size() != that.size()) {
                return false;
            }
            if (this.isEmpty() && that.isEmpty()) {
                return true;
            }
            Iterator<?> thatIterator = that.iterator();
            for (T item : this) {
                if (thatIterator.hasNext()) {
                    if (!thatIterator.next().equals(item)) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private LNode current = sentinel.next;

        @Override
        public boolean hasNext() {
            return current != sentinel;
        }

        @Override
        public T next() {
            T item = current.data;
            current = current.next;
            return item;
        }
    }
}
