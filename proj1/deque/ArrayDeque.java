package deque;

import java.util.Iterator;

@SuppressWarnings("unchecked")
public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private int capacity; // 数组长度
    private int size; // 队列的长度
    private int first; // 队列第一个元素的索引
    private int last; // 队列最后一个元素的索引后一位
    private T[] items; // 存储元素的数组

    public ArrayDeque() {
        items = (T[]) new Object[8];
        first = 0;
        last = 0;
        size = 0;
        capacity = 8;
    }

    @Override
    public void addFirst(T item) {
        if (isFull()) {
            resize(capacity * 2);
        }
        first = (first - 1 + capacity) % capacity;
        items[first] = item;
        size++;
    }

    @Override
    public void addLast(T item) {
        if (isFull()) {
            resize(capacity * 2);
        }
        items[last] = item;
        last = (last + 1) % capacity;
        size++;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T n = get(0);
        size--;
        first = (first + 1) % capacity;
        if ((size < capacity / 4) && capacity >= 16) {
            resize(capacity / 2);
        }
        return n;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T n = get(size - 1);
        size--;
        last = ((last - 1) + capacity) % capacity;
        if ((size < capacity / 4) && capacity >= 16) {
            resize(capacity / 2);
        }
        return n;
    }

    /**
     * 获取指定索引的元素
     *
     * @param index 索引
     * @return 返回的元素
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        return items[(first + index) % capacity];
    }

    /**
     * 将双端队列调整至指定大小（调整 capacity）
     *
     * @param newCapacity 调整的目标大小
     */
    private void resize(int newCapacity) {
        if (newCapacity < size) {
            throw new IllegalArgumentException("New capacity" +
                    " must be greater than or equal to the current size.");
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

    @Override
    public int size() {
        return size;
    }

    private double getCapacityFactor() {
        return (double) size / capacity;
    }

    /**
     * 打印双端队列中的所有元素。
     * 元素之间用空格分隔，最后换行。
     */
    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(items[(i + first) % capacity]);
            if (i < size - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
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
                if (thatIterator.hasNext()){
                    if (!thatIterator.next().equals(item)){
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int times;

        ArrayDequeIterator() {
            this.times = 0;
        }

        @Override
        public boolean hasNext() {
            return times < size;
        }

        @Override
        public T next() {
            T nextItem = items[(times + first) % capacity];
            times++;
            return nextItem;
        }
    }
}
