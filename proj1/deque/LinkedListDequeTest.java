package deque;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Performs some basic linked list tests.
 */
public class LinkedListDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

//        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

        System.out.println("Printing out deque: ");
        lld1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

//        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());

    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

//        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {


        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double> lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();

    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

//        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());


    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

//        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }
    }

    @Test
    /* Check if get() returns the correct element at a given index. */
    public void getTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();
        lld1.addLast("first");
        lld1.addLast("second");
        lld1.addLast("third");

        assertEquals("Should return 'first'", "first", lld1.get(0));
        assertEquals("first", lld1.getRecursive(0));
        assertEquals("Should return 'second'", "second", lld1.get(1));
        assertEquals("Should return 'second'", "second", lld1.getRecursive(1));
        assertEquals("Should return 'third'", "third", lld1.get(2));
        assertEquals("Should return 'third'", "third", lld1.getRecursive(2));
    }

    @Test
    /* Check if get() returns null for an out-of-bounds index. */
    public void getOutOfBoundsTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();
        lld1.addLast("first");
        lld1.addLast("second");

        assertNull("Should return null for negative index", lld1.get(-1));
        assertNull("Should return null for index greater than size", lld1.get(2));
    }

    @Test
    /*
    Check if equals() returns true for two LLD
    with same element or that are the same object.
     */
    public void testEquals_Basic() {
        LinkedListDeque<Integer> lldInt1 = new LinkedListDeque<>();
        LinkedListDeque<Integer> lldInt2 = new LinkedListDeque<>();
        LinkedListDeque<String> lldString1 = new LinkedListDeque<>();
        LinkedListDeque<String> lldString2 = new LinkedListDeque<>();

        assertTrue(lldInt2.equals(lldInt1));

        for (int i = 55; i < 59; i++){
            lldInt2.addLast(i);
            lldInt1.addLast(i);
            lldString1.addLast(Integer.toString(i));
            lldString2.addLast(Integer.toString(i));
        }
        assertTrue(lldInt2.equals(lldInt1));
        assertTrue(lldString1.equals(lldString2));
        assertTrue(lldString1.equals(lldString1));
        lldInt1.addLast(2);
        lldString1.addLast("2");
        lldString1.addLast("3");
        assertFalse(lldInt1.equals(lldInt2));
        assertFalse(lldString1.equals(lldString2));
    }

    @Test
    /* Test if equals() returns true for two deque with same elements
    different class.
     */
    public void testEquals_DifferentImp() {
        Deque<String> LLD = new LinkedListDeque<>();
        Deque<String> AD = new ArrayDeque<>();
        assertTrue(LLD.equals(AD));

        for (int i = 1; i < 5; i++){
            LLD.addLast("" + i);
            AD.addLast(""+ i);
        }
        assertTrue(LLD.equals(AD));

        AD.addLast("4");
        assertFalse(LLD.equals(AD));
    }

    @Test
    public void testEquals_DifferentClass() {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>();
        Object obj = new Object();
        assertFalse(deque.equals(obj));
    }
}
