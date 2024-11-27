package deque;

import java.util.Arrays;

@SuppressWarnings("unchecked")
public class ArrayDeque<T> {
    private int capacity; // 数组长度
    private int size; // 队列的长度
    private int last; // 队列最后一个元素的位置
    private int first; // 队列第一个元素的位置
    private T[] items;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        first = 0;
        last = 0;
        size = 0;
        capacity = 8;
    }

    /**
     * 在队列首部添加元素。item永不为null。
     */
    public void addFirst(T item) {
        if (isFull()) {
            resize(capacity * 2);
        }
        first = (first - 1 + capacity) % capacity;
        items[first] = item;
        size++;
    }

    public void addLast(T item) {
        items[last] = item;
        last = ( last + 1 ) % capacity;
        size++;
    }

    public T removeFirst(){
        T n = items[first];
        size--;
        first = (first + 1) % capacity;
        return n;
    }

    public T removeLast(){
        size--;
        last = (last - 1) % capacity;
        return items[last];
    }

    public T get(int index){
        return items[(first + index) % capacity];
    }

    public void resize(int newCapacity) {
        if (newCapacity < size) {
            throw new IllegalArgumentException("New capacity must be greater than or equal to the current size.");
        }
        T[] newItems = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newItems[(first + i) % newCapacity] = items[(first + i + capacity) % capacity];
        }
        items = newItems;
        last = (first + size) % newCapacity;
        capacity = newCapacity;
    }

    private boolean isFull() {
        return size == capacity;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }



    public void printDeque(){
        for (int i = 0; i < size; i++){
            System.out.print(items[(i + first) % capacity]);
            if (i < size - 1) System.out.print(" ");
        }
        System.out.println();
    }


    public String tooString() {
        return "ArrayDeque{" +
                "items=" + Arrays.toString(items) +
                ", first=" + first +
                ", last=" + last +
                ", size=" + size +
                ", capacity=" + capacity +
                '}';
    }

    @Override
    public String toString(){
        return Arrays.toString(items);
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
        arrayDeque.addFirst(1);
        arrayDeque.addFirst(2);
        arrayDeque.addLast(3);
        arrayDeque.addLast(4);
        arrayDeque.addLast(5);
        arrayDeque.addLast(6);
        arrayDeque.addLast(7);
        arrayDeque.addLast(8);
        System.out.println(arrayDeque.tooString());;
        arrayDeque.resize(16);
        System.out.println(arrayDeque.tooString());
        arrayDeque.printDeque();
        System.out.println(arrayDeque.removeFirst());
        System.out.println(arrayDeque.removeLast());
        System.out.println(arrayDeque.tooString());
        arrayDeque.printDeque();
        System.out.println(arrayDeque.get(0));
    }
}
