package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;


import java.util.Comparator;

import static org.junit.Assert.*;

public class ArrayDequeTest {
    @Test
    public void testEmpty() {
        ArrayDeque<String> AD = new ArrayDeque<>();
        assertTrue(AD.isEmpty());

        AD.addLast("a");
        AD.removeFirst();

        assertTrue(AD.isEmpty());
    }

    @Test
    public void testSize() {
        ArrayDeque<Double> AD = new ArrayDeque<>();

        AD.addFirst(1.1);
        assertEquals(1, AD.size());

        AD.addLast(1.2);
        assertEquals(2, AD.size());

        AD.removeLast();
        AD.removeFirst();

        assertEquals(0, AD.size());
    }

    @Test
    public void testGet(){
        ArrayDeque<Integer> AD = new ArrayDeque<>();

        AD.addLast(1);
        AD.addLast(2);

        Assert.assertEquals("Element with index 0 should be 1", Integer.valueOf(1), AD.get(0));
        Assert.assertEquals(Integer.valueOf(2), AD.get(1));
        assertNull(AD.get(-1));
        assertNull(AD.get(3));
    }

//    @Test
    /**
     * 测试AD是否能在增删元素时正确调整容量，满足size>=16时利用率不小于25%条件
     */
//    public void testCapacityScale(){
//        ArrayDeque<Integer> AD = new ArrayDeque<>();
//
//        for (int i = 0; i < 1000; i++){
//            AD.addLast(i);
//        }
//
//        assertTrue(AD.getCapacityFactor() > 0.25);
//
//        for (int i = 0; i < 899; i++){
//            AD.removeLast();
//        }
//
//        System.out.println(AD.getCapacityFactor());
//        assertTrue(AD.getCapacityFactor() > 0.25);
//    }

    @Test
    public void testRandomized(){
        ArrayDeque<Integer> AD = new ArrayDeque<>();
        int N = 10000;

        for (int i = 0; i < N; i++){
            int randNum = StdRandom.uniform(0,5);
            if (randNum <= 2){
                AD.addLast(i);
                assertEquals(Integer.valueOf(i), AD.get(AD.size() - 1));  // 测试addLast是否正常工作
            } else if (randNum > 2){
                int sizeP = AD.size(); // previous size
                Integer lastElement = AD.get(sizeP - 1);
                Integer removedElement = AD.removeLast();
                assertEquals(lastElement, removedElement);
            }
//            if (AD.size() >= 16){
//                assertTrue(AD.getCapacityFactor() > 0.25);
//            }
        }

        for (int i = 0; i < N * 1.2; i++){
            int randNum = StdRandom.uniform(0,5);
            if (randNum >= 2){
                AD.addLast(i);
            } else if (randNum < 2){
                AD.removeLast();
            }
//            if (AD.size() >= 16){
//                assertTrue(AD.getCapacityFactor() > 0.25);
//            }
        }
    }

    @Test
    public void testEquals_SameObject() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        assertTrue(deque.equals(deque));
    }

    @Test
    public void testEquals_NullObject() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        assertFalse(deque.equals(null));
    }

    @Test
    public void testEquals_DifferentClass() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        Object obj = new Object();
        assertFalse(deque.equals(obj));
    }

    @Test
    public void testEquals_EmptyDeques() {
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        ArrayDeque<Integer> deque2 = new ArrayDeque<>();
        assertTrue(deque1.equals(deque2));
    }

    @Test
    public void testEquals_SameElements() {
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        deque1.addFirst(1);
        deque1.addLast(2);
        deque1.addLast(3);

        ArrayDeque<Integer> deque2 = new ArrayDeque<>();
        deque2.addFirst(1);
        deque2.addLast(2);
        deque2.addLast(3);

        assertTrue(deque1.equals(deque2));
    }

    @Test
    public void testEquals_DifferentElements() {
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        deque1.addFirst(1);
        deque1.addLast(2);
        deque1.addLast(3);

        ArrayDeque<Integer> deque2 = new ArrayDeque<>();
        deque2.addFirst(1);
        deque2.addLast(2);
        deque2.addLast(4);

        assertFalse(deque1.equals(deque2));
    }

    @Test
    public void testEquals_DifferentSizes() {
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        deque1.addFirst(1);
        deque1.addLast(2);

        ArrayDeque<Integer> deque2 = new ArrayDeque<>();
        deque2.addFirst(1);
        deque2.addLast(2);
        deque2.addLast(3);

        assertFalse(deque1.equals(deque2));
    }

    @Test
    public void testEquals_DifferentCapacities() {
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        deque1.addFirst(1);
        deque1.addLast(2);
        deque1.addLast(3);

        ArrayDeque<Integer> deque2 = new ArrayDeque<>();
        deque2.addFirst(1);
        deque2.addLast(2);
        deque2.addLast(3);
//        deque2.resize(16);

        assertTrue(deque1.equals(deque2));
    }

    @Test
    public void testEquals_CircularArray() {
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        deque1.addFirst(1);
        deque1.addLast(2);
        deque1.addLast(3);
        deque1.removeFirst();
        deque1.addLast(4);

        ArrayDeque<Integer> deque2 = new ArrayDeque<>();
        deque2.addFirst(2);
        deque2.addLast(3);
        deque2.addLast(4);

        assertTrue(deque1.equals(deque2));
    }
}
