package deque;

import net.sf.saxon.functions.ConstantFunction;

public class LinkedListDeque<T> {
    private class LNode{
        T data;
        LNode prev;
        LNode next;

        public LNode(T data) {
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

    public void addFirst(T item){
        LNode newNode = new LNode(item);
        newNode.prev = sentinel;
        newNode.next = sentinel.next;
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    public void addLast(T item) {
        LNode newNode = new LNode(item);
        newNode.next = sentinel;
        newNode.prev = sentinel.prev;
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }

    public T removeFirst() {
        if (isEmpty()){
            return null;
        }
        LNode firstNode = sentinel.next;
        firstNode.next.prev = sentinel;
        sentinel.next = firstNode.next;
        size--;
        return firstNode.data;
    }

    public T removeLast(){
        if (isEmpty()){
            return null;
        }
        LNode lastNode = sentinel.prev;
        sentinel.prev = lastNode.prev;
        lastNode.prev.next = sentinel;
        size--;
        return lastNode.data;
    }

    public int size(){
        return this.size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void printDeque(){
        LNode current = sentinel.next;
        while (current != sentinel){
            System.out.println(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    public T get(int index){
        int counter = 0;
        LNode current = sentinel.next;
        while (current != sentinel){
            if (counter == index) return current.data;
            current = current.next;
            counter++;
        }
        return null;
    }

    
}
